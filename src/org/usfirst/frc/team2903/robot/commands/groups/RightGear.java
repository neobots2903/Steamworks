package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightGear extends CommandGroup {

	public RightGear() {
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);
		requires(Robot.cameraSubsystem);
	}

}