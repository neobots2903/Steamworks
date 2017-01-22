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
		Robot.shooterSubsystem.Kick(-1.0);

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		Timer.delay(0.128);
		Robot.shooterSubsystem.Kick(0);
		Robot.shooterSubsystem.Kick(1.0);
		Timer.delay(0.087);
		Robot.shooterSubsystem.Kick(0);
		return true;
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
