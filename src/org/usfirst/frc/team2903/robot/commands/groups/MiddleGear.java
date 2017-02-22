package org.usfirst.frc.team2903.robot.commands.groups; 
 
import org.usfirst.frc.team2903.robot.Robot; 
import org.usfirst.frc.team2903.robot.commands.commoners.CloseArms; 
import org.usfirst.frc.team2903.robot.commands.commoners.DriveToPositionTest; 
import org.usfirst.frc.team2903.robot.commands.commoners.OpenArms; 
 
import edu.wpi.first.wpilibj.command.CommandGroup; 
 
public class MiddleGear extends CommandGroup{ 
   
  public MiddleGear() { 
    requires(Robot.driveSubsystem); 
     
    addSequential(new DriveToPositionTest(78)); 
    addSequential(new OpenArms()); 
    addSequential(new DriveToPositionTest(-5)); 
    addSequential(new CloseArms()); 
 
    } 
     
 
  } 