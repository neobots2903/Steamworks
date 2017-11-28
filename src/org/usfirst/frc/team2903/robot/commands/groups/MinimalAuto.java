package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveToPosition;
//import org.usfirst.frc.team2903.robot.commands.commoners.GearAim;
//import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import org.usfirst.frc.team2903.robot.commands.commoners.Shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MinimalAuto extends CommandGroup {
	
	public MinimalAuto() throws InterruptedException{
		requires(Robot.driveSubsystem);
		Robot.driveSubsystem.driveReset();
		SmartDashboard.putNumber("Right E ", Robot.driveSubsystem.rightGetCount());
		addSequential(new DriveToPosition(12));
		//addSequential(new Shoot());
	}
	
}