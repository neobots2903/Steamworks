package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.RobotMap;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop extends Command {

	public Teleop() {
		requires(Robot.driveSubsystem);

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
		 * Y-axis -- forward and reverse
		 * X-axis -- turn left and rught
		 */
		
		double forward = Robot.joy1.getY();
		double turn = Robot.joy1.getX();
		SmartDashboard.putNumber("Forward", forward);
		SmartDashboard.putNumber("Turn", turn);
		Robot.driveSubsystem.arcadeDrive(forward, -turn);
		//Robot.driveSubsystem.tankDrive(forward, forward);
		
		SmartDashboard.putNumber("Voltage", Robot.driveSubsystem.GetVoltage());

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
