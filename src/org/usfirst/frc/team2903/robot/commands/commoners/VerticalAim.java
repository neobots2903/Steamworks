package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class VerticalAim extends Command {

	int index;
	
	public VerticalAim(int index) {
		super("VerticalAim");
		this.index = index;
		requires(Robot.cameraSubsystem);
		requires(Robot.armSubsystem);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		double VerticalAngle = Robot.cameraSubsystem.GetVerticalAngle(index);
		Robot.armSubsystem.setTargetAngle(VerticalAngle);
		Robot.armSubsystem.moveArm();
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Robot.armSubsystem.isTargetReached();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.armSubsystem.stopArm();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
