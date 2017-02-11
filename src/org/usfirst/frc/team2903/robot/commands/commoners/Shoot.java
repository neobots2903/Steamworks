package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Shoot extends Command {

	public Shoot() {
		super("Shoot");
		// requires(Robot.shootSubsystem);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		// requires(Robot.shooterSubsystem);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean isFinished() {
		return false;
		// TODO Auto-generated method stub
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
