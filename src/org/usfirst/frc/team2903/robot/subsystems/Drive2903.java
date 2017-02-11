package org.usfirst.frc.team2903.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
//import com.mindsensors.CANSD540;
//import com.mindsensors.CANSD540.StopMode;

public class Drive2903 extends Subsystem {

	
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
		
		
		rightFrontMotor.setInverted(false);
		rightRearMotor.setInverted(false);
		leftFrontMotor.setInverted(true);
		leftRearMotor.setInverted(true);
		
		robotDrive = new RobotDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor);
		
//		leftRearMotor.setVoltageRamp(0);
//		leftFrontMotor.setVoltageRamp(0);
//		rightRearMotor.setVoltageRamp(0);
//		rightFrontMotor.setVoltageRamp(0);
//
//		leftRearMotor.setStopMode(StopMode.Brake);
//		leftFrontMotor.setStopMode(StopMode.Brake);
//		rightRearMotor.setStopMode(StopMode.Brake);
//		rightFrontMotor.setStopMode(StopMode.Brake);
		
//		rightFrontMotor.setInverted(true);
//		rightRearMotor.setInverted(true);
//		leftFrontMotor.setInverted(false);
//		leftRearMotor.setInverted(false);
		
		// set the drive type
		driveType = DriveType.ArcadeMode1Joystick;

		// enable the encoder
		driveSpeedEncoder = null;// new Encoder(0, 1, false, Encoder.EncodingType.k4X);

//		RightCount = driveSpeedEncoder.get();
//		RightRawCount = driveSpeedEncoder.get();
		
		// talon position set up 
		int absolutePosition = leftFrontMotor.getPulseWidthPosition() & 0xFFF;
		rightFrontMotor.setEncPosition(absolutePosition);
		
		rightFrontMotor.setEncPosition(0);
		rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		rightRearMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftRearMotor.changeControlMode(TalonControlMode.PercentVbus);

		rightFrontMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightFrontMotor.reverseSensor(true);
		rightFrontMotor.configEncoderCodesPerRev(1024);
		
		rightFrontMotor.configNominalOutputVoltage(+0f, -0f);
		rightFrontMotor.configPeakOutputVoltage(+12f, -12f);
		rightFrontMotor.changeControlMode(TalonControlMode.Position);
		leftFrontMotor.setAllowableClosedLoopErr(0);
		
		leftFrontMotor.setProfile(0);
		leftFrontMotor.setF(0.0);
		leftFrontMotor.setP(0.1);
		leftFrontMotor.setI(0.0);
		leftFrontMotor.setD(0.0);
	}

	public void getDistanceTraveled() {
		robotDrive.arcadeDrive(0, 0);
		if (robotDrive.isAlive() == true) {
			driveSpeedEncoder.get();

		}
	}
//	  /**
//	   * Arcade drive implements single stick driving. This function lets you directly provide
//	   * joystick values from any source.
//	   *
//	   * @param moveValue     The value to use for forwards/backwards
//	   * @param rotateValue   The value to use for the rotate right/left
//	   * @param squaredInputs If set, decreases the sensitivity at low speeds
//	   */
//	  public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
//	    // local variables to hold the computed PWM values for the motors
////	    if (!kArcadeStandard_Reported) {
////	      HAL.report(tResourceType.kResourceType_RobotDrive, getNumMotors(),
////	          tInstances.kRobotDrive_ArcadeStandard);
////	      kArcadeStandard_Reported = true;
////	    }
//
//	    double leftMotorSpeed;
//	    double rightMotorSpeed;
//
//	    moveValue = limit(moveValue);
//	    rotateValue = limit(rotateValue);
//
//	    if (squaredInputs) {
//	      // square the inputs (while preserving the sign) to increase fine control
//	      // while permitting full power
//	      if (moveValue >= 0.0) {
//	        moveValue = moveValue * moveValue;
//	      } else {
//	        moveValue = -(moveValue * moveValue);
//	      }
//	      if (rotateValue >= 0.0) {
//	        rotateValue = rotateValue * rotateValue;
//	      } else {
//	        rotateValue = -(rotateValue * rotateValue);
//	      }
//	    }
//
//	    if (moveValue > 0.0) {
//	      if (rotateValue > 0.0) {
//	        leftMotorSpeed = moveValue - rotateValue;
//	        rightMotorSpeed = Math.max(moveValue, rotateValue);
//	      } else {
//	        leftMotorSpeed = Math.max(moveValue, -rotateValue);
//	        rightMotorSpeed = moveValue + rotateValue;
//	      }
//	    } else {
//	      if (rotateValue > 0.0) {
//	        leftMotorSpeed = -Math.max(-moveValue, rotateValue);
//	        rightMotorSpeed = moveValue + rotateValue;
//	      } else {
//	        leftMotorSpeed = moveValue - rotateValue;
//	        rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
//	      }
//	    }
//
//	    //setLeftRightMotorOutputs(leftMotorSpeed, rightMotorSpeed);
//	    leftFrontMotor.set(leftMotorSpeed);
//	    rightFrontMotor.set(-rightMotorSpeed);
//	    rightRearMotor.set(-rightMotorSpeed);
//	    leftRearMotor.set(leftMotorSpeed);
//	  }
//
//	  /**
//	   * Arcade drive implements single stick driving. This function lets you directly provide
//	   * joystick values from any source.
//	   *
//	   * @param moveValue   The value to use for fowards/backwards
//	   * @param rotateValue The value to use for the rotate right/left
//	   */
//	  public void arcadeDrive(double moveValue, double rotateValue) {
//	    arcadeDrive(moveValue, rotateValue, true);
//	  }

	public void arcadeDrive(double forward, double turn) {

		robotDrive.arcadeDrive(forward, turn);

	}

	  /**
	   * Provide tank steering using the stored robot configuration. This function lets you directly
	   * provide joystick values from any source.
	   *
	   * @param leftValue     The value of the left stick.
	   * @param rightValue    The value of the right stick.
	   * @param squaredInputs Setting this parameter to true decreases the sensitivity at lower speeds
	   */
//	  public void tankDrive(double leftValue, double rightValue, boolean squaredInputs) {
//
////	    if (!kTank_Reported) {
////	      HAL.report(tResourceType.kResourceType_RobotDrive, getNumMotors(),
////	          tInstances.kRobotDrive_Tank);
////	      kTank_Reported = true;
////	    }
//
//	    // square the inputs (while preserving the sign) to increase fine control
//	    // while permitting full power
//	    leftValue = limit(leftValue);
//	    rightValue = limit(rightValue);
//	    if (squaredInputs) {
//	      if (leftValue >= 0.0) {
//	        leftValue = leftValue * leftValue;
//	      } else {
//	        leftValue = -(leftValue * leftValue);
//	      }
//	      if (rightValue >= 0.0) {
//	        rightValue = rightValue * rightValue;
//	      } else {
//	        rightValue = -(rightValue * rightValue);
//	      }
//	    }
//	   // setLeftRightMotorOutputs(leftValue, rightValue);
//	    leftFrontMotor.set(leftValue);
//	    rightFrontMotor.set(rightValue);
//	    rightRearMotor.set(rightValue);
//	    leftRearMotor.set(leftValue);
//	    
//	  }
//
//	  /**
//	   * Provide tank steering using the stored robot configuration. This function lets you directly
//	   * provide joystick values from any source.
//	   *
//	   * @param leftValue  The value of the left stick.
//	   * @param rightValue The value of the right stick.
//	   */
//	  public void tankDrive(double leftValue, double rightValue) {
//	    tankDrive(leftValue, rightValue, true);
//	  }
	  
	public void tankDrive(double leftSpeed, double rightSpeed) {

		robotDrive.tankDrive(leftSpeed, rightSpeed);

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void setPosition(long distanceToDrive) {
		// TODO Auto-generated method stub
//		leftFrontMotor.changeControlMode(TalonControlMode.Position);
		leftFrontMotor.set(distanceToDrive);

	}

	public void setVelocity(double velocity) {
//		leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
		leftFrontMotor.set(velocity);

		
	}

	  /**
	   * Limit motor values to the -1.0 to +1.0 range.
	   */
	  protected static double limit(double num) {
	    if (num > 1.0) {
	      return 1.0;
	    }
	    if (num < -1.0) {
	      return -1.0;
	    }
	    return num;
	  }
	  
	  //Low to High gear
	  
	  public void changeToHighGear (){
		  Robot.pnuematicsSubsystem.highGear();
	  }
	  
	  public void changeToLowGear (){
		  Robot.pnuematicsSubsystem.lowGear();
	  }
	  
//	  public double GetVoltage() {
//		  return leftRearMotor.getBatteryVoltage();
//	  }
//	  
	  int RightCount;

//		public int rightGetCount() {
////			if (driveSpeedEncoder != null)
////				return RightCount = driveSpeedEncoder.get();
////			else
//				return (int) rightFrontMotor.getPosition();
//		}

		int RightRawCount;

//		public int rightGetRawCount() {
////			if (driveSpeedEncoder != null)
////				return RightRawCount = driveSpeedEncoder.getRaw();
////			else
//				return (int) rightFrontMotor.getEncPosition();
//		}

		int LeftCount;

//		public int leftGetCount() {
////			if (driveSpeedEncoder != null)
////				return LeftCount = driveSpeedEncoder.get();
////			else
//				return (int) leftFrontMotor.getPosition();
//		}

		int LeftRawCount;

//		public int leftGetRawcount() {
////			if (driveSpeedEncoder != null)
////				return LeftRawCount = driveSpeedEncoder.getRaw();
////			else
//				return (int) leftFrontMotor.getPosition();
//		}

}