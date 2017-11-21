package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.hal.DIOJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pnuematics2903 {
	
	public Solenoid highGearShift = new Solenoid(RobotMap.highGearShiftSol); 
	public Solenoid lowGearShift = new Solenoid(RobotMap.lowGearShiftSol); 
	public Solenoid gearArmsOpen = new Solenoid(RobotMap.gearArmsOpen);
	public Solenoid gearArmsClosed = new Solenoid(RobotMap.gearArmsClose);
	
	//public DigitalInput leftLimitSwitch = new DigitalInput(RobotMap.leftLimitSwitch);
	
	public Pnuematics2903 () {
		highGearShift.clearAllPCMStickyFaults();
		
		gearArmsClosed.set(false);
		gearArmsOpen.set(true);
		highGearShift.set(false);
		lowGearShift.set(true);
	}
	public void closearms()
	{
		if (gearArmsClosed.get()){
			gearArmsClosed.set(false);
			gearArmsOpen.set(true);
		}
	}
	
	public void openarms()
	{
		if (gearArmsOpen.get()){
			gearArmsOpen.set(false);
			gearArmsClosed.set(true);
		}
	}
	
	
	public void highGear()
	{
		if(lowGearShift.get()){
			lowGearShift.set(false);
			highGearShift.set(true);
		}
	}
	
	public void lowGear()
	{
		if(highGearShift.get()){
			highGearShift.set(false);
			lowGearShift.set(true);
		}
	}
	
	public boolean limitSwitchesPressed() {
//		SmartDashboard.putBoolean("Limit Switch Closed?", !leftLimitSwitch.get());
//		return !leftLimitSwitch.get(); // > 0;
		SmartDashboard.putBoolean("Limit Switch Closed?", !Robot.pressurePlateSwitch.get());
		return !Robot.pressurePlateSwitch.get(); // > 0;
	  }
	
	
	public void armReset()
	{
		closearms();
	}
		
}
