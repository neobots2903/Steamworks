package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Gear2903 extends Subsystem {


	public Gear2903() {
		
		super("Gear2903");
		
		
	}
	
	//gear delivery system
	public void openArms (){
//		if (Robot.pnuematicsSubsystem.limitSwitchesPressed())
		Robot.pnuematicsSubsystem.openarms();
	}
	
	public void closeArms (){
//		if (Robot.pnuematicsSubsystem.limitSwitchesPressed())
		Robot.pnuematicsSubsystem.closearms();
	}
	

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	
	

}
