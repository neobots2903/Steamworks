package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveForDistanceTest extends CommandGroup {

	public DriveForDistanceTest() throws InterruptedException{
		requires(Robot.driveSubsystem);

		addSequential(new DriveStraightForDistance(78));
//		addSequential(new TurnWithGyro(58.5));
//		addSequential(new DriveStraightForDistance(-1));
//		addSequential(new DriveStraightForDistance(14));
	}
	
}