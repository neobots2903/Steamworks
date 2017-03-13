package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.commands.commoners.CloseArms;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StraightGearTime extends CommandGroup {

	public StraightGearTime() {
		addSequential(new DriveForTime(3.2));
		addSequential(new OpenArms());
		addSequential(new DriveStraightForDistance(-1, true));
		addSequential(new CloseArms());
		
		
	}


}