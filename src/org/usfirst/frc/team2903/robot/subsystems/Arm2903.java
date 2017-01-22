package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
//import edu.wpi.first.wpilibj.
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Arm2903 extends Subsystem {

	public CANTalon ArmMotor;
	boolean IsReset;
	boolean IsCalibrated;
	boolean IsTargetAngleSet;
	
	// LimitSwitches
	//public DigitalInput bottomLimit = new DigitalInput(RobotMap.botLimitSwitch);
	//public DigitalInput topLimit = new DigitalInput(RobotMap.botLimitSwitch);

	// LimitSwitches' Booleans
	//boolean reachedBottomLimit = false;
	//boolean reachedTopLimit = false;
	
	
	Potentiometer pot;
	public PIDController autoShooter;// = new PIDController(0,1, 0.001, 0.0, pot, lifter); //PID controller

	double AngleError;
	double ArmResetAngle; // the starting position of the potentiometer when
							// calibrated.
	double TargetArmAngle; // the target position of the arm -- note that this
							// could be above or below the current position

	double MaxArmAngle; // the maximum angle for the arm to move

	double ArmMotorSpeed; // the speed at which to move the arm motor

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public Arm2903() {

		ArmMotor = new CANTalon(RobotMap.armMotor);

		pot = new AnalogPotentiometer(1, 360, 0);

		autoShooter = new PIDController(0.5, 0.0, 0.0, pot, ArmMotor); //PID controller

		IsReset = false;
		IsCalibrated = false;
		IsTargetAngleSet = false;
		ArmResetAngle = 0;
		AngleError = 2;
		MaxArmAngle = 1000;
		ArmMotorSpeed = 0.6;
	}

	// resets the arm to the calibrated angle
	public void resetArm() {

		if (pot.get() > ArmResetAngle) {
			lowerArm();
			IsReset = false;
		} else {
			stopArm();
			IsReset = true;
		}
	}

	// returns whether or not the arm is reset.
	public boolean isReset()
	{
		return IsReset;
	}
	

	// this sets the reset point of the angle and will only set the calibration
	// once.
	public void calibrate() {
		// TODO Auto-generated method stub
		ArmResetAngle = pot.get();
		IsCalibrated = true;
	}

	// returns whether or not the reset arm angle is calibrated
	public boolean isCalibrated()
	{
		return IsCalibrated;
	}
	

	// this sets the target angle for automatic movement and the IsTargetReached
	// method.
	public void setTargetAngle(double armTargetAngle) {
		// TODO Auto-generated method stub

		if (armTargetAngle > MaxArmAngle)
			TargetArmAngle = MaxArmAngle;
		else if (armTargetAngle < ArmResetAngle)
			TargetArmAngle = ArmResetAngle;
		else
			TargetArmAngle = armTargetAngle;
		IsTargetAngleSet = true;
	}

	// this sets the arm motor speed.
	public void setMotorSpeed(double armMoveSpeed) {
		ArmMotorSpeed = armMoveSpeed;

	}

	// Returns a boolean as to whether the target angle has been reached
	// returns true of the angle is between target-2 and target.
	// returns false if target not in range, if not calibrated, or if
	// target angle has not been reached.
	public boolean isTargetReached() {

		boolean returnValue = false;

		if (IsCalibrated && IsTargetAngleSet) {

			if (pot.get() >= (TargetArmAngle - AngleError) && pot.get() <= (TargetArmAngle + AngleError))
				returnValue = true;
		}
		return returnValue;
	}

	// this moves the arm based upon the TargetArmAngle. It will raise
	// if the target angle is larger than the current potentiometer reading,
	// lower the arm if the target angle is less than the current potentiometer
	// and stop moving it otherwise.
	public void moveArm() {
		// TODO Auto-generated method stub
		if (TargetArmAngle < pot.get())
			lowerArm();
		else if (TargetArmAngle > pot.get())
			raiseArm();
		else
			stopArm();
	}

	// this raises the arm, but limits the movement to no further than the
	// maximum angle
	public void raiseArm() {
		
		//if(reachedTopLimit = true){
		//	stopArm();
		//}
	//	if (pot.get() < MaxArmAngle) 
		{
			ArmMotor.enableBrakeMode(false);
			ArmMotor.set(ArmMotorSpeed);
			IsReset=false;
		}
//		else
			//stopArm();
	}

	// this lowers the arm, but limits the movement to no further than the reset
	// angle
	public void lowerArm() {
		//if(reachedBottomLimit = true){
		//	stopArm();
		//}
		//if (pot.get() > ArmResetAngle) 
		{
			ArmMotor.enableBrakeMode(false);
			ArmMotor.set(ArmMotorSpeed*-1);
			IsReset=false;
		}
		//else{
			//stopArm();
		//}
	}

	 //this stops the arm from moving.
	public void stopArm() {
		// TODO Auto-generated method stub
		ArmMotor.set(0);
		ArmMotor.enableBrakeMode(true);
		IsReset=false;

	}

}
