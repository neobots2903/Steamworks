package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.RobotMap;
import com.ctre.CANTalon;
import org.usfirst.frc.team2903.robot.subsystems.Pnuematics2903;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gear2903 extends Subsystem {


	public Gear2903() {
		
		super("Gear2903");
		
		
	}
	
	//gear delivery system
	public void openArms (){
		Robot.pnuematicsSubsystem.openarms();
	}
	
	public void closeArms (){
		Robot.pnuematicsSubsystem.closearms();
	}
	

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	
	

}
