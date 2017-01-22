package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public  class DriveForTime extends Command{
	double TimeForDistance;
	double timeInSeconds;
	Timer timer = new Timer();
	
	public DriveForTime(double timeInSeconds){
		super("DriveForTime");
		requires(Robot.driveSubsystem);

		this.timeInSeconds = timeInSeconds;
	}
	
	protected void initialize(){
		//TODO Auto-generated method stub
		timer.reset();
		timer.start();
		TimeForDistance =timer.get();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		//DriveStraightWithGyro.GyroPID(Robot.driveSubsystem, Robot.gyroSubsystem);
		Robot.driveSubsystem.tankDrive(0.6, -0.6);
		TimeForDistance = timer.get();
	}
	
	@Override
	
	protected boolean isFinished() {
		// TODO Auto-generated method stub

		if(TimeForDistance >= timeInSeconds){
			return true;
		}
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		timer.stop();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
}