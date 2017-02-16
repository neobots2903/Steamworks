package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveForDistanceTest extends CommandGroup {

	public DriveForDistanceTest() throws InterruptedException{
		requires(Robot.driveSubsystem);

		addSequential(new DriveStraightForDistance(12));
//		Thread.sleep(1000);
//		addSequential(new DriveStraightForDistance(24));
//		Thread.sleep(1000);
//		addSequential(new DriveStraightForDistance(36));
//		Thread.sleep(1000);
//		addSequential(new DriveStraightForDistance(48));
//		Thread.sleep(1000);
//		addSequential(new DriveStraightForDistance(-48));
//		Thread.sleep(1000);
//		addSequential(new DriveStraightForDistance(-36));
//		Thread.sleep(1000);
//		addSequential(new DriveStraightForDistance(-24));
//		Thread.sleep(1000);
//		addSequential(new DriveStraightForDistance(-12));
//		Thread.sleep(1000);

	}
	
}