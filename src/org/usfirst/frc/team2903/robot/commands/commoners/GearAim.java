package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.subsystems.GearPegPipeline2903;
import edu.wpi.first.wpilibj.command.Command;

public class GearAim extends Command {

	private double centerX;
	private GearPegPipeline2903 gppSubsystem;

	public GearAim() {
		super("GearAim");
		requires (Robot.driveSubsystem);
		
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		gppSubsystem = new GearPegPipeline2903();
		centerX = 0;
	}

	@Override
	protected void execute() {
		centerX = gppSubsystem.getCenterX();
	}

	@Override
	protected boolean isFinished() {
		
		double turn = centerX - (GearPegPipeline2903.IMG_WIDTH / 2);
		if (turn != 0)
		{
			Robot.driveSubsystem.arcadeDrive(0, turn * 0.005);
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.driveSubsystem.arcadeDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
