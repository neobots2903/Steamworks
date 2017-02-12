package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;
import org.usfirst.frc.team2903.robot.commands.commoners.CloseArms;

public class StraightGearNoVision extends CommandGroup {

	public StraightGearNoVision() {
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);

		addSequential(new DriveStraightForDistance(1));
		addSequential(new OpenArms());
		addSequential(new DriveStraightForDistance(-1));
		addSequential(new CloseArms());
		
		
	}

}