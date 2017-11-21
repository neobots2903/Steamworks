package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;
import org.usfirst.frc.team2903.robot.commands.commoners.CloseArms;
import org.usfirst.frc.team2903.robot.commands.commoners.Shoot;
//import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MiddleGear extends CommandGroup {
	
	public MiddleGear() throws InterruptedException{
		requires(Robot.driveSubsystem);
		//addSequential(new DriveStraightForDistance(78,true,true));
		addSequential(new DriveStraightForDistance(12, true, true));
		//addSequential(new OpenArms());
//		addSequential(new WaitCommand(0.3));
//		addSequential(new DriveForTime(0.75, true));
	}

	
}