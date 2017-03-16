package org.usfirst.frc.team2903.robot.commands.commoners;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.subsystems.GearPegPipeline2903;
import org.usfirst.frc.team2903.robot.subsystems.Vision2903;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class DriveStraightForDistance extends Command {

	// TODO update for two encoders with average of the two and gyro

	private Vision2903 camera;

	double HighLimit;
	double LowLimit;
	double ErrorLimit = 3;

	int savedEncoderPosition = 0;
	int sameEncoderCount = 0;
	static final int MAX_SAME_ENCODER_COUNT = 5;

	int TargetEncoderPos = 0;
	int CurrentRightEncoderPos = 0;
	int CurrentLeftEncoderPos = 0;
	private int Distance = 0;


	static final double MIN_MOTOR_SPEED = 0.5;

	private double Kp = 0.03;
	private double MotorSpeed;
//	private double StartAngle;
	private boolean UseGyro;

	static final double PI = 3.14159;
	static final int COUNTS_PER_MOTOR_REV = 1024; // eg: Grayhill 61R256
	static final double DRIVE_GEAR_REDUCTION = 1.1; // Vex Pro 3 Sim Shifter Gear Ratio
	static final double WHEEL_DIAMETER_INCHES = 4.0; // For figuring circumference
	
	// count conversion to inches
	static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * PI);

	// for camera tracking
	private Object imgLock = new Object();
	private VisionThread visionThread;
	private double centerX = 0.0;


	// distance in inches
	public DriveStraightForDistance(int distance, boolean useGyro) {
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);

		Robot.driveSubsystem.driveReset();
		Robot.driveSubsystem.setAutoMode();
		Distance = distance;
		UseGyro = useGyro;
	}

	@Override
	protected void initialize() {

		Robot.gyroSubsystem.reset();
		Robot.driveSubsystem.driveReset();
		Robot.driveSubsystem.setAutoMode();
		

		// get current encoder counts
		CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();
		CurrentLeftEncoderPos = Robot.driveSubsystem.leftGetRawCount();

		// calculate target position
		TargetEncoderPos = (Distance * (int) COUNTS_PER_INCH);

		// setup the PID system
		Robot.minipidSubsystem.reset();
		Robot.minipidSubsystem.setSetpoint(TargetEncoderPos);
		Robot.minipidSubsystem.setOutputLimits(-80, 80);

		Robot.minipidSubsystem.setP(0.2);
		Robot.minipidSubsystem.setI(0);
		Robot.minipidSubsystem.setD(0);

		HighLimit = TargetEncoderPos + ErrorLimit;
		LowLimit = TargetEncoderPos - ErrorLimit;

		if (UseGyro) {
			Robot.gyroSubsystem.reset();
//			StartAngle = Robot.gyroSubsystem.GyroPosition();
		} else {
//			StartAngle = 0;
			
			camera = new Vision2903("10.29.3.56");
			camera.setResolution(Robot.IMG_WIDTH, Robot.IMG_HEIGHT);
			camera.setBrightness(0);

			visionThread = new VisionThread(camera.getCamera(), new GearPegPipeline2903(), pipeline -> {
				if (!pipeline.filterContoursOutput().isEmpty()) {
					Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
					synchronized (imgLock) {
						centerX = r.x + (r.width / 2);
					}
				}
			});
			visionThread.start();
		}

	}

	public double getCenterX() {
		double localCenterX = 0;
		if (!UseGyro) {
			synchronized (imgLock) {
				localCenterX = this.centerX;
			}
		}
		return localCenterX;
	}

	public int getDistance() {
		return Distance;
	}

	public void setDistance(int distance) {
		Distance = distance;
	}

	@Override
	protected void execute() {
		
		double angle = 0;

		CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();
		CurrentLeftEncoderPos = Robot.driveSubsystem.leftGetRawCount();

		// get motor speed from PID loop
		MotorSpeed = Robot.minipidSubsystem.getOutput(CurrentRightEncoderPos, TargetEncoderPos) / 100;
		
		// get angle from either gyro or camera centerx
		//angle = getTurnAngle();
		if (UseGyro) {
			angle = -Robot.gyroSubsystem.GyroPosition(); //StartAngle - 
		}

		// use camera image to calculate our turn value
		else {
			double localCenterX = getCenterX();
			double distanceFromCenter = (localCenterX - (Robot.IMG_WIDTH / 2));

			// get distance from center of image by percentage
			angle = (distanceFromCenter / (Robot.IMG_WIDTH / 2));
			SmartDashboard.putNumber("CenterX in Drive Straight", localCenterX);
		}
		
		// limit the minimum forward speed
		if (0 <= MotorSpeed && MotorSpeed < MIN_MOTOR_SPEED)
			MotorSpeed = MIN_MOTOR_SPEED;
		else if (-MIN_MOTOR_SPEED < MotorSpeed && MotorSpeed <= 0)
			MotorSpeed = -MIN_MOTOR_SPEED;

		SmartDashboard.putNumber("current right position", CurrentRightEncoderPos);
		SmartDashboard.putNumber("target position", TargetEncoderPos);
		SmartDashboard.putNumber("MOTOR SPEED", MotorSpeed);
		SmartDashboard.putNumber("Angle", angle);
		
		Robot.driveSubsystem.arcadeDrive(-MotorSpeed, angle * Kp);
	}

	@Override
	protected boolean isFinished() {

		// assume we are not finished
		boolean ourJobIsDoneHere = false;

		if (Math.abs(Robot.driveSubsystem.rightGetRawCount()) >= Math.abs(TargetEncoderPos)){
			ourJobIsDoneHere = true;
		}

		if (ourJobIsDoneHere && !UseGyro && camera != null)
			camera.setBrightness(100);

		return ourJobIsDoneHere;
	}

	@Override
	protected void end() {

		// stop driving
		Robot.driveSubsystem.arcadeDrive(0, 0);

		// raise the brightness if using camera to drive straight
		if (!UseGyro) {
			camera.setBrightness(100);
		}
	}

	@Override
	protected void interrupted() {
		end();
	}

}
