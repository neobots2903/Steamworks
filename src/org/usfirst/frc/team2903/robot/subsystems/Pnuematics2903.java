package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Pnuematics2903 {

	public Solenoid highGearShift = new Solenoid(RobotMap.highGearShiftSol);
	public Solenoid lowGearShift = new Solenoid(RobotMap.lowGearShiftSol);
	public Solenoid gearArmsOpen = new Solenoid(RobotMap.gearArmsOpen);
	public Solenoid gearArmsClosed = new Solenoid(RobotMap.gearArmsClose);
	
	
	public AnalogInput leftLimitSwitch = new AnalogInput(RobotMap.leftLimitSwitch);
	//public DigitalInput rightLimitSwitch = new DigitalInput(RobotMap.rightLimitSwitch);
	
	public double leftLimitPressed = leftLimitSwitch.getVoltage();
//public boolean rightLimitPressed = rightLimitSwitch.get();

	
	public Pnuematics2903() {
		highGearShift.clearAllPCMStickyFaults();

		gearArmsClosed.set(false);
		gearArmsOpen.set(true);
		highGearShift.set(false);
		lowGearShift.set(true);
	}

	public void closearms() {
		if (gearArmsClosed.get()) {
			gearArmsClosed.set(false);
			gearArmsOpen.set(true);
		}
	}

	public void openarms() {
		if (gearArmsOpen.get()) {
			gearArmsOpen.set(false);
			gearArmsClosed.set(true);
		}
	}

	public void highGear() {
		if (lowGearShift.get()) {
			lowGearShift.set(false);
			highGearShift.set(true);
		}
	}

	public void lowGear() {
		if (highGearShift.get()) {
			highGearShift.set(false);
			lowGearShift.set(true);
		}
	}
	
//	public boolean limitSwitchesPressed() {
//    if (leftLimitPressed < 0.1)// && rightLimitPressed)
//    	return true;
//    else
//    	return false;
//  }

	public void armReset() {
		closearms();
	}

}
