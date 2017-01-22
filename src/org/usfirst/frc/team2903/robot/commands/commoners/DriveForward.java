package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForward extends Command {

	long DistanceToDrive;
	
	public DriveForward(int distance)
	{
		super("DriveForward");
		
		requires(Robot.driveSubsystem);
		
		DistanceToDrive = Math.round((960 * distance) / 75.36);		
	}
	
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		Robot.driveSubsystem.setPosition(DistanceToDrive);

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
