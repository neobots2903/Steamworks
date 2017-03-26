package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commands.commoners.LowGear;
//import org.usfirst.frc.team2903.robot.commands.commoners.GearAim;
//import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MinimalAutonomous extends CommandGroup {
	
	public MinimalAutonomous() throws InterruptedException{
		requires(Robot.driveSubsystem);
		addSequential(new LowGear());
		addSequential(new DriveStraightForDistance(12,true));
		addSequential(new DriveStraightForDistance(12,true));
		addSequential(new DriveStraightForDistance(12,true));
		addSequential(new DriveStraightForDistance(12,true));

		
	}

}