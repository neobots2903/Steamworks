package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowerArmsDefault extends Command {

    public LowerArmsDefault() {
        super ("LowerArmsDefault");
        requires(Robot.armSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.armSubsystem.ArmMotor.reverseOutput(false);
     Robot.armSubsystem.autoShooter.setOutputRange(-0.4,  0.4);
		Robot.armSubsystem.autoShooter.enable();
		Robot.armSubsystem.autoShooter.setSetpoint(220);	//ground level
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.armSubsystem.autoShooter.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
