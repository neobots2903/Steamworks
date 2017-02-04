/**
 * 
 */
package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.subsystems.Drive2903;
import org.usfirst.frc.team2903.robot.subsystems.Gyro2903;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2903.robot.*;

/**
 * @author robotics
 *
 */
public class TurnWithGyro extends Command{

	double TargetAngle = 0;

	double CumulativeAngle = 0;
	
	double HighLimit;
	double LowLimit;
	double ErrorLimit = 1.0;
	double MotorSpeed = 0.6;
	double MinMotorSpeed = 0.3;
	double ReadjustMotorSpeed = 0;
	boolean PreviousTurnLeft = false;
	boolean TurnLeft = false;
	
	double AccumulatedAngle = 0;

	public TurnWithGyro(double targetangle) {
		setTargetAngle(targetangle);
		
		// handle negative target angles
		// handle cumulative angle management to handle 0 -> 359 or 359 -> 0
		if (targetangle < 0){
			TurnLeft = true;
		}
		else {
			TurnLeft = false;
		}
		
		// setup the PID system
		Robot.minipidSubsystem.reset();
		Robot.minipidSubsystem.setSetpoint(TargetAngle);
		Robot.minipidSubsystem.setOutputLimits(-80, 80);
	}

	public  double getTargetAngle() {
		return TargetAngle;
	}

	public void setTargetAngle(double targetAngle) {
		TargetAngle = targetAngle;

		// adjust angle down to one circle
		//TargetAngle = TargetAngle

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
		double gyroAngle = (Robot.gyroSubsystem.GyroPosition()*-1.0);
		boolean turnLeft = false;
		
		MotorSpeed = Robot.minipidSubsystem.getOutput(gyroAngle, TargetAngle) / 100;
		
		SmartDashboard.putNumber("TurnWithGyro", gyroAngle);
		
		// TODO Auto-generated method stub
//		if (TargetAngle <= 0) {
//			if (gyroAngle > TargetAngle)
//				turnLeft = true;
//			if (gyroAngle < TargetAngle)
//				turnLeft = false;
//		}
//		else{
//			if (gyroAngle > TargetAngle)
//				turnLeft = false;
//			if (gyroAngle < TargetAngle)
//				turnLeft = true;
//		}
//		
//		// we are readjusting the angle, lower the readjustment speed
//		if (turnLeft != PreviousTurnLeft) {
//			MotorSpeed = ReadjustMotorSpeed;
//			PreviousTurnLeft = turnLeft;
//		}
		
		if (0 <= MotorSpeed && MotorSpeed < MinMotorSpeed)
			MotorSpeed = MinMotorSpeed;
		else if (-MinMotorSpeed < MotorSpeed && MotorSpeed <= 0)
			MotorSpeed = -MinMotorSpeed;
		
		SmartDashboard.putNumber("Taget Angle", TargetAngle);
		SmartDashboard.putNumber("MotorSpeed = ", MotorSpeed);
		Robot.driveSubsystem.tankDrive(-MotorSpeed, -MotorSpeed);
//		if (!turnLeft)
//			// turn right
//			Robot.driveSubsystem.tankDrive(MotorSpeed * -1.0, MotorSpeed * -1.0);
//		else 
//			// turn left
//			Robot.driveSubsystem.tankDrive(MotorSpeed, MotorSpeed);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		double gyroAngle = (Robot.gyroSubsystem.GyroPosition()*-1.0);
		
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