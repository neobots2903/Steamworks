package org.usfirst.frc.team2903.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

import org.usfirst.frc.team2903.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class Drive2903 extends Subsystem {

	int RightCount;

	public int rightGetCount() {
//		if (driveSpeedEncoder != null)
//			return RightCount = driveSpeedEncoder.get();
//		else
			return (int) rightFrontMotor.getPosition();
	}

	int RightRawCount;

	public int rightGetRawCount() {
//		if (driveSpeedEncoder != null)
//			return RightRawCount = driveSpeedEncoder.getRaw();
//		else
			return (int) rightFrontMotor.getEncPosition();
	}

	int LeftCount;

	public int leftGetCount() {
//		if (driveSpeedEncoder != null)
//			return LeftCount = driveSpeedEncoder.get();
//		else
			return (int) leftFrontMotor.getPosition();
	}

	int LeftRawCount;

	public int leftGetRawcount() {
//		if (driveSpeedEncoder != null)
//			return LeftRawCount = driveSpeedEncoder.getRaw();
//		else
			return (int) leftFrontMotor.getPosition();
	}

	CANTalon leftFrontMotor;
	CANTalon leftRearMotor;
	CANTalon rightFrontMotor;
	CANTalon rightRearMotor;

	// here is the encoder
	Encoder leftDriveSpeedEncoder;
	Encoder driveSpeedEncoder;

	public enum DriveType {
		ArcadeMode1Joystick,
		// ArcadeMode2Joystick,
		ArcadeModeJoyOp,
		// ArcadeModeJoyOp2,
		// TankDriveJoysticks,
		// TankDriveJoyOp
	}

	// drive mode selection
	public DriveType driveType;

	public RobotDrive robotDrive;

	public Drive2903() {

		// instantiate the talons
		leftFrontMotor = new CANTalon(RobotMap.LeftTopMotor);
		leftRearMotor = new CANTalon(RobotMap.LeftBottomMotor);
		rightFrontMotor = new CANTalon(RobotMap.RightTopMotor);
		rightRearMotor = new CANTalon(RobotMap.RightBottomMotor);

		// leftFrontMotor.changeControlMode(TalonControlMode.Follower);
		// rightRearMotor.changeControlMode(TalonControlMode.Follower);
		// leftRearMotor.changeControlMode(TalonControlMode.Follower);
		//
		// leftFrontMotor.set(RobotMap.RightTopMotor);
		// rightRearMotor.set(RobotMap.RightTopMotor);
		// leftRearMotor.set(RobotMap.RightTopMotor);

		// enable the motors
		leftFrontMotor.enable();
		leftRearMotor.enable();
		rightFrontMotor.enable();
		rightRearMotor.enable();

		// instantiate the drive system
		robotDrive = new RobotDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor);
		robotDrive.setInvertedMotor(MotorType.kRearLeft, true);
		robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);

		// set the drive type
		driveType = DriveType.ArcadeMode1Joystick;

		// enable the encoder
		driveSpeedEncoder = null;// new Encoder(0, 1, false, Encoder.EncodingType.k4X);

//		RightCount = driveSpeedEncoder.get();
//		RightRawCount = driveSpeedEncoder.get();
		
		// talon position set up 
//		int absolutePosition = leftFrontMotor.getPulseWidthPosition() & 0xFFF;
//		rightFrontMotor.setEncPosition(absolutePosition);
//		
		rightFrontMotor.setEncPosition(0);
		rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		rightRearMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftRearMotor.changeControlMode(TalonControlMode.PercentVbus);

		rightFrontMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
//		rightFrontMotor.reverseSensor(true);
//		rightFrontMotor.configEncoderCodesPerRev(1024);
//		
//		rightFrontMotor.configNominalOutputVoltage(+0f, -0f);
//		rightFrontMotor.configPeakOutputVoltage(+12f, -12f);
//		rightFrontMotor.changeControlMode(TalonControlMode.Position);
//		leftFrontMotor.setAllowableClosedLoopErr(0);
//		
//		leftFrontMotor.setProfile(0);
//		leftFrontMotor.setF(0.0);
//		leftFrontMotor.setP(0.1);
//		leftFrontMotor.setI(0.0);
//		leftFrontMotor.setD(0.0);
	}

	public void getDistanceTraveled() {
		robotDrive.arcadeDrive(0, 0);
		if (robotDrive.isAlive() == true) {
			driveSpeedEncoder.get();

		}
	}

	public void arcadeDrive(double forward, double turn) {

		robotDrive.arcadeDrive(forward, turn);

	}

	public void tankDrive(double leftSpeed, double rightSpeed) {

		robotDrive.tankDrive(leftSpeed, rightSpeed);

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void setPosition(long distanceToDrive) {
		// TODO Auto-generated method stub
		leftFrontMotor.changeControlMode(TalonControlMode.Position);
		leftFrontMotor.set(distanceToDrive);

	}

	public void setVelocity(double velocity) {
		leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftFrontMotor.set(velocity);

	}

}