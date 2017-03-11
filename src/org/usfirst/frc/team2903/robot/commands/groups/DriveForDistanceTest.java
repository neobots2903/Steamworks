package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightBackwardsForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
//import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveForDistanceTest extends CommandGroup {

	public DriveForDistanceTest() throws InterruptedException{
		requires(Robot.driveSubsystem);

		addSequential(new DriveStraightForDistance(106));
		addSequential(new TurnWithGyro(58.5));
		addSequential(new DriveStraightForDistance(-1));
		addSequential(new DriveStraightForDistance(12));
	}
	
}