package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveInAOneFootSquare extends CommandGroup {

	public DriveInAOneFootSquare(){
		//addSequential(new TurnWithGyro(0));
		addSequential(new DriveStraightForDistance(12, true));
		addSequential(new TurnWithGyro(90));
		Robot.gyroSubsystem.reset();
		addSequential(new DriveStraightForDistance(12, true));
		addSequential(new TurnWithGyro(180));
		Robot.gyroSubsystem.reset();
		addSequential(new DriveStraightForDistance(12, true));
		addSequential(new TurnWithGyro(270));
		Robot.gyroSubsystem.reset();
		addSequential(new DriveStraightForDistance(12, true));
//		addSequential(new TurnWithGyro(360));
		


	}
	
}