package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.commands.commoners.AimShooter;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForward;
import org.usfirst.frc.team2903.robot.commands.commoners.LiftPortcullis;
import org.usfirst.frc.team2903.robot.commands.commoners.Shoot;
import org.usfirst.frc.team2903.robot.commands.commoners.SpinUpShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LiftAndCrossPortcullis extends CommandGroup {
	
	public LiftAndCrossPortcullis(boolean shootBoulder)
	{
		addSequential(new DriveForward(10));
		addSequential(new LiftPortcullis());
		addSequential(new DriveForward(10));
		if (shootBoulder)
		{
			addSequential(new AimShooter());
			addParallel(new SpinUpShooter(true));
			addSequential(new Shoot());
		}
	}
        
}
