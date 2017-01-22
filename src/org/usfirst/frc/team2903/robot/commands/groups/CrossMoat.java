package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.commands.commoners.AimShooter;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commands.commoners.LowerArmsDefault;
import org.usfirst.frc.team2903.robot.commands.commoners.MoveArmForATime;
import org.usfirst.frc.team2903.robot.commands.commoners.Shoot;
import org.usfirst.frc.team2903.robot.commands.commoners.SpinUpShooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CrossMoat extends CommandGroup {

	public CrossMoat(boolean shoot) {
		super("CrossMoat");
		
			//addSequential(new LowerArmsDefault());
			addSequential(new MoveArmForATime(0.85));
			addParallel(new DriveForTime(4));
		
		if (shoot) {
			addParallel(new SpinUpShooter(true));
			addSequential(new Shoot());
		}
	}

}
