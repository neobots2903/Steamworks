/**
 * 
 */
package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author robotics
 *
 */
public class TurnWithGyro extends Command{

	double TargetAngle = 0;
	double CumulativeAngle = 0;
	double HighLimit;
	double LowLimit;
	double ErrorLimit = 0.45;
	double MotorSpeed = 0.6;
	double MinMotorSpeed = 0.65;
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

		// set the limits for adjustment
		HighLimit = TargetAngle + ErrorLimit;
		LowLimit = TargetAngle - ErrorLimit;
	}

	@Override
	protected void initialize() {
		Robot.gyroSubsystem.reset();
		Robot.driveSubsystem.setAutoMode();
	}

	@Override
	protected void execute() {
		double gyroAngle = (Robot.gyroSubsystem.GyroPosition());
		MotorSpeed = Robot.minipidSubsystem.getOutput(gyroAngle, TargetAngle) / 100;
		
		SmartDashboard.putNumber("TurnWithGyro", gyroAngle);

		if (0 <= MotorSpeed && MotorSpeed < MinMotorSpeed)
			MotorSpeed = MinMotorSpeed;
		else if (-MinMotorSpeed < MotorSpeed && MotorSpeed <= 0)
			MotorSpeed = -MinMotorSpeed;
		
		SmartDashboard.putNumber("Taget Angle", TargetAngle);
		SmartDashboard.putNumber("MotorSpeed = ", MotorSpeed);
		Robot.driveSubsystem.tankDrive(-MotorSpeed, MotorSpeed);
//		Robot.driveSubsystem.arcadeDrive(MotorSpeed,0);

	}

	@Override
	protected boolean isFinished() {
		double gyroAngle = (Robot.gyroSubsystem.GyroPosition());
		
		if (gyroAngle >= LowLimit && gyroAngle <= HighLimit ) 
			return true;
		else
			return false;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.tankDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}