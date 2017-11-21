package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightForDistance;
import org.usfirst.frc.team2903.robot.commands.commoners.GearAim;
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class RightGear extends CommandGroup {

	//Angle when driving straight from base line to the gear peg
	static final double sideGearPegAngle = -60;
	
	public RightGear() throws InterruptedException{
		requires(Robot.driveSubsystem);
		requires(Robot.gearSubsystem);

//		for (int i = 0; i < 5; i++) {
			addSequential(new DriveStraightForDistance(64,true,false));
			addSequential(new TurnWithGyro(sideGearPegAngle));
//			addSequential(new DriveStraightForDistance(-1,true,false));
			//addSequential(new DriveStraightForDistance(1,true,true));
			//addSequential(new OpenArms());	
			//addSequential(new WaitCommand(0.3));
			//addSequential(new DriveForTime(0.5, true));
//		}
	}
	
}