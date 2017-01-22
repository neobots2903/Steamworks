package org.usfirst.frc.team2903.robot.commands.groups;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.HorizontalAim;
import org.usfirst.frc.team2903.robot.commands.commoners.Shoot;
import org.usfirst.frc.team2903.robot.commands.commoners.SpinUpShooter;
import org.usfirst.frc.team2903.robot.commands.commoners.VerticalAim;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AimBots extends CommandGroup{
	int index;

	//We need to convert pixels to motor movements
	//To add a note i can change the size of the image. Currently its 400 to 280
	
	public AimBots(boolean Shoot) {
		
		super("AimBots");
		index = Robot.cameraSubsystem.GetBiggestAreaIndex();
		
		addParallel(new HorizontalAim(index));
		addParallel(new VerticalAim(index));
		addSequential(new HorizontalAim(index));
		addSequential(new VerticalAim(index));
		if (Shoot){
			addSequential(new SpinUpShooter(true));
			addSequential(new Shoot());
		}
		
		/**int index = 0;

		index = Robot.cameraSubsystem.GetBiggestAreaIndex();
		
		double offsetX = Robot.cameraSubsystem.GetOffsetX(index); 
		if (offsetX < -5) {
			// then move robot to the left
		}
		if (offsetX > 5) {
			// then move robot to the right
		}
		
		double offsetBound = Robot.cameraSubsystem.GetOffsetBounding(index);
		if (offsetBound < -5) {
			// move shooter up
		}
		if (offsetBound > 5) {
			// move shooter down
		}
		  
		if (offsetX > -5 && offsetX < 5 && offsetBound > -5 && offsetBound < 5) {
			// FIRE THE CANNONS!!!
		}**/
	}
}
