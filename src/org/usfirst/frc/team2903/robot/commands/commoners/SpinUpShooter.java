package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SpinUpShooter extends Command {

	boolean HighShooter;
	
	public SpinUpShooter(boolean highShoot) {
		super("SpinUpShooter");
		
		requires(Robot.shooterSubsystem);
	
		HighShooter = highShoot;
	}

	@Override
	protected void initialize() {
		
		if (HighShooter)
			Robot.shooterSubsystem.enableHighGoalMode();
		else
			Robot.shooterSubsystem.enableLowGoalMode();
			

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		
		// how do we know we have spun up the shooter?
		
		return false;
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
