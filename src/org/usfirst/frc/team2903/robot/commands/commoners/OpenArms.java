package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OpenArms extends Command {

	// TODO update for two encoders with average of the two and gyro 
	
	
	public OpenArms()
	{
		super("OpenArms");
		
		requires(Robot.gearSubsystem);
		
		
	}
	
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("Switch value", Robot.pnuematicsSubsystem.leftLimitSwitch.getVoltage());
		Robot.gearSubsystem.openArms(true);
	}

	@Override
	protected boolean isFinished() {
		return true;
		}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
	
	}

}
