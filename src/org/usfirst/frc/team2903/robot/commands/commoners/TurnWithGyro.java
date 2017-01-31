/**
 * 
 */
package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.subsystems.Drive2903;
import org.usfirst.frc.team2903.robot.subsystems.Gyro2903;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2903.robot.*;

/**
 * @author robotics
 *
 */
public class TurnWithGyro extends Command{

	double TargetAngle = 0;

	double HighLimit;
	double LowLimit;
	double ErrorLimit = 0.1;
	double MotorSpeed = 0.6;
	double ReadjustMotorSpeed = 0;
	boolean PreviousTurnLeft = false;

	public TurnWithGyro(double targetangle) {
		setTargetAngle(targetangle);
	}

	public  double getTargetAngle() {
		return TargetAngle;
	}

	public void setTargetAngle(double targetAngle) {
		TargetAngle = targetAngle;

		// adjust angle down to one circle
		TargetAngle = TargetAngle % 360;

		// set the limits for adjustment
		HighLimit = TargetAngle + ErrorLimit;
		LowLimit = TargetAngle - ErrorLimit;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
//		Robot.gyroSubsystem.gyro.reset();
	}

	@Override
	protected void execute() {
		double gyroAngle = Robot.gyroSubsystem.GyroPosition() % 360;
		boolean turnLeft = false;
		
		// TODO Auto-generated method stub
		if (TargetAngle <= 0) {
			if (gyroAngle > TargetAngle)
				turnLeft = true;
			if (gyroAngle < TargetAngle)
				turnLeft = false;
		}
		else{
			if (gyroAngle > TargetAngle)
				turnLeft = false;
			if (gyroAngle < TargetAngle)
				turnLeft = true;
		}
		
		// we are readjusting the angle, lower the readjustment speed
		if (turnLeft != PreviousTurnLeft) {
			MotorSpeed = ReadjustMotorSpeed;
			PreviousTurnLeft = turnLeft;
		}
		
		if (!turnLeft)
			// turn right
			Robot.driveSubsystem.tankDrive(MotorSpeed * -1.0, MotorSpeed * -1.0);
		else 
			// turn left
			Robot.driveSubsystem.tankDrive(MotorSpeed, MotorSpeed);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		double gyroAngle = Robot.gyroSubsystem.GyroPosition() % 360;
		
		if (gyroAngle >= LowLimit && gyroAngle <= HighLimit ) 
			return true;
		else
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