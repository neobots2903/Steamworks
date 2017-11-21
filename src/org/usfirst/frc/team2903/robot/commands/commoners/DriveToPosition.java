package org.usfirst.frc.team2903.robot.commands.commoners;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.subsystems.Drive2903;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;


public class DriveToPosition extends Command {

	int Distance;
	
	int DistanceInTicks;
	
	static final double PI = 3.14159;
	static final int COUNTS_PER_MOTOR_REV = 1024; // eg: Grayhill 61R256
	static final double DRIVE_GEAR_REDUCTION = 0.5; // This is < 1.0 if geared
													// UP
	static final double WHEEL_DIAMETER_INCHES = 4.5; // For figuring// circumference
	
	//change parameter to number of rotations instead of counts per inch
	static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * PI);
	
	public DriveToPosition(int distance) {
		super("DriveToPosition");
		
		Distance = distance;
		
		DistanceInTicks = (int)((double)distance * COUNTS_PER_INCH);

	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		Robot.driveSubsystem.rightFrontMotor.changeControlMode(TalonControlMode.Position);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		Robot.driveSubsystem.rightFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		Robot.driveSubsystem.rightFrontMotor.setPID(0.5, 0.0001, 0.0);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		Robot.driveSubsystem.rightFrontMotor.set(DistanceInTicks);
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
