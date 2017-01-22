package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class AutoBase extends Command {

	public AutoBase() {
		requires(Robot.driveSubsystem);
//		requires(Robot.gyroSubsystem);
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
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
