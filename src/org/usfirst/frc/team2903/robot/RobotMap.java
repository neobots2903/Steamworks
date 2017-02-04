package org.usfirst.frc.team2903.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// drive motors
	public static int RightTopMotor = 30;
	public static int LeftTopMotor = 31;
	public static int LeftBottomMotor = 33;
	public static int RightBottomMotor = 32;


	//public static int climbingMotor = 35

	// shooter motors
	public static int RightShooter = 36;
	public static int LeftShooter = 37;
	


	// Analog Gyro is on analog 0
	public static int AnalogGyro = 0;

	// Winch
	public static int WinchMotor = 3;
}
