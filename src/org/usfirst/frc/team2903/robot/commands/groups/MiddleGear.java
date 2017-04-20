package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;
import org.usfirst.frc.team2903.robot.commands.commoners.Shoot;
//import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MiddleGear extends CommandGroup {
	
	public MiddleGear() throws InterruptedException{
		requires(Robot.driveSubsystem);
//		addSequential(new DriveStraightForDistance(114,true));
		addSequential(new OpenArms());
	}

	
}