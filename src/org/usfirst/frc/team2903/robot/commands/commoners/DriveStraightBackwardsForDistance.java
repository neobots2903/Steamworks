package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveStraightBackwardsForDistance extends Command {

	// TODO update for two encoders with average of the two and gyro 

	double HighLimit;
	double LowLimit;
	double ErrorLimit = 3;

	int TargetEncoderPos;
	int CurrentRightEncoderPos;
	int CurrentLeftEncoderPos;
	private int Distance;
	
	double MinMotorSpeed = 0.3;
	private double Kp = 0.03;

	static final double		PI						= 3.14159;
	static final double COUNTS_PER_MOTOR_REV = 4096; //Quad Encoder
	static final double DRIVE_GEAR_REDUCTION = 0.5; 
	static final double WHEEL_DIAMETER_INCHES = 6.0;
	static final double COUNTS_PER_INCH = ((COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / 
			(WHEEL_DIAMETER_INCHES * 3.141595));
	 static final double 	CM_PER_INCH             = 2.54;
	 static final double 	COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
				((WHEEL_DIAMETER_INCHES * CM_PER_INCH) * PI);
	
	 //distance in inches 
	public DriveStraightBackwardsForDistance(int distance)
	{
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);
		
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
		
		// initialize the gyro
		Robot.gyroSubsystem.reset();
		Robot.gyroSubsystem.Calibrate();
		
		// get current encoder counts
		CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();
		CurrentLeftEncoderPos = Robot.driveSubsystem.leftGetRawCount();
		
		// calculate target position
		TargetEncoderPos =  (Distance * (int) COUNTS_PER_INCH);// + Robot.driveSubsystem.rightGetRawCount(); 
		
//		SmartDashboard.putNumber("Right ", CurrentRightEncoderPos);
//		SmartDashboard.putNumber("Left ", CurrentLeftEncoderPos);
//		SmartDashboard.putNumber("DF distance", Distance);
//		SmartDashboard.putNumber("DF Target", TargetEncoderPos);

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

		
		CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();
		CurrentLeftEncoderPos = Robot.driveSubsystem.leftGetRawCount();

		double MotorSpeed = Robot.minipidSubsystem.getOutput(CurrentRightEncoderPos, TargetEncoderPos) / 100;
		
		if (0 <= MotorSpeed && MotorSpeed < MinMotorSpeed)
			MotorSpeed = MinMotorSpeed;
		else if (-MinMotorSpeed < MotorSpeed && MotorSpeed <= 0)
			MotorSpeed = -MinMotorSpeed;

		double angle = Robot.gyroSubsystem.GyroPosition();
	
		//SmartDashboard.putNumber("Right ", CurrentRightEncoderPos);
		//SmartDashboard.putNumber("Left ", CurrentLeftEncoderPos);
		//SmartDashboard.putNumber("Angle", angle);
		
		// TODO Auto-generated method stub
		Robot.driveSubsystem.arcadeDrive(-MotorSpeed, -angle * Kp);
		Timer.delay(0.01);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		CurrentRightEncoderPos = Robot.driveSubsystem.rightGetRawCount();
		CurrentLeftEncoderPos = Robot.driveSubsystem.leftGetRawCount();
		
//		SmartDashboard.putNumber("current right position", CurrentRightEncoderPos);
//		SmartDashboard.putNumber("current left position",CurrentLeftEncoderPos);
//		SmartDashboard.putNumber("target position",TargetEncoderPos);
		
		if (CurrentRightEncoderPos >= LowLimit && CurrentRightEncoderPos <= HighLimit ) 
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
