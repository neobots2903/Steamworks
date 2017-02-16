package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraightForDistance extends Command {

	// TODO update for two encoders with average of the two and gyro 
	
	int TargetEncoderPos;
	int CurrentEncoderPos;
	int Distance;
	
	static final double		PI						= 3.14159;
	static final double COUNTS_PER_MOTOR_REV = 256; //Quad Encoder
	static final double DRIVE_GEAR_REDUCTION = 3; 
	static final double WHEEL_DIAMETER_INCHES = 4.0;
	static final double COUNTS_PER_INCH = ((COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / 
			(WHEEL_DIAMETER_INCHES * 3.141595));
	 static final double 	CM_PER_INCH             = 2.54;
	 static final double 	COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
				((WHEEL_DIAMETER_INCHES * CM_PER_INCH) * PI);
	
	public DriveStraightForDistance(int distance)
	{
		super("DriveForward");
		
		requires(Robot.driveSubsystem);
		
		Distance = distance;
		CurrentEncoderPos = Math.abs(Robot.driveSubsystem.rightGetRawCount());
		TargetEncoderPos =  distance * (int) COUNTS_PER_INCH + CurrentEncoderPos; 
		SmartDashboard.putNumber("DFCEP", CurrentEncoderPos);
		SmartDashboard.putNumber("DF distance", distance);
		SmartDashboard.putNumber("DFTEP", TargetEncoderPos);
		 		
	}
	
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		Robot.driveSubsystem.setPosition(TargetEncoderPos);

		CurrentEncoderPos = Math.abs(Robot.driveSubsystem.rightGetRawCount());
		TargetEncoderPos =  Distance * (int) COUNTS_PER_INCH + CurrentEncoderPos; 
		SmartDashboard.putNumber("DFINITCEP", CurrentEncoderPos);
		SmartDashboard.putNumber("DFINIT distance", Distance);
		SmartDashboard.putNumber("DFINITTEP", TargetEncoderPos);


	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		Robot.driveSubsystem.tankDrive(0.5, -0.5);

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		CurrentEncoderPos = Math.abs(Robot.driveSubsystem.rightGetRawCount());
		SmartDashboard.putNumber("current position",CurrentEncoderPos);
		SmartDashboard.putNumber("target position",TargetEncoderPos);
		if (CurrentEncoderPos >= TargetEncoderPos){
			Robot.driveSubsystem.arcadeDrive(0, 0);
			return true;
		}
		return false;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.arcadeDrive(0, 0);
		CurrentEncoderPos = 0;
		TargetEncoderPos = 0;
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		Robot.driveSubsystem.arcadeDrive(0, 0);
		// TODO Auto-generated method stub

	}

}
