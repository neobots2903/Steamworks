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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive2903 extends Subsystem {

	
	CANTalon leftFrontMotor;
	CANTalon leftRearMotor;
	CANTalon rightFrontMotor;
	CANTalon rightRearMotor;

	static final double		PI						= 3.14159;
    static final double     COUNTS_PER_MOTOR_REV    = 1024 ;    // eg: Grayhill 61R256
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            												(WHEEL_DIAMETER_INCHES * PI);
    
    static final double 	CM_PER_INCH             = 2.54;
    static final double 	COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
    													((WHEEL_DIAMETER_INCHES * CM_PER_INCH) * PI);
    
    static final double 	TARGET_SPEED			= 1500;		// revolutions per minute
    static final double 	MIN_PER_SEC				= 0.0167;  // minute to second ratio
    static final double		SEC_PER_TVE				= 0.1;	   // Seconds per 10 velocity measurement periods (100ms)
    static final double 	NATIVE_UNITS_PER_TVE	= TARGET_SPEED * MIN_PER_SEC * SEC_PER_TVE * COUNTS_PER_MOTOR_REV;
    static final double		FULL_FORWARD			= 255;
    
    // TARGET_SPEED needs to be determined from the maximum speed from the self-test display
    // while running in teleop.  We probably only want to use 80% of that speed to make sure 
    // that we can achieve the value
    
    // F-GAIN = FULL FORWARD / NATIVE_UNITS_PER_TVE
    
    // FULL FORWARD comes from the SELF-TEST for Motor Controller 1 or 3

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
		
		// talon position set up 
		int absolutePosition = leftFrontMotor.getPulseWidthPosition() & 0xFFF;
		leftFrontMotor.setEncPosition(absolutePosition);
		absolutePosition = rightFrontMotor.getPulseWidthPosition() & 0xFFF;
		rightFrontMotor.setEncPosition(absolutePosition);		
		
		
		// disable timeout safety on drives
		rightFrontMotor.setSafetyEnabled(false);
		rightFrontMotor.set(0);
		leftFrontMotor.setSafetyEnabled(false);
		leftFrontMotor.set(0);
		
		// configure the encoders
		rightFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightFrontMotor.reverseSensor(true);
		rightFrontMotor.configEncoderCodesPerRev(256);
		leftFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		leftFrontMotor.reverseSensor(false);
		leftFrontMotor.configEncoderCodesPerRev(256);
		SmartDashboard.putNumber("Feedback Status", CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent.value);
		
		// configure the output
		rightFrontMotor.configNominalOutputVoltage(+0f, -0f);
		rightFrontMotor.configPeakOutputVoltage(+12f, -12f);
		
		leftFrontMotor.configNominalOutputVoltage(+0f, -0f);
		leftFrontMotor.configPeakOutputVoltage(+12f, -12f);
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

	  /**
	   * Arcade drive implements single stick driving. This function lets you directly provide
	   * joystick values from any source.
	   *
	   * @param forward     The value to use for forwards/backwards
	   * @param turn        The value to use for the rotate right/left
	   */
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
		SmartDashboard.putNumber("Left Encoder", leftGetRawCount());
		SmartDashboard.putNumber("Right Encoder", rightGetRawCount());
		
		robotDrive.tankDrive(leftSpeed, rightSpeed);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void setAutoMode()
	{
		Robot.driveSubsystem.rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		Robot.driveSubsystem.leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		Robot.driveSubsystem.rightRearMotor.changeControlMode(TalonControlMode.Follower);
		Robot.driveSubsystem.leftRearMotor.changeControlMode(TalonControlMode.Follower);	
		
		// have the motors follow rightFrontMotor
		rightFrontMotor.set(0);
		leftFrontMotor.set(0);
		leftRearMotor.set(leftFrontMotor.getDeviceID());
		rightRearMotor.set(rightFrontMotor.getDeviceID());
	}
	
	public void setAutoPositionMode()
	{
		Robot.driveSubsystem.rightFrontMotor.changeControlMode(TalonControlMode.Position);
		Robot.driveSubsystem.leftFrontMotor.changeControlMode(TalonControlMode.Follower);
		Robot.driveSubsystem.rightRearMotor.changeControlMode(TalonControlMode.Follower);
		Robot.driveSubsystem.leftRearMotor.changeControlMode(TalonControlMode.Follower);	
		
		// have the motors follow rightFrontMotor
		rightFrontMotor.set(0);
		leftRearMotor.set(rightFrontMotor.getDeviceID());
		leftRearMotor.set(rightFrontMotor.getDeviceID());
		rightRearMotor.set(rightFrontMotor.getDeviceID());

		// Enable PID control on the talon
		rightFrontMotor.enableControl(); 		
		
		//Reset the encoder to zero as its current position
		rightFrontMotor.setPosition(0);

		/* set closed loop gains in slot0 */
		rightFrontMotor.setProfile(0);
		rightFrontMotor.setF(FULL_FORWARD / NATIVE_UNITS_PER_TVE);  // this needs to be FULL-FOWARD / NATIVE UNITS)
		rightFrontMotor.setP(0);
		rightFrontMotor.setI(0);
		rightFrontMotor.setD(0);
		rightFrontMotor.setMotionMagicCruiseVelocity(0); 
		rightFrontMotor.setMotionMagicAcceleration(0);
	}
	
	public void setTeleopMode() {
		Robot.driveSubsystem.rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		Robot.driveSubsystem.leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		Robot.driveSubsystem.rightRearMotor.changeControlMode(TalonControlMode.PercentVbus);
		Robot.driveSubsystem.leftRearMotor.changeControlMode(TalonControlMode.PercentVbus);
		
		leftRearMotor.set(0);
		rightRearMotor.set(0);
		leftFrontMotor.set(0);
		rightFrontMotor.set(0);

	}
	public void setPosition(long distanceToDrive) {
		// TODO Auto-generated method stub
		rightFrontMotor.changeControlMode(TalonControlMode.Position);
		rightFrontMotor.set(distanceToDrive);
	}

	public void setVelocity(double velocity) {
		rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		rightFrontMotor.set(velocity);
		leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftFrontMotor.set(velocity);
	}

	  //Low to High gear
	  public void changeToHighGear (){
		  Robot.pnuematicsSubsystem.highGear();
	  }
	  
	  public void changeToLowGear (){
		  Robot.pnuematicsSubsystem.lowGear();
	  }
	  
	  public void driveReset() {
		  rightFrontMotor.setPosition(0);
		  leftFrontMotor.setPosition(0);
		  rightFrontMotor.setEncPosition(0);
		  leftFrontMotor.setEncPosition(0);
	  }
	  
		public int rightGetCount() {
			return (int)rightFrontMotor.getPosition();
		}

		public int rightGetRawCount() {
			return (int)rightFrontMotor.getEncPosition();
		}


		public int leftGetCount() {
				return (int)leftFrontMotor.getPosition();
		}

		public int leftGetRawCount() {
				return (int)leftFrontMotor.getEncPosition();
		}

}