package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearDropOff extends Command {

	// TODO update for two encoders with average of the two and gyro 
	
	
	public GearDropOff(int distance)
	{
		super("GearDropOff");
		
		requires(Robot.driveSubsystem);
		requires(Robot.gearSubsystem);
		
	}
	
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		
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
