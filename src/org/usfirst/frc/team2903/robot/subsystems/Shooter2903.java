package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter2903 extends Subsystem {

	// shooter and kicker talons
	static CANTalon ShootMotor;
	// static CANTalon KickingMotor;

	// encoder for shooter motors
	static Encoder shooterLeftSpeedEncoder;
	static Encoder shooterRightSpeedEncoder;

	public Shooter2903() {

		super("Shooter2903");

		// instantiate the talon motor controllers
		ShootMotor = new CANTalon(RobotMap.ShootMotor);

		// enable the motors
		ShootMotor.enable();
		// KickingMotor.enable();

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void selectGoalMode(boolean highGoal) {

	}

	public void enableLowGoalMode() {
	}

	public void enablePickupMode() {

	}

	public void disableShooter() {

	}
}
