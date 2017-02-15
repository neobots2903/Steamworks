package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LIDARTest extends Command {

	public LIDARTest() {
		super("LIDARTest");
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		Robot.lidarSubsystem.start();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Distance ", Robot.lidarSubsystem.getDistance());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.lidarSubsystem.stop();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		Robot.lidarSubsystem.stop();
	}

}
