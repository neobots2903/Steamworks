/**
 * 
 */
package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.subsystems.Drive2903;
import org.usfirst.frc.team2903.robot.subsystems.Gyro2903;
import org.usfirst.frc.team2903.robot.subsystems.MiniPID2903;
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
	private Drive2903 drive = new Drive2903();
	private MiniPID2903 pid;
	private Gyro2903 gyro = new Gyro2903();
	

	public TurnWithGyro(double targetangle) {
		setTargetAngle(targetangle);
		pid = new MiniPID2903(4.0, 0.2, 1.0);
		pid.setSetpoint(TargetAngle);
		pid.setOutputLimits(-100, 100);
	
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
		gyro.reset();
		gyro.Calibrate();
		
	}

	@Override
	protected void execute() {
		double gyroAngle = gyro.GyroPosition() % 360;

		double speed = pid.getOutput(gyroAngle);
		
		drive.arcadeDrive(0, speed);
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
		drive.arcadeDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		drive.arcadeDrive(0, 0);
	}

}