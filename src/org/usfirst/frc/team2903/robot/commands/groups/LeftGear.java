package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.GearAim;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;
//import org.usfirst.frc.team2903.robot.commands.commoners.CloseArms;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftGear extends CommandGroup {

	//Angle when driving straight from base line to the gear peg
	static final double sideGearPegAngle = 50;
	
	public LeftGear() throws InterruptedException{
		requires(Robot.driveSubsystem);
		requires(Robot.gearSubsystem);

//		for (int i = 0; i < 5; i++) {
			addSequential(new DriveStraightForDistance(83, true));
			addSequential(new TurnWithGyro(sideGearPegAngle));
			//addSequential(new GearAim());
			addSequential(new DriveStraightForDistance(-1,true));
			addSequential(new DriveStraightForDistance(50,false));
			addSequential(new OpenArms());
			
//		}
	}
	
}