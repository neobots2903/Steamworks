package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class Pnuematics2903 {
	
	public Solenoid highGearShift = new Solenoid(RobotMap.highGearShiftSol); 
	public Solenoid lowGearShift = new Solenoid(RobotMap.lowGearShiftSol); 
	public Solenoid gearArmsOpen = new Solenoid(RobotMap.gearArmsOpen);
	public Solenoid gearArmsClosed = new Solenoid(RobotMap.gearArmsClose);
	
	public void openarms()
	{
		if (gearArmsClosed.get()){
			gearArmsOpen.set(true);
		}
	}
	
	public void closearms()
	{
		if (gearArmsOpen.get()){
			gearArmsClosed.set(false);
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
	
	
	public void armReset()
	{
		closearms();
	}
		
}
