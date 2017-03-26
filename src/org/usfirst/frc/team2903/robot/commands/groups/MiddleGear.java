package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.GearAim;
import org.usfirst.frc.team2903.robot.commands.commoners.LowGear;
//import org.usfirst.frc.team2903.robot.commands.commoners.GearAim;
//import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleGear extends CommandGroup
{

	public MiddleGear() throws InterruptedException
	{
		requires(Robot.driveSubsystem);
		addSequential(new LowGear());
		//distance from front of robot to peg total is 68in
		addSequential(new DriveStraightForDistance(68, true));

	}

}