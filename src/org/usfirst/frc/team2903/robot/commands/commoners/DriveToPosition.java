package org.usfirst.frc.team2903.robot.commands.commoners;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.subsystems.Drive2903;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;


public class DriveToPosition extends Command {

	int Distance;
	
	int DistanceInRevolutions;
	
	static final double PI = 3.14159;
	static final int COUNTS_PER_MOTOR_REV = 1024; // eg: Grayhill 61R256
	static final double DRIVE_GEAR_REDUCTION = 8; // This is < 1.0 if geared
													// UP
	static final double WHEEL_DIAMETER_INCHES = 4.5; // For figuring// circumference
	
	//change parameter to number of rotations instead of counts per inch
	//static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * PI);
	static final double INCHES_PER_ENCODER_REV = (WHEEL_DIAMETER_INCHES* PI)/DRIVE_GEAR_REDUCTION;
	
	public DriveToPosition(int distance) {
		super("DriveToPosition");
		
		Distance = distance;
		
		DistanceInRevolutions = (int)Math.round((double)distance/INCHES_PER_ENCODER_REV);
		
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		Robot.driveSubsystem.rightFrontMotor.reset();
		
		Robot.driveSubsystem.rightFrontMotor.changeControlMode(TalonControlMode.Position);
		Robot.driveSubsystem.rightRearMotor.changeControlMode(TalonControlMode.Follower);
		Robot.driveSubsystem.leftFrontMotor.changeControlMode(TalonControlMode.Follower);
		Robot.driveSubsystem.rightRearMotor.changeControlMode(TalonControlMode.Follower);
		
		// have the motors follow rightFrontMotor
		Robot.driveSubsystem.rightFrontMotor.setPosition(0);
		Robot.driveSubsystem.leftFrontMotor.set(Robot.driveSubsystem.rightFrontMotor.getDeviceID());
		Robot.driveSubsystem.leftRearMotor.set(Robot.driveSubsystem.rightFrontMotor.getDeviceID());
		Robot.driveSubsystem.rightRearMotor.set(Robot.driveSubsystem.rightFrontMotor.getDeviceID());
				
				//Reset the encoder to zero as its current position
//		Robot.driveSubsystem.rightFrontMotor.setPosition(0);
		//Robot.driveSubsystem.rightFrontMotor.setEncPosition(0);
//				leftFrontMotor.setPosition(0);
//				leftFrontMotor.setEncPosition(0);	
		
		Robot.driveSubsystem.rightFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		Robot.driveSubsystem.rightFrontMotor.setPID(0.5, 0.0001, 0.0);
		//Robot.driveSubsystem.rightFrontMotor.setPID(0.5, 0.001, 0.00, 0.00, 360, 36, 0);
		
		Robot.driveSubsystem.rightFrontMotor.enableControl();
		Robot.driveSubsystem.rightFrontMotor.set(DistanceInRevolutions);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Drive   ", DistanceInRevolutions);
		SmartDashboard.putNumber("Right E ", Robot.driveSubsystem.rightGetRawCount());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
