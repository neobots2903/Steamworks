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

	// arm controller motor
	public static int armMotor = 34;
	//public static int climbingMotor = 35
	// kicker motor
	public static int kickerMotor = 38;
	// kicker Limit Switches
//	public static int botLimitSwitch = 4;//port subjected to change
//    public static int topLimitSwitch = 5;//port subjected to change
	
	// shooter motors
	public static int RightShooter = 36;
	public static int LeftShooter = 37;
	

	// PWM

	// Gyro is on analog 0
	public static int Gyro = 0;

	// Winch
	public static int WinchMotor = 3;
}
