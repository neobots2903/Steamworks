package org.usfirst.frc.team2903.robot.commands;

import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveStraightWithGyro;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto extends Command {

	public Auto() {
		requires(Robot.driveSubsystem);
		requires(Robot.gyroSubsystem);
		requires(Robot.minipidSubsystem); 
		Robot.minipidSubsystem.setPID(0.25, 0.01, 0.4);
		Robot.gyroSubsystem.Calibrate();
		
		
		//9.549296585513720 encoder clicks per rotation of a wheel
		// distance of one wheel rotation is 37.699111843077518861551720599354 inches
		//close to three feet.
	}

	// private void requires(Gyro2903 gyroSubsystem) {
	// // TODO Auto-generated method stub
	//
	// }

	protected void end() {
		Robot.driveSubsystem.arcadeDrive(0, 0);

	}

	protected void execute() {
		double output = Robot.minipidSubsystem.getOutput(Robot.gyroSubsystem.GyroPosition(),90) * 0.25;
		if (output > 25) output = 25;
		output /= 100;
		
		Robot.driveSubsystem.robotDrive.arcadeDrive(0,output);
		//Robot.driveSubsystem.tankDrive(-output, output);
		SmartDashboard.putNumber("auto output=", output);
		//DriveStraightWithGyro.GyroPID(Robot.driveSubsystem, Robot.gyroSubsystem);
	}

	protected void initialize() {
		Robot.minipidSubsystem.setSetpoint(90);
		Robot.minipidSubsystem.setOutputLimits(-80,80);
		Robot.driveSubsystem.robotDrive.setSafetyEnabled(false);
	}

	protected void interrupted() {

	}

	protected boolean isFinished() {
		double output = Robot.gyroSubsystem.GyroPosition();

		if (Math.abs(output - 90) <= 2) {
			return true;
		} else {
			return false;
		}
	
	}

}
