package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;

public class StraightGear extends CommandGroup {

	public StraightGear() {
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);
		requires(Robot.cameraSubsystem);

	}

}