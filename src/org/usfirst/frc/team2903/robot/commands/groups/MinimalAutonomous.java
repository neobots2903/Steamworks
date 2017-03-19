package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
//import org.usfirst.frc.team2903.robot.commands.commoners.GearAim;
//import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MinimalAutonomous extends CommandGroup {
	
	public MinimalAutonomous() throws InterruptedException{
		requires(Robot.driveSubsystem);

			addSequential(new DriveStraightForDistance(81,true));
			//addSequential(new OpenArms());
	}

}