package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class Pnuematics2903 {
	
	public Solenoid gearShift = null; //new Solenoid(RobotMap.gearShift); 
	public Solenoid gearDelivery = null; //new Solenoid(RobotMap.gearDelivery);
	public boolean isOpen = false;
	
	public void openarms()
	{
		gearDelivery.set(true);
	}
	
	public void closearms()
	{
		gearDelivery.set(false);
	}
	
	
	public void highGear()
	{
		gearShift.set(false);
	}
	
	public void lowGear()
	{
		gearShift.set(true);
	}
	
	
	public void armReset()
	{
		closearms();
	}

	public void armsOpenClose()
	{
		if (isOpen)
		{
			closearms();
			isOpen = false;
		}
		else
		{
			openarms();
			isOpen = true; 
		}
	}
		
}
