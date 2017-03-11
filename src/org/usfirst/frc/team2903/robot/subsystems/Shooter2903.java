package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Servo;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter2903 extends Subsystem {

	static final double PI = 3.14159;

	static final double COUNTS_PER_MOTOR_REV = 1024; // Quad Encoder
	static final double DRIVE_GEAR_REDUCTION = 1.1;
	static final double WHEEL_DIAMETER_INCHES = 4.0;
	static final double WHEEL_CIRCUMFERENCE_INCHES = WHEEL_DIAMETER_INCHES * PI;

	static final double COUNTS_PER_INCH = ((COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)
			/ (WHEEL_DIAMETER_INCHES * 3.141595));

	// gate positions
	static final double OPEN_GATE_POSITION = 0;
	static final double CLOSED_GATE_POSITION = 1;

	// shooter and kicker talons
	static CANTalon shootMotor;
	static CANTalon shakeMotor;
	private Servo gate;

	// variables
	static final double optimalSpeed = 0.62;

	public Shooter2903() {

		super("Shooter2903");

		shootMotor = new CANTalon(RobotMap.shootMotor);
		shakeMotor = new CANTalon(RobotMap.shakeMotor);
		gate = new Servo(1);
		shootMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		shootMotor.changeControlMode(TalonControlMode.PercentVbus);

		shootMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		shootMotor.configPeakOutputVoltage(+12.0f, -12.0f);
		shootMotor.setProfile(0);
		shootMotor.setF(0);

		// enable the motors
		shakeMotor.enable();
		shootMotor.enable();
		// KickingMotor.enable();

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void shoot() {
		// shootMotor.set(0.62);
		shootMotor.set(0.5);
	}

	public void shaker(double shakerSpeed) {
		shakeMotor.set(shakerSpeed);
	}

	public void StopShoot() {
		// spit balls out
		shootMotor.set(0);
	}

	public void OpenGate() {
		if (gate.get() == CLOSED_GATE_POSITION) {
			gate.set(OPEN_GATE_POSITION);
		}
	}
	public void CloseGate(){
		if(gate.get() == OPEN_GATE_POSITION){
			gate.set(CLOSED_GATE_POSITION);
		}
	}


	public void GateControl() {
		// controls gate opening and closing
		if (shootMotor.get() == optimalSpeed){
			OpenGate();
		}
		else{
			CloseGate();
		}
	}

}
