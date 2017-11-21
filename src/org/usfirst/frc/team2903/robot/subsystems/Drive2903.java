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

	
	public CANTalon leftFrontMotor;
	public CANTalon leftRearMotor;
	public CANTalon rightFrontMotor;
	public CANTalon rightRearMotor;
	
	// ANDREW-TODO -- this should be set to 256 and the line in DriveReset for the right encoder should be uncommented.
    static final int     COUNTS_PER_MOTOR_REV    = 256 ;    // eg: Grayhill 61R256 
    
    // TARGET_SPEED needs to be determined from the maximum speed from the self-test display // ANDREW
    // while running in teleop.  We probably only want to use 80% of that speed to make sure  // ANDREW
    // that we can achieve the value // ANDREW
    
    // F-GAIN = FULL FORWARD / NATIVE_UNITS_PER_TVE // ANDREW
    
    // FULL FORWARD comes from the SELF-TEST for Motor Controller 1 or 3 // ANDREW

	public RobotDrive robotDrive;
	
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
	
	/**
	 * This will set the primary motor controller on the right side to position mode.  Optionally, it can
	 * put the left side into position mode as well.  By default, it will leave the left side in follower mode
	 * 
	 * @param bothSides		if true, sets the left side into position mode
	 * 						if false, sets the left side into follower mode
	 */
	
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
			rightFrontMotor.reverseSensor(false);		
			
			 // ANDREW-TODO this should probably be uncommented and set to 256 for the grayhill encoder.  
		    rightFrontMotor.configEncoderCodesPerRev(COUNTS_PER_MOTOR_REV);
			
			// Initialize the raw counts
			
		  
		  rightFrontMotor.setPosition(0);
		  rightFrontMotor.setEncPosition(0);
	  }
	  
		public int rightGetCount() {
			return (int)rightFrontMotor.getPosition();
		}

//		public int leftGetCount() {
//			return (int)leftFrontMotor.getPosition();
//		}
		
		public int rightGetRawCount() {
			return (int)rightFrontMotor.getEncPosition(); // - lastRightRawCount;
		}
		
		public boolean isRightEncoderPresent() {
			return rightFrontMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative) != null;
		}

}