/**
 * 
 */
package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.subsystems.Drive2903;
import org.usfirst.frc.team2903.robot.subsystems.Gyro2903;
import org.usfirst.frc.team2903.robot.subsystems.MiniPID2903;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private Drive2903 drive;
	private MiniPID2903 pid;
	private Gyro2903 gyro;
	

	public TurnWithGyro(double targetangle) {
		super ("TurnWithGyro");
		setTargetAngle(targetangle);
		drive = Robot.driveSubsystem;
		gyro = Robot.gyroSubsystem;
		pid = Robot.minipidSubsystem;
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
		SmartDashboard.putNumber("auto output=", speed);
		//drive.arcadeDrive(0, speed);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		double gyroAngle = gyro.GyroPosition() % 360;
		
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