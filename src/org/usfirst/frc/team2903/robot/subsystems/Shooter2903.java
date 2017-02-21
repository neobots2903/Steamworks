package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter2903 extends Subsystem {

	// shooter and kicker talons
	static CANTalon shootMotor;
	// static CANTalon KickingMotor;

	
	//variables 
	static final double optimalSpeed = 0.75;

	public Shooter2903() {

		super("Shooter2903");

		
		shootMotor = new CANTalon(RobotMap.shootMotor);
		shootMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		shootMotor.changeControlMode(TalonControlMode.PercentVbus);
		
		shootMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		shootMotor.configPeakOutputVoltage(+12.0f, -12.0f);
		shootMotor.setProfile (0);
		shootMotor.setF (0);
		
		// enable the motors
		shootMotor.enable();
		// KickingMotor.enable();

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void shoot() {
		shootMotor.set(0.62);
		//if(ShootMotor.get() = optimalSpeed){
		//	kick 
		//}
	}
	
	public void StopShoot() {
		//spit balls out
		shootMotor.set(0);
	}

	
}
