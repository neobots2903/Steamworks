package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop extends Command {

	static final double errorTurn = 0.25;
	static final double Proportional = 0.03;
	static final double circleDegrees = 360;
	static final double adjustTurn = 0.01;

	public Teleop() {
		requires(Robot.driveSubsystem);
		requires(Robot.pickupSubsystem);

		// requires(Robot.pneumaticsSubsystem);
	}

	protected void initialize() {
		// Robot.elevatorSubsystem.encoder.reset();
		Robot.driveSubsystem.setTeleopMode();
		Robot.gyroSubsystem.reset();

		
		Robot.camera.setBrightness(100);
	}

	/*
	 * This routine is called by the scheduler on a regular basis so be careful
	 * when adding code to not cause blocking issues or delays as this will
	 * affect the performance of the robot.
	 */
	protected void execute() {
		SmartDashboard.putNumber("TurnWithGyro", Robot.gyroSubsystem.GyroPosition());
		SmartDashboard.putNumber("Left Encoder", Robot.driveSubsystem.rightGetRawCount());

		/***************************
		 * DRIVER CONTROLS
		 ***************************/

		/*
		 * Drive the robot arcade style. X-axis -- forward and reverse Y-axis --
		 * turn left and right
		 */
		double forward = Robot.joy1.getY();
		double turn = Robot.joy1.getX();
		Robot.driveSubsystem.arcadeDrive(forward, turn);

		// gear shifting
		// switch to high gear
		if (Robot.joy1.getRawButton(3)) {
			Robot.driveSubsystem.changeToHighGear();
		}

		// switch to low gear
		if (Robot.joy1.getRawButton(4)) {
			Robot.driveSubsystem.changeToLowGear();
		}

		// climb the rope
		if (Robot.joy1.getRawButton(8)) {
			Robot.climberSubsystem.LiftOff();
		}

		// stop climing
		else {
			Robot.climberSubsystem.StopLift();
		}

		/***************************
		 * OPERATOR CONTROLS
		 ***************************/

		// ball agitator
		double shaker = Robot.joyOp.getY();
		Robot.shooterSubsystem.shaker(shaker);

		// see if the ball gate needs to be opened to allow a ball into the
		// shooter
		Robot.shooterSubsystem.GateControl();

		// open the arms holding the gear
		if (Robot.joyOp.getRawButton(2)) {
			Robot.gearSubsystem.openArms();
		}

		// close the arms
		if (Robot.joyOp.getRawButton(3)) {
			Robot.gearSubsystem.closeArms();
		}

		// spin the fuel pick up motor
		if (Robot.joyOp.getRawButton(5)) {
			Robot.pickupSubsystem.PickUp();
		}

		// reverse the pickup motor
		else if (Robot.joyOp.getRawButton(6)) {
			Robot.pickupSubsystem.SpitOut();
		}

		// stop spinning the pick up motor
		else {
			Robot.pickupSubsystem.StopPickUp();
		}

		// spin up the shoot wheel
		if (Robot.joyOp.getRawButton(1)) {
			Robot.shooterSubsystem.shoot();
		} else {
			Robot.shooterSubsystem.StopShoot();
		}

		/*
		 * Once we have further functionality for buttons add it here/
		 */
	}

	/*
	 * This is also called by the scheduler on a regular basis and will cause
	 * the robot to cease to function for the duration of the match if true is
	 * returned. Return true if the robot work is finished Return false if the
	 * robot is not finished
	 */
	protected boolean isFinished() {
		return false;
	}

	/*
	 * This is called when the match ends or if isFinished returns true. all
	 * systems should be disabled at this point.
	 */
	protected void end() {
	}

	/*
	 * This is called if for some reason the normal operation of the robot is
	 * cancelled by an external action.
	 */
	protected void interrupted() {
	}
}
