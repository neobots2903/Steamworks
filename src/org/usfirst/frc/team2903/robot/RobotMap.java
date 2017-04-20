package org.usfirst.frc.team2903.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// drive motors
	public static int RightTopMotor = 1;
	public static int RightBottomMotor = 2;
	public static int LeftTopMotor = 3;
	public static int LeftBottomMotor = 4;


	

	
	// shooter mechanisim
	public static int shootMotor = 5;
	public static int shakeMotor = 8;
	
	
	// Pick Up
	public static int PickUpMotor = 6;
	
	
	// Analog Gyro is on analog 0
	public static int AnalogGyro = 0;

	
	// Winch
	public static int WinchMotor = 7;
	
	// Pnuematics
	public static int highGearShiftSol = 6;
	public static int lowGearShiftSol = 7;
	public static int gearArmsOpen = 1;
	public static int gearArmsClose = 0;
	
	public static int leftLimitSwitch = 0;
	//public static int rightLimitSwitch = 1;
}
