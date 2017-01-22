package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.commands.commoners.AimShooter;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForward;
import org.usfirst.frc.team2903.robot.commands.commoners.Shoot;
import org.usfirst.frc.team2903.robot.commands.commoners.SpinUpShooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBar extends CommandGroup {

	public LowBar(boolean shoot) {
		super("LowBar");
		addSequential(new DriveForward(1));

		if (shoot) {
			addSequential(new AimShooter());
			addParallel(new SpinUpShooter(true));
			addSequential(new Shoot());
		}
	}

}
