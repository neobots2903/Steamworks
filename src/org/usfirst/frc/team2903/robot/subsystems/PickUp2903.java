package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class PickUp2903 extends Subsystem {
	
	// motor
	static CANTalon PickUpMotor;

	public PickUp2903() {
		
		super("PickUp2903");
		
		// instantiate the talon motor controllers
		PickUpMotor = new CANTalon(RobotMap.PickUpMotor);

		
		// enable the motors
		PickUpMotor.enable();
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void PickUp() {
		//Pick balls up 
		PickUpMotor.set(0.5);
	}

	

	public void SpitOut() {
		//spit balls out
		PickUpMotor.set(-0.5);
	}
}
