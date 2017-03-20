package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
//import org.usfirst.frc.team2903.robot.commands.commoners.GearAim;
//import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleGear extends CommandGroup
{

	public MiddleGear() throws InterruptedException
	{
		requires(Robot.driveSubsystem);

		addSequential(new DriveStraightForDistance(27, true)); //28
		//addSequential(new DriveStraightForDistance(-1, true));
		//addSequential(new DriveStraightForDistance(16, false)); //50

	}

}