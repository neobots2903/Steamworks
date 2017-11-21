/*
// ANDREW 
 * 
 * We can remove all left side encoder stuff.  Other than that, the COUNTS_PER_MOTOR_REVOLUTION 
 * looks correct. 
 * 
 * We can remove all of the Centimeter related stuff as well. 
*/

package org.usfirst.frc.team2903.robot.commands.commoners;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.subsystems.GearPegPipeline2903;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
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
	private int Distance = 0;

	boolean ourJobIsDoneHere = false;

	double MinMotorSpeed = 0.3;
	double MaxMotorSpeed = 0.5;

	private double Kp = 0.3;
	private double MotorSpeed;
	private double StartAngle;
	private boolean UseGyro;
	boolean useLimit;

	static final double PI = 3.14159;
	static final int COUNTS_PER_MOTOR_REV = 1024; // eg: Grayhill 61R256
	static final double DRIVE_GEAR_REDUCTION = 0.5; // This is < 1.0 if geared
													// UP
	static final double WHEEL_DIAMETER_INCHES = 4.5; // For figuring// circumference
	
	//change parameter to number of rotations instead of counts per inch
	static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * PI);

	private Object imgLock = new Object();
	private VisionThread visionThread;
	private double centerX = 0.0;

	public static final double maxSpeed = 0.8;
	public static final double minSpeed = 0.6;

	AxisCamera camera;

	// distance in inches
	public DriveStraightForDistance(int distance, boolean useGyro, boolean useLimitSwitch) {
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);

		Distance = distance;
		UseGyro = useGyro;

		// calculate target position
		TargetEncoderPos = (Distance * (int) COUNTS_PER_INCH) + Robot.driveSubsystem.rightGetRawCount();

		if (useLimitSwitch) {
			useLimit = true;
		} else {
			useLimit = false;
		}
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		// Robot.driveSubsystem.setPosition(TargetEncoderPos);

		Robot.driveSubsystem.driveReset();
		Robot.driveSubsystem.setAutoMode();
		if (UseGyro) {
			Robot.gyroSubsystem.reset();
		} else {
			camera = CameraServer.getInstance().addAxisCamera("10.29.3.56");

			camera.setResolution(Robot.IMG_WIDTH, Robot.IMG_HEIGHT);
			camera.setBrightness(0);

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
		// int CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();

		StartAngle = Robot.gyroSubsystem.GyroPosition();

		// setup the PID system
		Robot.minipidSubsystem.reset();
		Robot.minipidSubsystem.setSetpoint(TargetEncoderPos);

		Robot.minipidSubsystem.setOutputLimits(-80, 80);

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

		int CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();
		double angle;

		if (useLimit && !Robot.pnuematicsSubsystem.limitSwitchesPressed() && Distance < 0) {
			end();
		} else if (useLimit && Robot.pnuematicsSubsystem.limitSwitchesPressed() && Distance > 0) {
			end();
		} else {

			MotorSpeed = Robot.minipidSubsystem.getOutput(CurrentRightEncoderPos, TargetEncoderPos) / 100;
			if (UseGyro) {
				angle = -(StartAngle - Robot.gyroSubsystem.GyroPosition());
				angle *= Kp;
			} else {
				double localCenterX = getCenterX();
				double distanceFromCenter = (localCenterX - (Robot.IMG_WIDTH / 2));
				angle = -(distanceFromCenter / (Robot.IMG_WIDTH / 2));
				SmartDashboard.putNumber("CenterX in Drive Straight", localCenterX);
				if (Math.abs(angle) > maxSpeed) {
					if (angle < 0)
						angle = -maxSpeed;
					else if (angle > 0)
						angle = maxSpeed;
				} else if (Math.abs(angle) < minSpeed) {
					if (angle < 0)
						angle = -minSpeed;
					else if (angle > 0)
						angle = minSpeed;
				}
			}

			if (0 <= MotorSpeed && MotorSpeed < MinMotorSpeed)
				MotorSpeed = MinMotorSpeed;
			else if (-MinMotorSpeed < MotorSpeed && MotorSpeed <= 0)
				MotorSpeed = -MinMotorSpeed;

			SmartDashboard.putNumber("Right ", CurrentRightEncoderPos);
			SmartDashboard.putNumber("Angle", angle);

			// TODO Auto-generated method stub
			Robot.driveSubsystem.arcadeDrive(-MotorSpeed, -angle);
		}
	}

	@Override
	protected boolean isFinished() {
		// reset the return value
		ourJobIsDoneHere = false;

		// TODO Auto-generated method stub
		int CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();

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

		if (useLimit && !Robot.pressurePlateSwitch.get()) {
			ourJobIsDoneHere = true;
		}

		if (ourJobIsDoneHere && !UseGyro)
			camera.setBrightness(100);
		return ourJobIsDoneHere;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.arcadeDrive(0, 0);
		Robot.driveSubsystem.setTeleopMode();
		ourJobIsDoneHere = false;
	}

	@Override
	protected void interrupted() {
		end();
		// TODO Auto-generated method stub

	}

}
