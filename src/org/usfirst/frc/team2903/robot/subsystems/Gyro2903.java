package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;
import org.usfirst.frc.team2903.robot.drivers.ADIS16448_IMU;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro2903 extends Subsystem {
	

	public enum GYRO_TYPE {
		ANALOG,
		SPI,
		IMU
	};
	
	ADIS16448_IMU imu;
	ADXRS450_Gyro spi;
	AnalogGyro gyro;
	
	double gyroAngle;
	GYRO_TYPE gyroToUse;
	
	// initialize the gyro subsystem
	// if you want to use the ADI IMU pass useIMU as true
	// if you want to use the ADI SPI gyro, pass useIMU as false
	public Gyro2903(GYRO_TYPE gyroType) {
		gyroToUse = gyroType;
		
		switch (gyroType)
		{
		case ANALOG:
			gyro = new AnalogGyro(RobotMap.AnalogGyro);
			break;

		case SPI:
			spi = new ADXRS450_Gyro();
			break;

		case IMU:
			imu = new ADIS16448_IMU();			
			break;
		}
	}

	// return the gyro position
	//	limits the angle to 360.
	public double GyroPosition() {
		switch (gyroToUse)
		{
		case ANALOG:
			gyroAngle = gyro.getAngle();
			break;

		case SPI:
			gyroAngle = spi.getAngle();
			break;

		case IMU:
			gyroAngle = imu.getAngleZ();		
			break;
		}

		gyroAngle = gyroAngle % 360;
		
		SmartDashboard.putNumber("gyroAngle", gyroAngle);
		return gyroAngle;
		
	}
	
	// Calibrate the gyro
	public void Calibrate()
	{
		switch (gyroToUse)
		{
		case ANALOG:
			gyro.calibrate();
			break;

		case SPI:
			spi.calibrate();
			break;

		case IMU:
			imu.calibrate();
			break;
		}
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	// reset the gyro
	public void reset() {
		switch (gyroToUse)
		{
		case ANALOG:
			gyro.reset();
			break;

		case SPI:
			spi.reset();
			break;

		case IMU:
			imu.reset();
			break;
		}
	}
}