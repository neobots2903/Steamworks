package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber2903 extends Subsystem {
	
	// motor
	static CANTalon ClimberMotor;

	public Climber2903() {
		
		super("Climber2903");
		
		// instantiate the talon motor controllers
		ClimberMotor = new CANTalon(RobotMap.WinchMotor);

		
		// enable the motors
		ClimberMotor.enable();
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void Fall() {
		//Pick balls up 
		ClimberMotor.set(-1);
	}

	

	public void LiftOff() {
		//spit balls out
		ClimberMotor.set(1);
	}
	
	public void StopLift(){
		ClimberMotor.set(0);
	}
}
