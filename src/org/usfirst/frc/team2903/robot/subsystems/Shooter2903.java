package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter2903 extends Subsystem {

	// LimitSwitches
//	public DigitalInput bottomLimit = new DigitalInput(RobotMap.botLimitSwitch);
//	public DigitalInput upperLimit = new DigitalInput(RobotMap.topLimitSwitch);

	// LimitSwitches' Booleans
//	boolean bottomLimitAct = bottomLimit.get();
//	boolean topLimitAct = upperLimit.get();

	static double leftHighForward;
	static double leftLowForward;
	static double leftReverse;
	static double rightHighForward;
	static double rightLowForward;
	static double rightReverse;
	static int leftCount;
	static int rightCount;

	// raw encoder counts
	static int leftRawCount;
	static int rightRawCount;
	
	
	// shooter and kicker talons
	static CANTalon rightShooter;
	static CANTalon leftShooter;
	static CANTalon KickingMotor;

	// encoder for shooter motors
	static Encoder shooterLeftSpeedEncoder;
	static Encoder shooterRightSpeedEncoder;



	public Shooter2903() {
		
		super("Shooter2903");
		
		// instantiate the talon motor controllers
		rightShooter = new CANTalon(RobotMap.RightShooter);
		leftShooter = new CANTalon(RobotMap.LeftShooter);
		KickingMotor = new CANTalon(RobotMap.kickerMotor);
		
		
		// enable the motors
		leftShooter.enable();
		rightShooter.enable();
		KickingMotor.enable();

//		KickingMotor.enableLimitSwitch(true, true);
//		KickingMotor.ConfigFwdLimitSwitchNormallyOpen(false);
//		KickingMotor.ConfigRevLimitSwitchNormallyOpen(false);

		// instantiate the encoder
//		shooterLeftSpeedEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
//		shooterRightSpeedEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

		// initialize class data
		// TODO: Make these numbers constants -- see the values in RobotMap.java
		leftHighForward = 1.0;
		leftLowForward = 0.35;
		leftReverse = -0.25;
		rightHighForward = 1.0;
		rightLowForward = 0.35;
		rightReverse = -0.25;
		
		// get the encoder counts
//		leftCount = shooterLeftSpeedEncoder.get();
//		leftRawCount = shooterLeftSpeedEncoder.getRaw();
//		rightCount = shooterRightSpeedEncoder.get();
//		rightRawCount = shooterRightSpeedEncoder.getRaw();

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void selectGoalMode(boolean highGoal)
	{
//		shooterLeftSpeedEncoder.reset();
//		shooterRightSpeedEncoder.reset();
		
	}
	public void enableHighGoalMode() {
//		shooterLeftSpeedEncoder.reset();
//		shooterRightSpeedEncoder.reset();
		// TODO: add kicker
		//leftShooter.set(leftHighForward * -1.0);
		//rightShooter.set(rightHighForward);

		// get and display the encoder counts
//		SmartDashboard.putNumber("leftCount", getLeftCount());
//		SmartDashboard.putNumber("leftRawCount", getLeftRawCount());
//		SmartDashboard.putNumber("rightCount", getRightCount());
//		SmartDashboard.putNumber("rightRawCount", getRightRawCount());
		// if statements to balance the motors
//		if (getLeftCount() != getRightCount()) {
//			if (getLeftCount() < getRightCount()) {
//				rightHighForward = rightHighForward - 0.1;
//			}
//			if (getLeftCount() > getRightCount()) {
//				leftHighForward = leftHighForward - 0.1;
//			}
//		}
//		if (rightHighForward < 0.8) {
//			rightHighForward = 1.0;
//		}
//		if (leftHighForward < 0.8) {
//			leftHighForward = 1.0;
//		}
	}

	public void enableLowGoalMode() {
//		shooterLeftSpeedEncoder.reset();
//		shooterRightSpeedEncoder.reset();
		
//		// TODO: add kicker
//		leftShooter.set(leftLowForward * -1.0);
//		rightShooter.set(rightLowForward);
//
//		// get and display the encoder counts
//		SmartDashboard.putNumber("leftCount", getLeftCount());
//		SmartDashboard.putNumber("leftRawCount", getLeftRawCount());
//		SmartDashboard.putNumber("rightCount", getRightCount());
//		SmartDashboard.putNumber("rightRawCount", getRightRawCount());
//		if (getLeftCount() != getRightCount()) {
//			double difference = getLeftCount() - getRightCount();
//			if (difference < 0)
//			{
//				rightLowForward -= difference;
//				leftLowForward += difference;
//			}
//			else
//			{
//				rightLowForward += difference;
//				leftLowForward -= difference;
//			}
////			if (getLeftCount() < getRightCount()) {
////				rightLowForward = rightLowForward + 0.1;
////			}
////			if (getLeftCount() > getRightCount()) {
////				leftLowForward = leftLowForward + 0.1;
////			}
//		}
////		if (rightLowForward > 0.) {
////			rightLowForward = .0;
////		}
////		if (leftLowForward > 0.) {
////			leftLowForward = .0;
////		}
	}

	public void enablePickupMode() {
//		shooterLeftSpeedEncoder.reset();
//		shooterRightSpeedEncoder.reset();
//
//		leftShooter.set(leftReverse * -1.0);
//		rightShooter.set(rightReverse);
//
//		// get and display the encoder counts
//		SmartDashboard.putNumber("leftCount", getLeftCount());
//		SmartDashboard.putNumber("leftRawCount", getLeftRawCount());
//		SmartDashboard.putNumber("rightCount", getRightCount());
//		SmartDashboard.putNumber("rightRawCount", getRightRawCount());
//		if (getLeftCount() != getRightCount()) {
//			if (getLeftCount() < getRightCount()) {
//				rightHighForward = rightHighForward + 0.1;
//			}
//			if (getLeftCount() > getRightCount()) {
//				leftHighForward = leftHighForward + 0.1;
//			}
//		}
//		if (rightHighForward > 0.5) {
//			rightHighForward = 0.25;
//		}
//		if (leftHighForward > 0.5) {
//			leftHighForward = 0.25;
//		}
	}

	public void disableShooter() {
//		shooterLeftSpeedEncoder.reset();
//		shooterRightSpeedEncoder.reset();
//
//		leftShooter.set(0);
//		rightShooter.set(0);
//		if (getLeftCount() != getRightCount()) {
//			if (getLeftCount() != 0) {
//				SmartDashboard.getString("Error with LEFT SHOOTER");
//			}
//			if (getRightCount() != 0) {
//				SmartDashboard.getString("Error with RIGHT SHOOTER");
//			}
//		}
	}

	// stops the kicker motor
	public void stopKicker() {
		KickingMotor.set(0);
	}

	// checks to see if either limit switch is true, then moves
	public void Kick(double KickerSpeed) {
		KickingMotor.set(KickerSpeed);
//		if (topLimitAct == true) {
//			KickingMotor.set(-KickerSpeed);
//			} 
//		else if (bottomLimitAct == true) {
//			KickingMotor.set(0);
//			}
	}

	
	
//	public static int getLeftCount() {
//		if (shooterLeftSpeedEncoder != null)
//			return leftCount = shooterLeftSpeedEncoder.get();
//		else
//			return 0;
//
//	}
//
//	public static int getLeftRawCount() {
//		if (shooterLeftSpeedEncoder != null)
//			return leftRawCount = shooterLeftSpeedEncoder.getRaw();
//		else
//			return 0;
//	}
//
//	public static int getRightCount() {
//		if (shooterRightSpeedEncoder != null)
//			return rightCount = shooterRightSpeedEncoder.get();
//		else
//			return 0;
//
//	}
//
//	public static int getRightRawCount() {
//		if (shooterRightSpeedEncoder != null)
//			return rightRawCount = shooterRightSpeedEncoder.getRaw();
//		else
//			return 0;
//	}

}
