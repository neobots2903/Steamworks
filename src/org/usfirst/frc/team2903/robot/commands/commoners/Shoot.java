package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shoot extends Command {

	public Shoot() {
		super("Shoot");
		 requires(Robot.driveSubsystem);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		// requires(Robot.shooterSubsystem);
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("Switch value", Robot.pnuematicsSubsystem.leftLimitSwitch.getVoltage());

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
