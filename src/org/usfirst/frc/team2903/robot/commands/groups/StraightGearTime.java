package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.CloseArms;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightWithGyro;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StraightGearTime extends CommandGroup {

	public StraightGearTime() {
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);
		
		//Robot.gyroSubsystem.reset();
		Robot.gyroSubsystem.Calibrate();
		
		addSequential(new DriveForTime(3.2, false));
		addSequential(new OpenArms());
//		addSequential(new DriveStraightForDistance(-1));
		addSequential(new CloseArms());
		
		
	}


}