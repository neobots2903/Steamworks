package org.usfirst.frc.team2903.robot.commands.commoners;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.subsystems.GearPegPipeline2903;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class DriveStraightForDistance extends Command {

	// TODO update for two encoders with average of the two and gyro

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

	private double Kp = 0.03;
	private double MotorSpeed;
	private double StartAngle;
	private boolean UseGyro;

	static final double		PI						= 3.14159;
    static final int     COUNTS_PER_MOTOR_REV    = 1024 ;    // eg: Grayhill 61R256
    static final double     DRIVE_GEAR_REDUCTION    = 1.1 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            												(WHEEL_DIAMETER_INCHES * PI);
    
    static final double 	CM_PER_INCH             = 2.54;
    static final double 	COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
    													((WHEEL_DIAMETER_INCHES * CM_PER_INCH) * PI);
    
	private Object imgLock = new Object();
	private VisionThread visionThread;
	private double centerX = 0.0;
	
	public static final double minSpeed = 0.45;
	public static final double maxSpeed = 0.6;

    
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
		AxisCamera camera;
		
		Robot.driveSubsystem.driveReset();
		Robot.driveSubsystem.setAutoMode();
		if (UseGyro){
			Robot.gyroSubsystem.reset();
			StartAngle = Robot.gyroSubsystem.GyroPosition();

			// setup the PID system
			Robot.minipidSubsystem.reset();
			Robot.minipidSubsystem.setSetpoint(TargetEncoderPos);
			Robot.minipidSubsystem.setOutputLimits(-80, 80);
			Robot.minipidSubsystem.setP(0.2);
			Robot.minipidSubsystem.setI(0);
			Robot.minipidSubsystem.setD(0);
		}
		else{
			camera = CameraServer.getInstance().addAxisCamera("10.29.3.56");
			
			camera.setResolution(Robot.IMG_WIDTH, Robot.IMG_HEIGHT);
		
			visionThread = new VisionThread(camera, new GearPegPipeline2903(), pipeline -> {
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
		TargetEncoderPos = (Distance * (int) COUNTS_PER_INCH);// +
																// Robot.driveSubsystem.rightGetRawCount();

		// SmartDashboard.putNumber("Right ", CurrentRightEncoderPos);
		// SmartDashboard.putNumber("Left ", CurrentLeftEncoderPos);
		// SmartDashboard.putNumber("DF distance", Distance);
		// SmartDashboard.putNumber("DF Target", TargetEncoderPos);

		HighLimit = TargetEncoderPos + ErrorLimit;
		LowLimit = TargetEncoderPos - ErrorLimit;

		ourJobIsDoneHere = false;
	}
	
	public double getCenterX() {
		double localCenterX;
		synchronized (imgLock) {
			localCenterX = this.centerX;
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

		if (UseGyro){
			MotorSpeed = Robot.minipidSubsystem.getOutput(CurrentRightEncoderPos, TargetEncoderPos) / 100;
			angle = -(StartAngle - Robot.gyroSubsystem.GyroPosition());
		}
		else{
			double localCenterX = getCenterX();
			double distanceFromCenter = (localCenterX - (Robot.IMG_WIDTH / 2));
			angle = distanceFromCenter / (Robot.IMG_WIDTH / 2);
			if (Math.abs(angle) > maxSpeed) {
				if (angle < 0)
					angle = -maxSpeed;
				else 
					angle = maxSpeed;
			} else if (Math.abs(angle) < minSpeed) {
				if (angle < 0)
					angle = -minSpeed;
				else 
					angle = minSpeed;
			}
		}

		if (0 <= MotorSpeed && MotorSpeed < MinMotorSpeed)
			MotorSpeed = MinMotorSpeed;
		else if (-MinMotorSpeed < MotorSpeed && MotorSpeed <= 0)
			MotorSpeed = -MinMotorSpeed;
		

		SmartDashboard.putNumber("Right ", CurrentRightEncoderPos);
		SmartDashboard.putNumber("Left ", CurrentLeftEncoderPos);
		SmartDashboard.putNumber("Angle", angle);

		// TODO Auto-generated method stub
		Robot.driveSubsystem.arcadeDrive(-MotorSpeed, -angle * Kp);
		Timer.delay(0.01);
	}

	@Override
	protected boolean isFinished() {
		// reset the return value
		ourJobIsDoneHere = false;

		// TODO Auto-generated method stub
		CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();
		CurrentLeftEncoderPos = Robot.driveSubsystem.leftGetRawCount();

		SmartDashboard.putNumber("current right position", CurrentRightEncoderPos);
		SmartDashboard.putNumber("current left position", CurrentLeftEncoderPos);
		SmartDashboard.putNumber("target position", TargetEncoderPos);
		SmartDashboard.putNumber("MOTOR SPEED", MotorSpeed);

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

		return ourJobIsDoneHere;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.arcadeDrive(0, 0);
		// CurrentRightEncoderPos = 0;
		// TargetEncoderPos = 0;
		// TODO Auto-generated method stub
		// putting drives back into teleop mode
		Robot.driveSubsystem.setTeleopMode();
		ourJobIsDoneHere = false;
	}

	@Override
	protected void interrupted() {
		end();
		// TODO Auto-generated method stub

	}

}
