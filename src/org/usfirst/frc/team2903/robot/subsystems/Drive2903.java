package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
//import com.mindsensors.CANSD540;
//import com.mindsensors.CANSD540.StopMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive2903 extends Subsystem {

	
	CANTalon leftFrontMotor;
	CANTalon leftRearMotor;
	CANTalon rightFrontMotor;
	CANTalon rightRearMotor;

	static final double		PI						= 3.14159;
    static final double     COUNTS_PER_MOTOR_REV    = 256 ;    // eg: Grayhill 61R256
    static final double     DRIVE_GEAR_REDUCTION    = 3.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            												(WHEEL_DIAMETER_INCHES * PI);
    
    static final double 	CM_PER_INCH             = 2.54;
    static final double 	COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
    													((WHEEL_DIAMETER_INCHES * CM_PER_INCH) * PI);
	


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
		
		
		rightFrontMotor.setInverted(false);
		rightRearMotor.setInverted(false);
		leftFrontMotor.setInverted(true);
		leftRearMotor.setInverted(true);
		
		robotDrive = new RobotDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor);
		
		// set the drive type
		driveType = DriveType.ArcadeMode1Joystick;

		
		// talon position set up 
		int absolutePosition = leftFrontMotor.getPulseWidthPosition() & 0xFFF;
		leftFrontMotor.setEncPosition(absolutePosition);
		absolutePosition = rightFrontMotor.getPulseWidthPosition() & 0xFFF;
		rightFrontMotor.setEncPosition(absolutePosition);		
		
		rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftFrontMotor.changeControlMode(TalonControlMode.Follower);
		rightRearMotor.changeControlMode(TalonControlMode.Follower);
		leftRearMotor.changeControlMode(TalonControlMode.Follower);
		
		// have the other motors follow the rightFrontMotor
		leftFrontMotor.set(rightFrontMotor.getDeviceID());
		leftRearMotor.set(rightFrontMotor.getDeviceID());
		rightRearMotor.set(rightFrontMotor.getDeviceID());
		
		// disable timeout safety on drives
		rightFrontMotor.setSafetyEnabled(false);
		rightFrontMotor.set(0);
		
		// configure the encoders
		rightFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightFrontMotor.reverseSensor(true);
		rightFrontMotor.configEncoderCodesPerRev(256);
		leftFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		leftFrontMotor.reverseSensor(false);
		leftFrontMotor.configEncoderCodesPerRev(256);
		
		// configure the output
		rightFrontMotor.configNominalOutputVoltage(+0f, -0f);
		rightFrontMotor.configPeakOutputVoltage(+12f, -12f);
		
		leftFrontMotor.configNominalOutputVoltage(+0f, -0f);
		leftFrontMotor.configPeakOutputVoltage(+12f, -12f);
		
		// I found that you do variable = SmartDashboard.getNumber("Enter a value: ");  
		// will allow you to set values into the running program.  Which means we could 
		// dynamically adjust the below values for PID  or for our miniPID when gyro turning.
		
		rightFrontMotor.setProfile(0);
		rightFrontMotor.setF(0.0);
		rightFrontMotor.setP(0.1);
		rightFrontMotor.setI(0.0);
		rightFrontMotor.setD(0.0);
		
		leftFrontMotor.setProfile(0);
		leftFrontMotor.setF(0.0);
		leftFrontMotor.setP(0.1);
		leftFrontMotor.setI(0.0);
		leftFrontMotor.setD(0.0);
	}

	  /**
	   * Converts inches to number of encoder counts.
	   *
	   * @param inches	The value in inches to convert
	   */
	public double convertInchesToEncoderCount(int inches)
	{
		return inches * COUNTS_PER_INCH;
	}
	
	  /**
	   * Converts centimeters to number of encoder counts.
	   *
	   * @param centimeters	The value in centimeters to convert
	   */
	public double convertCentimetersToEncoder(int centimeters)
	{
		return centimeters * COUNTS_PER_CM;
	}
	
	
	public double getDistanceTraveled() {
		robotDrive.arcadeDrive(0, 0);
		if (robotDrive.isAlive() == true) {
			return (leftFrontMotor.getPosition() + rightFrontMotor.getPosition()) / 2;
		}
		else {
			return 0;
		}
	}

//	  /**
//	   * Arcade drive implements single stick driving. This function lets you directly provide
//	   * joystick values from any source.
//	   *
//	   * @param forward     The value to use for forwards/backwards
//	   * @param turn        The value to use for the rotate right/left
//	   */
	public void arcadeDrive(double forward, double turn) {
		robotDrive.arcadeDrive(forward, turn);
	}

	  /**
	   * Provide tank steering using the stored robot configuration. This function lets you directly
	   * provide joystick values from any source.
	   *
	   * @param leftSpeed     The value of the left stick.
	   * @param rightSpeed    The value of the right stick.
	   */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		robotDrive.tankDrive(leftSpeed, rightSpeed);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void setPosition(long distanceToDrive) {
		// TODO Auto-generated method stub
		rightFrontMotor.changeControlMode(TalonControlMode.Position);
		rightFrontMotor.set(distanceToDrive);
		leftFrontMotor.set(distanceToDrive);

	}

	public void setVelocity(double velocity) {
		rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		rightFrontMotor.set(velocity);
		leftFrontMotor.set(velocity);
	}

	  //Low to High gear
	  
	  public void changeToHighGear (){
		  Robot.pnuematicsSubsystem.highGear();
	  }
	  
	  public void changeToLowGear (){
		  Robot.pnuematicsSubsystem.lowGear();
	  }
	  
		public double rightGetCount() {
			return rightFrontMotor.getPosition();
		}

		public double rightGetRawCount() {
			return rightFrontMotor.getEncPosition();
		}


		public double leftGetCount() {
				return leftFrontMotor.getPosition();
		}

		public double leftGetRawcount() {
				return leftFrontMotor.getEncPosition();
		}

}