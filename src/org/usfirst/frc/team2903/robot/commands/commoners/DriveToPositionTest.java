package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToPositionTest extends Command {

	// TODO update for two encoders with average of the two and gyro 

	double HighLimit;
	double LowLimit;
	double ErrorLimit = 6;

	int TargetEncoderPos;
	int CurrentRightEncoderPos;
	int CurrentLeftEncoderPos;
	private double Distance;
	
	double MinMotorSpeed = 0.3;

	static final double		PI						= 3.14159;
	
	static final double COUNTS_PER_MOTOR_REV = 4096; //Quad Encoder
	static final double DRIVE_GEAR_REDUCTION = 4.0; 
	static final double WHEEL_DIAMETER_INCHES = 6.0;
	static final double WHEEL_CIRCUMFERENCE_INCHES = WHEEL_DIAMETER_INCHES * PI;
	
	static final double COUNTS_PER_INCH = ((COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / 
			(WHEEL_DIAMETER_INCHES * PI));

	//distance in inches 
	public DriveToPositionTest(double distance)
	{
		super("DriveToPositionTest");
		
		requires(Robot.driveSubsystem);
		Distance = distance;
	}
	
	
	@Override
	protected void initialize() {

		Robot.driveSubsystem.driveReset();
		
		// get current encoder counts
		CurrentRightEncoderPos = Math.abs(Robot.driveSubsystem.rightGetRawCount());
//		CurrentRightEncoderPos = Math.abs(Robot.driveSubsystem.rightGetCount());
		
		// calculate target position
		TargetEncoderPos =  (int) ((Distance * COUNTS_PER_INCH));// + Math.abs(Robot.driveSubsystem.rightGetRawCount())) ; 
//		TargetEncoderPos =  (int) ((Distance * COUNTS_PER_INCH) + Math.abs(Robot.driveSubsystem.rightGetCount())) ; 
		
//		SmartDashboard.putNumber("Right ", Math.abs(Robot.driveSubsystem.rightGetRawCount()));
//		SmartDashboard.putNumber("DP distance", Distance);
//		SmartDashboard.putNumber("DP Target", TargetEncoderPos);
//		SmartDashboard.putNumber("Right ", Math.abs(Robot.driveSubsystem.rightGetCount()));
//		SmartDashboard.putNumber("DP distance", Distance);
//		SmartDashboard.putNumber("DP Target", TargetEncoderPos);

		HighLimit = TargetEncoderPos + ErrorLimit;
		LowLimit = TargetEncoderPos - ErrorLimit;
	}

	@Override
	protected void execute() {
//		SmartDashboard.putNumber("R DP curpos", Math.abs(Robot.driveSubsystem.rightGetRawCount()));
//		SmartDashboard.putNumber("L DP curpos", Math.abs(Robot.driveSubsystem.leftGetRawCount()));
//		SmartDashboard.putNumber("DP curpos", Math.abs(Robot.driveSubsystem.rightGetCount()));
	}

	@Override
	protected boolean isFinished() {
		// get current encoder counts
		CurrentRightEncoderPos = Math.abs(Robot.driveSubsystem.rightGetRawCount());
//		CurrentRightEncoderPos = Math.abs(Robot.driveSubsystem.rightGetCount());

		// within our error window?
//		if (LowLimit <= CurrentRightEncoderPos && CurrentRightEncoderPos <= HighLimit ) 
//			return true;
//		else
			return false;
	}

	@Override
	protected void end() {
		// stop the robot
//		Robot.driveSubsystem.setLastRightRaw(Robot.driveSubsystem.rightGetRawCount());
		Robot.driveSubsystem.arcadeDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		// stop the robot
		end();
	}

}
