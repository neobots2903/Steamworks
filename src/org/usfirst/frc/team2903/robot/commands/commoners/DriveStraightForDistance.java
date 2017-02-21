package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraightForDistance extends Command {

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
	static final double DRIVE_GEAR_REDUCTION = 1.0; 
	static final double WHEEL_DIAMETER_INCHES = 4.0;
	static final double COUNTS_PER_INCH = ((COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / 
			(WHEEL_DIAMETER_INCHES * 3.141595));
	 static final double 	CM_PER_INCH             = 2.54;
	 static final double 	COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
				((WHEEL_DIAMETER_INCHES * CM_PER_INCH) * PI);
	
	 //distance in inches 
	public DriveStraightForDistance(int distance)
	{
		super("DriveForward");
		
		requires(Robot.driveSubsystem);
		Robot.driveSubsystem.driveReset();
		Robot.driveSubsystem.setAutoMode();
		Distance = distance;
	}
	
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		//Robot.driveSubsystem.setPosition(TargetEncoderPos);

		Robot.driveSubsystem.driveReset();
		Robot.driveSubsystem.setAutoMode();
		
		
		// get current encoder counts
		CurrentRightEncoderPos = Math.abs(Robot.driveSubsystem.rightGetRawCount());
		CurrentLeftEncoderPos = Math.abs(Robot.driveSubsystem.leftGetRawCount());
		
		// calculate target position
		TargetEncoderPos =  (Distance * (int) COUNTS_PER_INCH) + Math.abs(Robot.driveSubsystem.rightGetRawCount()); 
		
		SmartDashboard.putNumber("Right ", Math.abs(Robot.driveSubsystem.rightGetRawCount()));
		SmartDashboard.putNumber("Left ", CurrentLeftEncoderPos);
		SmartDashboard.putNumber("DF distance", Distance);
		SmartDashboard.putNumber("DF Target", TargetEncoderPos);

		// setup the PID system
		Robot.minipidSubsystem.reset();
		Robot.minipidSubsystem.setSetpoint(TargetEncoderPos);
		Robot.minipidSubsystem.setOutputLimits(-80, 80);

		HighLimit = TargetEncoderPos + ErrorLimit;
		LowLimit = TargetEncoderPos - ErrorLimit;
		
		Robot.minipidSubsystem.setP(0.2);
		Robot.minipidSubsystem.setI(0);
		Robot.minipidSubsystem.setD(0);
		
	
		

	}

	public int getDistance() {
		return Distance;
	}


	public void setDistance(int distance) {
		Distance = distance;
	}


	@Override
	protected void execute() {
		CurrentRightEncoderPos = Math.abs(Robot.driveSubsystem.rightGetRawCount());
		double MotorSpeed = Robot.minipidSubsystem.getOutput(CurrentRightEncoderPos, TargetEncoderPos) / 100;
		
		if (0 <= MotorSpeed && MotorSpeed < MinMotorSpeed)
			MotorSpeed = MinMotorSpeed;
		else if (-MinMotorSpeed < MotorSpeed && MotorSpeed <= 0)
			MotorSpeed = -MinMotorSpeed;
		
		// TODO Auto-generated method stub
		Robot.driveSubsystem.tankDrive(MotorSpeed, -MotorSpeed);

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		CurrentRightEncoderPos = Math.abs(Robot.driveSubsystem.rightGetRawCount());
		CurrentLeftEncoderPos = Math.abs(Robot.driveSubsystem.leftGetRawCount());
		
		SmartDashboard.putNumber("current right position", Math.abs(Robot.driveSubsystem.rightGetRawCount()));
//		SmartDashboard.putNumber("current left position",CurrentLeftEncoderPos);
		SmartDashboard.putNumber("target position",TargetEncoderPos);
		
		if (Math.abs(Robot.driveSubsystem.rightGetRawCount()) >= LowLimit && Math.abs(Robot.driveSubsystem.rightGetRawCount()) <= HighLimit ) 
			return true;
		else
			return false;

//		if (Math.abs(Robot.driveSubsystem.rightGetRawCount()) >= TargetEncoderPos){
//			Robot.driveSubsystem.arcadeDrive(0, 0);
//			return true;
//		}
//		return false;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.arcadeDrive(0, 0);
//		CurrentRightEncoderPos = 0;
//		TargetEncoderPos = 0;
		// TODO Auto-generated method stub
		//putting drives back into teleop mode
		Robot.driveSubsystem.setTeleopMode();
	}

	@Override
	protected void interrupted() {
		Robot.driveSubsystem.arcadeDrive(0, 0);
		// TODO Auto-generated method stub

	}

}
