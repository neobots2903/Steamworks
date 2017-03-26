package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.GearAim;
//import org.usfirst.frc.team2903.robot.commands.commoners.GearAim;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import org.usfirst.frc.team2903.robot.commands.commoners.LowGear;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftGear extends CommandGroup {

	// Angle when driving straight from base line to the gear peg
	static final double sideGearPegAngle = 60.0;
	
	public LeftGear() throws InterruptedException {
			addSequential(new LowGear());
//			addSequential(new DriveStraightForDistance(79, true));
			addSequential(new TurnWithGyro(sideGearPegAngle));
			addSequential(new GearAim());
			addSequential(new DriveStraightForDistance(-1,true));
			addSequential(new DriveStraightForDistance(25, true));
			//addSequential(new DriveStraightForDistance(-1, true));

			
	}

}