/*
// ANDREW-TODO
 * 
 * Remove all lines marked with // ANDREW some might have ANDREW-TODO (as above)
 * We can remove all left side encoder stuff.  Other than that, the COUNTS_PER_MOTOR_REVOLUTION 
 * looks correct. 
 * 
 * We can remove all of the Centimeter and position stuff
 * 
 * we probably need to set the encoder count as well in drive reset.  its interesting that we put it there instead of in the initializer as we did on Bertha
 * 
 * I also noticed that bernard is still creating the camera instance in the drivestraight.  Maybe we should still be doing that since we only need
 * the camera for driving and they can refresh the browser when teleop starts.  Maybe even have bernard set the camera to full brightness before exiting
 * 
*/
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

	static final double		PI						= 3.14159; // ANDREW
	
	// ANDREW-TODO -- this should be set to 256 and the line in DriveReset for the right encoder should be uncommented.
    static final int     COUNTS_PER_MOTOR_REV    = 360 ;    // eg: Grayhill 61R256 
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP // ANDREW
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference // ANDREW
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / // ANDREW
            												(WHEEL_DIAMETER_INCHES * PI); // ANDREW
    
    static final double 	CM_PER_INCH             = 2.54; // ANDREW
    static final double 	COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / // ANDREW
    													((WHEEL_DIAMETER_INCHES * CM_PER_INCH) * PI); // ANDREW
    
    static final double 	TARGET_SPEED			= 144;		// revolutions per minute // ANDREW
    static final double 	MIN_PER_SEC				= 0.0167;  // minute to second ratio // ANDREW
    static final double		SEC_PER_TVE				= 0.1;	   // Seconds per 10 velocity measurement periods (100ms) // ANDREW
    static final double 	NATIVE_UNITS_PER_TVE	= TARGET_SPEED * MIN_PER_SEC * SEC_PER_TVE * COUNTS_PER_MOTOR_REV; // ANDREW
    static final double		FULL_FORWARD			= 1023; // ANDREW
    
    // TARGET_SPEED needs to be determined from the maximum speed from the self-test display // ANDREW
    // while running in teleop.  We probably only want to use 80% of that speed to make sure  // ANDREW
    // that we can achieve the value // ANDREW
    
    // F-GAIN = FULL FORWARD / NATIVE_UNITS_PER_TVE // ANDREW
    
    // FULL FORWARD comes from the SELF-TEST for Motor Controller 1 or 3 // ANDREW

	public RobotDrive robotDrive;
	private int lastRightRawCount;
	private int lastLeftRawCount; // ANDREW
	private boolean autoPositionBothSides; // ANDREW
	
	public Drive2903() {

		// instantiate the talons
		leftFrontMotor = new CANTalon(RobotMap.LeftTopMotor);
		leftRearMotor = new CANTalon(RobotMap.LeftBottomMotor);
		rightFrontMotor = new CANTalon(RobotMap.RightTopMotor);
		rightRearMotor = new CANTalon(RobotMap.RightBottomMotor);
		
		
		rightFrontMotor.setInverted(false);
		rightRearMotor.setInverted(false);
		leftFrontMotor.setInverted(false);
		leftRearMotor.setInverted(false);
		
		robotDrive = new RobotDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor);
		driveReset();
	}

	  /** // ANDREW
	   * Converts inches to number of encoder counts. // ANDREW
	   * // ANDREW
	   * @param inches	The value in inches to convert // ANDREW
	   */ // ANDREW
	public double convertInchesToEncoderCount(int inches) // ANDREW
	{ // ANDREW
		return inches * COUNTS_PER_INCH; // ANDREW
	} // ANDREW
	
	  /** // ANDREW
	   * Converts centimeters to number of encoder counts. // ANDREW
	   * // ANDREW
	   * @param centimeters	The value in centimeters to convert // ANDREW
	   */ // ANDREW
	public double convertCentimetersToEncoder(int centimeters) // ANDREW
	{ // ANDREW
		return centimeters * COUNTS_PER_CM; // ANDREW
	} // ANDREW
	
	
	public double getDistanceTraveled() {
		robotDrive.arcadeDrive(0, 0);
		if (robotDrive.isAlive() == true) {
			return (leftFrontMotor.getPosition() + rightFrontMotor.getPosition()) / 2;
		}
		else {
			return 0;
		}
	}

	public void drive(double forward, double turn) {
		robotDrive.drive(forward, turn);
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
//		SmartDashboard.putNumber("Left Encoder", leftGetRawCount()); // ANDREW
//		SmartDashboard.putNumber("Right Encoder", rightGetRawCount()); // ANDREW
		
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
		
		//Reset the encoder to zero as its current position
		rightFrontMotor.setPosition(0);
		rightFrontMotor.setEncPosition(0);
		leftFrontMotor.setPosition(0);
		leftFrontMotor.setEncPosition(0);	}
	
	public void setAutoPositionMode() {
		setAutoPositionMode(false);
		autoPositionBothSides = false;
	}
	
	/**
	 * This will set the primary motor controller on the right side to position mode.  Optionally, it can
	 * put the left side into position mode as well.  By default, it will leave the left side in follower mode
	 * 
	 * @param bothSides		if true, sets the left side into position mode
	 * 						if false, sets the left side into follower mode
	 */
	public void setAutoPositionMode(boolean bothSides)
	{
		
		autoPositionBothSides = bothSides; // ANDREW
		
		// set the right side primary to position and the secondary to follower // ANDREW
		Robot.driveSubsystem.rightFrontMotor.changeControlMode(TalonControlMode.Position); // ANDREW
		Robot.driveSubsystem.rightRearMotor.changeControlMode(TalonControlMode.Follower); // ANDREW
		
		// talon position set up  // ANDREW
		int absolutePosition = rightFrontMotor.getPulseWidthPosition() & 0xFFF; // ANDREW
		rightFrontMotor.setEncPosition(absolutePosition);		 // ANDREW
		
		
		// have the motors follow rightFrontMotor // ANDREW
		rightFrontMotor.set(0); // ANDREW
		rightRearMotor.set(rightFrontMotor.getDeviceID()); // ANDREW

		// Enable PID control on the talon // ANDREW
		rightFrontMotor.enableControl(); 		 // ANDREW
		
		//Reset the encoder to zero as its current position // ANDREW
		rightFrontMotor.setPosition(0); // ANDREW
		rightFrontMotor.setEncPosition(0); // ANDREW

		/* set closed loop gains in slot0 */ // ANDREW
		rightFrontMotor.setAllowableClosedLoopErr(0); // ANDREW
		rightFrontMotor.setProfile(0); // ANDREW
//		rightFrontMotor.setF(FULL_FORWARD / NATIVE_UNITS_PER_TVE);  // this needs to be FULL-FOWARD / NATIVE UNITS) // ANDREW
		rightFrontMotor.setF(0);  // this needs to be FULL-FOWARD / NATIVE UNITS) // ANDREW
		rightFrontMotor.setP(0.1); // ANDREW
		rightFrontMotor.setI(0); // ANDREW
		rightFrontMotor.setD(0); // ANDREW
//		rightFrontMotor.setMotionMagicCruiseVelocity(0);  // ANDREW
//		rightFrontMotor.setMotionMagicAcceleration(0); // ANDREW

		// both sides are going to be monitoring position // ANDREW
		if (bothSides) { // ANDREW
			// put left primary into position mode and the secondary to follower // ANDREW
			Robot.driveSubsystem.leftFrontMotor.changeControlMode(TalonControlMode.Position); // ANDREW
			Robot.driveSubsystem.leftRearMotor.changeControlMode(TalonControlMode.Follower); // ANDREW	
			
			// talon position set up  // ANDREW
			absolutePosition = leftFrontMotor.getPulseWidthPosition() & 0xFFF; // ANDREW
			leftFrontMotor.setEncPosition(absolutePosition); // ANDREW
		
			// left side follows right front motor // ANDREW
			leftFrontMotor.set(0); // ANDREW
			leftRearMotor.set(leftFrontMotor.getDeviceID()); // ANDREW
			
			// Enable PID control on the talon // ANDREW
			leftFrontMotor.enableControl(); 		 // ANDREW
			
			//Reset the encoder to zero as its current position // ANDREW
			leftFrontMotor.setPosition(0); // ANDREW
			leftFrontMotor.setEncPosition(0); // ANDREW

			/* set closed loop gains in slot0 */ // ANDREW
			leftFrontMotor.setProfile(0); // ANDREW
			leftFrontMotor.setF(FULL_FORWARD / NATIVE_UNITS_PER_TVE);  // this needs to be FULL-FOWARD / NATIVE UNITS) // ANDREW
			leftFrontMotor.setP(0); // ANDREW
			leftFrontMotor.setI(0); // ANDREW
			leftFrontMotor.setD(0); // ANDREW
			leftFrontMotor.setMotionMagicCruiseVelocity(0);  // ANDREW
			leftFrontMotor.setMotionMagicAcceleration(0); // ANDREW
		} 
		
		// only the right side is monitoring position, left side follows right side
		else { // ANDREW
			// put left side into follower mode // ANDREW
			Robot.driveSubsystem.leftFrontMotor.changeControlMode(TalonControlMode.Follower); // ANDREW
			Robot.driveSubsystem.leftRearMotor.changeControlMode(TalonControlMode.Follower); // ANDREW	
			
			// left side follows right front motor // ANDREW
			leftFrontMotor.set(rightFrontMotor.getDeviceID()); // ANDREW
			leftRearMotor.set(rightFrontMotor.getDeviceID()); // ANDREW
		} // ANDREW
	}
	
	public void setTeleopMode() {
		Robot.driveSubsystem.rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		Robot.driveSubsystem.leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		Robot.driveSubsystem.rightRearMotor.changeControlMode(TalonControlMode.PercentVbus);
		Robot.driveSubsystem.leftRearMotor.changeControlMode(TalonControlMode.PercentVbus);
		
//		rightFrontMotor.setInverted(false); // ANDREW
//		rightRearMotor.setInverted(false); // ANDREW
//		leftFrontMotor.setInverted(false); // ANDREW
//		leftRearMotor.setInverted(false); // ANDREW
		
		leftRearMotor.set(0);
		rightRearMotor.set(0);
		leftFrontMotor.set(0);
		rightFrontMotor.set(0);
	}
	
	
	public void setPosition(long distanceToDrive) {
		// TODO Auto-generated method stub // ANDREW
		rightFrontMotor.changeControlMode(TalonControlMode.Position); // ANDREW
		rightFrontMotor.set(distanceToDrive); // ANDREW
		if (autoPositionBothSides) { // ANDREW
			leftFrontMotor.changeControlMode(TalonControlMode.Position); // ANDREW
			leftFrontMotor.set(distanceToDrive); // ANDREW
		} // ANDREW
	}

	public void setVelocity(double velocity) {
		rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus); // ANDREW
		rightFrontMotor.set(velocity); // ANDREW
		leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus); // ANDREW
		leftFrontMotor.set(velocity); // ANDREW
	}

	  //Low to High gear
	  public void changeToHighGear (){
		  Robot.pnuematicsSubsystem.highGear();
	  }
	  
	  public void changeToLowGear (){
		  Robot.pnuematicsSubsystem.lowGear();
	  }
	  
	  public void driveReset() {
			// disable timeout safety on drives
			rightFrontMotor.setSafetyEnabled(false);
			leftFrontMotor.setSafetyEnabled(false);
			
			// configure the encoders
			rightFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			rightFrontMotor.reverseSensor(true);		
			
			 // ANDREW-TODO this should probably be uncommented and set to 256 for the grayhill encoder.  
//			rightFrontMotor.configEncoderCodesPerRev(COUNTS_PER_MOTOR_REV);
			leftFrontMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);  // ANDREW
			leftFrontMotor.reverseSensor(false);  		 // ANDREW
//			leftFrontMotor.configEncoderCodesPerRev(COUNTS_PER_MOTOR_REV); // ANDREW
			//SmartDashboard.putNumber("Feedback Status", CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent.value); // ANDREW
			
			// configure the output
			rightFrontMotor.configNominalOutputVoltage(+0f, -0f); // ANDREW
			rightFrontMotor.configPeakOutputVoltage(+12f, -12f); // ANDREW
			
			leftFrontMotor.configNominalOutputVoltage(+0f, -0f); // ANDREW
			leftFrontMotor.configPeakOutputVoltage(+12f, -12f); // ANDREW
			
			// Initialize the raw counts
			lastRightRawCount = 0; // ANDREW
			lastLeftRawCount = 0; // ANDREW
		  
		  rightFrontMotor.setPosition(0);
		  leftFrontMotor.setPosition(0); // ANDREW
		  rightFrontMotor.setEncPosition(0);
		  leftFrontMotor.setEncPosition(0); // ANDREW
	  }
	  
		public int rightGetCount() {
			return (int)rightFrontMotor.getPosition();
		}

		public int rightGetRawCount() {
			return (int)-rightFrontMotor.getEncPosition(); // - lastRightRawCount;
		}


		public int leftGetCount() {
				return (int)leftFrontMotor.getPosition();
		}

		public int leftGetRawCount() {
				return (int)leftFrontMotor.getEncPosition(); // -lastLeftRawCount;
		}

		public void setLastRightRaw(int lastRawCount) {
			lastRightRawCount = lastRawCount;
		}

		public void setLastLeftRaw(int lastRawCount) {
			lastLeftRawCount = lastRawCount;
		}
		
		public boolean isRightEncoderPresent() {
			return rightFrontMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative) != null;
		}

		public boolean isLeftEncoderPresent() {
			return leftFrontMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative) != null;
		}

}