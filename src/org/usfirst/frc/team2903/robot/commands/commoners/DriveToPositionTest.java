package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveToPositionTest extends Command {

	// TODO update for two encoders with average of the two and gyro 

	double HighLimit;
	double LowLimit;
	double ErrorLimit = 6;

	int TargetEncoderPos;
	int CurrentRightEncoderPos;
	int CurrentLeftEncoderPos;
	private int Distance;
	
	double MinMotorSpeed = 0.3;

	static final double		PI						= 3.14159;
	
	static final double COUNTS_PER_MOTOR_REV = 1024; //Quad Encoder
	static final double DRIVE_GEAR_REDUCTION = 1.1; 
	static final double WHEEL_DIAMETER_INCHES = 4.0;
	static final double WHEEL_CIRCUMFERENCE_INCHES = WHEEL_DIAMETER_INCHES * PI;
	
	static final double COUNTS_PER_INCH = ((COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / 
			(WHEEL_DIAMETER_INCHES * 3.141595));

	//distance in inches 
	public DriveToPositionTest(int distance)
	{
		super("DriveToPositionTest");
		
		requires(Robot.driveSubsystem);
		Distance = distance;
	}
	
	
	@Override
	protected void initialize() {

		Robot.driveSubsystem.driveReset();
		Robot.driveSubsystem.setAutoPositionMode();
		
		// get current encoder counts
		CurrentRightEncoderPos = Math.abs(Robot.driveSubsystem.rightGetRawCount());
		
		// calculate target position
		TargetEncoderPos =  (Distance * (int) COUNTS_PER_INCH) + Math.abs(Robot.driveSubsystem.rightGetRawCount()); 
		
		SmartDashboard.putNumber("Right ", Math.abs(Robot.driveSubsystem.rightGetRawCount()));
		SmartDashboard.putNumber("DF distance", Distance);
		SmartDashboard.putNumber("DF Target", TargetEncoderPos);

		HighLimit = TargetEncoderPos + ErrorLimit;
		LowLimit = TargetEncoderPos - ErrorLimit;
		
		Robot.driveSubsystem.setPosition(-TargetEncoderPos);

	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("current right position", Math.abs(Robot.driveSubsystem.rightGetRawCount()));
		SmartDashboard.putNumber("target position",TargetEncoderPos);
	}

	@Override
	protected boolean isFinished() {
		// get current encoder counts
		CurrentRightEncoderPos = Math.abs(Robot.driveSubsystem.rightGetRawCount());

		// within our error window?
		if (LowLimit <= CurrentRightEncoderPos && CurrentRightEncoderPos <= HighLimit ) 
			return true;
		else
			return false;
	}

	@Override
	protected void end() {
		// stop the robot
		Robot.driveSubsystem.arcadeDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		// stop the robot
		Robot.driveSubsystem.arcadeDrive(0, 0);
	}

}
