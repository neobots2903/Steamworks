package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveWithLIDAR extends Command {
	Timer timer = new Timer();
	double timeInSeconds = timer.get();
	double gyroAngle = Robot.gyroSubsystem.GyroPosition();
	double distanceFromWall = Robot.lidarSubsystem.getDistance();
	double closestDistance = 20;

	public DriveWithLIDAR() {
		requires(Robot.driveSubsystem);
	}

	protected void initialize() {
		Robot.lidarSubsystem.start();
		timer.reset();
		timer.start();
	}

	@Override
	protected void execute() {
		if (distanceFromWall > closestDistance) {
			Robot.driveSubsystem.tankDrive(0.6, -0.6);
		} else {
			double currentTime = timeInSeconds;
			while (currentTime + 2 > timeInSeconds) {
				Robot.driveSubsystem.tankDrive(-0.4, 0.4);
			}
			double startingAngle = gyroAngle;
			while (startingAngle + 90 > gyroAngle) {
				Robot.driveSubsystem.arcadeDrive(0, 0.4);
			}
		}
	}

	@Override

	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.lidarSubsystem.stop();
	}

	@Override
	protected void interrupted() {
		end();
		// TODO Auto-generated method stub

	}
}