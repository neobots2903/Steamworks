package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.CloseArms;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightWithGyro;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StraightGearNoVision extends CommandGroup {

	public StraightGearNoVision() {
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);
		
		addSequential(new DriveForTime(4));
		addParallel(new DriveStraightWithGyro());
		addSequential(new OpenArms());
//		addSequential(new DriveStraightForDistance(-1));
		addSequential(new CloseArms());
		
		
	}

	private void addParallel(DriveStraightWithGyro driveStraightWithGyro) {
		// TODO Auto-generated method stub
		
	}

}