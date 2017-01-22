package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForTime;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveInAOneSecondSquare extends CommandGroup {

	public DriveInAOneSecondSquare(){
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);

		//Robot.gyroSubsystem.Calibrate();

		addSequential(new DriveForTime(1));
		addSequential(new TurnWithGyro(90));
		addSequential(new DriveForTime(1));
		addSequential(new TurnWithGyro(180));
		addSequential(new DriveForTime(1));
		addSequential(new TurnWithGyro(270));		
		addSequential(new DriveForTime(1));
		addSequential(new TurnWithGyro(360));

	}
	
}