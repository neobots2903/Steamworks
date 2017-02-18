package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop extends Command {

	static final double errorTurn = 0.25;
	static final double Proportional = 0.03;
	static final double circleDegrees = 360;
	static final double adjustTurn = 0.01;
	
	public Teleop() {
		requires(Robot.driveSubsystem);
		requires(Robot.pickupSubsystem);
		
		// requires(Robot.pneumaticsSubsystem);
	}

	protected void initialize() {
		// Robot.elevatorSubsystem.encoder.reset();
	}
	
	/*
	 * This routine is called by the scheduler on a regular basis so be careful
	 * when adding code to not cause blocking issues or delays as this will affect
	 * the performance of the robot.
	 */
	protected void execute() {
			
		/*
		 * Drive the robot arcade style.  
		 * X-axis -- forward and reverse
		 * Y-axis -- turn left and right
		 */
		
		//Driver
		double forward = Robot.joy1.getX();
		double turn = Robot.joy1.getY();
		SmartDashboard.putNumber("Forward", forward);
		SmartDashboard.putNumber("Turn", turn);
//		if (-errorTurn >= turn && turn <= errorTurn) {
//			double angle = Robot.gyroSubsystem.GyroPosition() % circleDegrees;
//			angle = angle / circleDegrees;
//			Robot.driveSubsystem.arcadeDrive(forward, -angle * Proportional);
//		} else {
	//		}			
		
		//drive straight error adjustment
//		if (Robot.joy1.getThrottle() < 1){
//			turn = turn + Robot.joy1.getThrottle() / 2;
//		}
		Robot.driveSubsystem.arcadeDrive(forward, turn);
		//Robot.driveSubsystem.tankDrive(forward, forward);

		//driver
		if (Robot.joy1.getRawButton(3)){
			Robot.driveSubsystem.changeToHighGear();
		}
		
		if (Robot.joy1.getRawButton(4)){
			Robot.driveSubsystem.changeToLowGear();
		}
		
		//Operator
		if (Robot.joyOp.getRawButton(5)){
			Robot.gearSubsystem.openArms();
		}
		
		if (Robot.joyOp.getRawButton(6)){
			Robot.gearSubsystem.closeArms();
		}
		
		if (Robot.joyOp.getRawButton(5)) {
			Robot.pickupSubsystem.PickUp();
		}
		
		if (Robot.joyOp.getRawButton(6)) {
			Robot.pickupSubsystem.SpitOut();
		}
		
//		if(Robot.joyOp.getRawButton(2)){
//			Robot.
//		}
		
		/*if (Robot.joyOp.getRawButton(a)){
				run shooter 
			}
			
		if ((shooter motor >= optimalspeed)? Robot.joyOp.getRawButton(x)){
				shoot 
		}
		
		if(Robot.joyOp.getRawButton(x)){
			emergency shoot 
		}*/
		
		
		/*
		 *  Once we have further functionality for buttons 
		 *  add it here/
		 */
	}
	

	/*
	 * This is also called by the scheduler on a regular basis and will cause the robot
	 * to cease to function for the duration of the match if true is returned.
	 *	Return true if the robot work is finished
	 *	Return false if the robot is not finished
	 */
	protected boolean isFinished() {
		return false;
	}

	/*
	 * This is called when the match ends or if isFinished returns true.
	 * all systems should be disabled at this point.
	 */
	protected void end() {
	}

	/*
	 * This is called if for some reason the normal operation of the robot is cancelled 
	 * by an external action.
	 */
	protected void interrupted() {
	}
}
