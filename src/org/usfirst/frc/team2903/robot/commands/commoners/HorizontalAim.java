package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HorizontalAim extends Command {

	int index;
	Command turnGyro;

	public HorizontalAim(int index) {
		super("HorizontalAim");
		this.index = index;
		requires(Robot.driveSubsystem);
		requires(Robot.cameraSubsystem);
		requires(Robot.gyroSubsystem);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		double HorizontalAngle = Robot.cameraSubsystem.GetHorizontalAngle(index);
		turnGyro = new TurnWithGyro(HorizontalAngle);
		turnGyro.start();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return !turnGyro.isRunning();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		if (turnGyro.isRunning()) {
			turnGyro.cancel();
		}
	}

}
