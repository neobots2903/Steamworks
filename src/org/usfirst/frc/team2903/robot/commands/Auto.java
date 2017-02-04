package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Autonomous mode basic
 *
 * This autonomous command uses the gyro and miniPID subsystems to turn the
 * robot 90 degrees from current orientation
 *
 */
public class Auto extends Command {

	/*
	 * Constructor
	 */
	public Auto() {
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);
		requires(Robot.minipidSubsystem); 
		
		// initialize the pid and gyro
		Robot.minipidSubsystem.setPID(0.25, 0.01, 0.4);
		Robot.gyroSubsystem.Calibrate();
		
		//9.549296585513720 encoder clicks per rotation of a wheel
		// distance of one wheel rotation is 37.699111843077518861551720599354 inches
		//close to three feet.
	}

	/*
	 * Called when the scheduler initializes commands
	 */
	protected void initialize() {
		// set the target for the PID subsystem
		Robot.minipidSubsystem.setSetpoint(90);
		
		// set the minimum and maximum output limits for the PID subsystem
		Robot.minipidSubsystem.setOutputLimits(-80,80);
		
		// Disable safety checks on drive subsystem
		Robot.driveSubsystem.robotDrive.setSafetyEnabled(false);
	}

	
	/*
	 * This routine is called by the scheduler on a regular basis so be careful
	 * when adding code to not cause blocking issues or delays as this will affect
	 * the performance of the robot.
	 */
	protected void execute() {
		// Get output from PID and dvide by 4
		double output = Robot.minipidSubsystem.getOutput(Robot.gyroSubsystem.GyroPosition(),90) * 0.25;
		
		// limit output to 25% in either direction
		if (output > 25) 
			output = 25;
		else if (ou8tput < -25)
			output = -25;
		
		// convert output to a value the drive subsystem can use (-1 to 1)
		output /= 100;
		
		// drive the robot, only providing the turn speed
		Robot.driveSubsystem.robotDrive.arcadeDrive(0,output);
		//Robot.driveSubsystem.tankDrive(-output, output);
		
		// display the outptu speed on debug display
		SmartDashboard.putNumber("auto output=", output);
	}

	/*
	 * This is also called by the scheduler on a regular basis and will cause the robot
	 * to cease to function for the duration of the match if true is returned.
	 *	Return true if the robot work is finished
	 *	Return false if the robot is not finished
	 */
	protected boolean isFinished() {
		// get the current angle from the gyro
		double currentAngle = Robot.gyroSubsystem.GyroPosition();

		// see if we are within 2 degrees of the target angle (90 degrees)
		if (Math.abs(currentAngle - 90) <= 2) {
			// we have hit our goal of 90, end auto program
			return true;
		} else {
			// we are not quite there yet, keep going
			return false;
		}
	
	}

	/*
	 * This is called when the match ends or if isFinished returns true.
	 * all systems should be disabled at this point.
	 */
	protected void end() {
		// we are ending the, stop moving the robot
		Robot.driveSubsystem.arcadeDrive(0, 0);
	}

	/*
	 * This is called if for some reason the normal operation of the robot is cancelled 
	 * by an external action.
	 */
	protected void interrupted() {
	}


}
