package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro2903 extends Subsystem {
	
	AnalogGyro gyro;
	double gyroAngle;

	public Gyro2903() {
			gyro = new AnalogGyro(RobotMap.Gyro);
			gyro.initGyro();
			gyro.calibrate();
	}
	
	public double GyroPosition() {

		//gyro.reset();
		gyroAngle = gyro.getAngle();
		SmartDashboard.putNumber("gyroAngle", gyroAngle);
		return gyroAngle;
	}
	
	public void Calibrate()
	{
		gyro.calibrate();
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void reset() {
		gyro.reset();
		
	}
}