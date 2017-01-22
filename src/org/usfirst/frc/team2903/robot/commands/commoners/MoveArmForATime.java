package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveArmForATime extends Command {
	
	double TimeForArm;
	double timeInSeconds;
	Timer timer = new Timer();

    public MoveArmForATime(double timeInSeconds) {
      super("MoveArmForATime");
      requires(Robot.armSubsystem);
      
      this.timeInSeconds = timeInSeconds;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
		timer.start();
		TimeForArm =timer.get();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.armSubsystem.lowerArm();
		TimeForArm = timer.get();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	if(TimeForArm >= timeInSeconds){
    		Robot.armSubsystem.stopArm();
			return true;
		}
    	
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
