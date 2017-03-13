package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveForTime extends Command {

	static final double TURN_SPEED = 0.5;
	static final double MAX_SPEED = 0.8;
	double TimeForDistance;
	double timeInSeconds;
	Timer timer = new Timer();
	private double gyroAngle;
	private double highAngleLimit = 2;
	private double lowAngleLimit = -2;

	public DriveForTime(double timeInSeconds) {
		requires(Robot.driveSubsystem);

		this.timeInSeconds = timeInSeconds;
	}

	protected void initialize() {
		// TODO Auto-generated method stub
		timer.reset();
		timer.start();
		TimeForDistance = timer.get();

	}

	@Override
	protected void execute() {

		double turnSpeed = 0;

		gyroAngle = Robot.gyroSubsystem.GyroPosition();

		// SmartDashboard.putNumber("gyroAngle ", gyroAngle);
		if (lowAngleLimit >= gyroAngle && gyroAngle >= highAngleLimit) {
			if (gyroAngle > 0) {
				turnSpeed = -TURN_SPEED;

			} else {
				turnSpeed = TURN_SPEED;

			}
		} else {
			turnSpeed = 0;
		}

		Robot.driveSubsystem.tankDrive(MAX_SPEED + (turnSpeed / 2), -MAX_SPEED + (turnSpeed / 2));
		TimeForDistance = timer.get();
	}

	@Override

	protected boolean isFinished() {
		if (TimeForDistance >= timeInSeconds) {
			return true;
		}
		return false;
	}

	@Override
	protected void end() {
		Robot.driveSubsystem.tankDrive(0, 0);
		timer.stop();
	}

	@Override
	protected void interrupted() {
		end();

	}
}