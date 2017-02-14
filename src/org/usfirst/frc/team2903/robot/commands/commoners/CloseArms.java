package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CloseArms extends Command {

	// TODO update for two encoders with average of the two and gyro 
	
	
	public CloseArms()
	{
		super("CloseArms");
		
		requires(Robot.gearSubsystem);
		
		
	}
	
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		Robot.gearSubsystem.closeArms();
	}

	@Override
	protected boolean isFinished() {
		return false;
		}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
	
	}

}