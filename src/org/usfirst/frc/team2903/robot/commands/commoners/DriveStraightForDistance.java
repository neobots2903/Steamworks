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

	boolean ourJobIsDoneHere = false;

	double MinMotorSpeed = 0.3;
	double MaxMotorSpeed = 0.5;

	private double Kp = 0.03;
	private double MotorSpeed;
	private double StartAngle;
	private boolean UseGyro;

	static final double PI = 3.14159;
	static final int COUNTS_PER_MOTOR_REV = 1024; // eg: Grayhill 61R256
	static final double DRIVE_GEAR_REDUCTION = 30/54; // Vex Pro 3 Sim Shifter Gear Ratio
	static final double WHEEL_DIAMETER_INCHES = 4.0; // For figuring circumference
	static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * PI);

	static final double CM_PER_INCH = 2.54;
	static final double COUNTS_PER_CM = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)
			/ ((WHEEL_DIAMETER_INCHES * CM_PER_INCH) * PI);

	private Object imgLock = new Object();
	private VisionThread visionThread;
	private double centerX = 0.0;

	public static final double maxSpeed = 0.8;
	public static final double minSpeed = 0.6;
	
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

		Robot.driveSubsystem.driveReset();
		Robot.driveSubsystem.setAutoMode();
		if (UseGyro) {
			Robot.gyroSubsystem.reset();
			StartAngle = Robot.gyroSubsystem.GyroPosition();
		} else {
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

		// get current encoder counts
		CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();
		CurrentLeftEncoderPos = Robot.driveSubsystem.leftGetRawCount();

		// calculate target position
		TargetEncoderPos = (Distance * (int) COUNTS_PER_INCH);

		// SmartDashboard.putNumber("Right ", CurrentRightEncoderPos);
		// SmartDashboard.putNumber("Left ", CurrentLeftEncoderPos);
		// SmartDashboard.putNumber("DF distance", Distance);
		// SmartDashboard.putNumber("DF Target", TargetEncoderPos);

		// setup the PID system
		Robot.minipidSubsystem.reset();
		Robot.minipidSubsystem.setSetpoint(TargetEncoderPos);
		Robot.minipidSubsystem.setOutputLimits(-80, 80);

		HighLimit = TargetEncoderPos + ErrorLimit;
		LowLimit = TargetEncoderPos - ErrorLimit;

		Robot.minipidSubsystem.setP(0.2);
		Robot.minipidSubsystem.setI(0);
		Robot.minipidSubsystem.setD(0);
		// Robot.minipidSubsystem.setP(Kp);
		// Robot.minipidSubsystem.setI(0);
		// Robot.minipidSubsystem.setD(0);

		ourJobIsDoneHere = false;
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
		double angle;

		CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();
		CurrentLeftEncoderPos = Robot.driveSubsystem.leftGetRawCount();

		// get motor speed from PID loop
		MotorSpeed = Robot.minipidSubsystem.getOutput(CurrentRightEncoderPos, TargetEncoderPos) / 100;

		// Use gyro to calculate our turn value
		if (UseGyro) {
			angle = -(StartAngle - Robot.gyroSubsystem.GyroPosition());
			angle *= Kp;
		} 
		
		// use camera image to calculate our turn value
		else {
			double localCenterX = getCenterX();
			double distanceFromCenter = (localCenterX - (Robot.IMG_WIDTH / 2));

			// get distance from center of image by percentage
			angle = -(distanceFromCenter / (Robot.IMG_WIDTH / 2));
			SmartDashboard.putNumber("CenterX in Drive Straight", localCenterX);

			// cap the maximum turn speed
			if (Math.abs(angle) > maxSpeed) {
				if (angle < 0)
					angle = -maxSpeed;
				else if (angle > 0)
					angle = maxSpeed;
			} 
			 
			else if (Math.abs(angle) < minSpeed) {
				if (angle < 0)
					angle = -minSpeed;
				else if (angle > 0)
					angle = minSpeed;
			}
		}

		// cap the maximum forward speed
		if (0 <= MotorSpeed && MotorSpeed < MinMotorSpeed)
			MotorSpeed = MinMotorSpeed;
		else if (-MinMotorSpeed < MotorSpeed && MotorSpeed <= 0)
			MotorSpeed = -MinMotorSpeed;

		SmartDashboard.putNumber("Right    ", CurrentRightEncoderPos);
		SmartDashboard.putNumber("Angle    ", angle);
		SmartDashboard.putNumber("Angle*Kp ", angle * Kp);

		Robot.driveSubsystem.arcadeDrive(-MotorSpeed, angle);
	}

	@Override
	protected boolean isFinished() {
		// reset the return value
		ourJobIsDoneHere = false;

		// TODO Auto-generated method stub
		CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();

		SmartDashboard.putNumber("current right position", CurrentRightEncoderPos);
		SmartDashboard.putNumber("target position", TargetEncoderPos);
		SmartDashboard.putNumber("MOTOR SPEED", MotorSpeed);

		if (!ourJobIsDoneHere) {
			if (Distance > 0) {
				if (CurrentRightEncoderPos >= TargetEncoderPos && TargetEncoderPos != 0) {
					ourJobIsDoneHere = true;
				}
			} else if (Distance < 0) {
				if (CurrentRightEncoderPos <= TargetEncoderPos && TargetEncoderPos != 0) {
					ourJobIsDoneHere = true;
				}
			} else {
				ourJobIsDoneHere = true;
			}
		}
		
		if (ourJobIsDoneHere && !UseGyro) 
			camera.setBrightness(100);
		return ourJobIsDoneHere;
	}

	@Override
	protected void end() {

		// stop driving and put us back in teleop mode
		Robot.driveSubsystem.arcadeDrive(0, 0);
		ourJobIsDoneHere = false;

		// raise the brightness if using camera to drive straight
		if (!UseGyro) {
			camera.setBrightness(100);
		}
	}

	@Override
	protected void interrupted() {
		end();
		// TODO Auto-generated method stub

	}

}
