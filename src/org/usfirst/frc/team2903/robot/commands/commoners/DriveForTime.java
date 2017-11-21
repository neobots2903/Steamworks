package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public  class DriveForTime extends Command{
	double TimeForDistance;
	double timeInSeconds;
	Timer timer = new Timer();
	private int targetAngle;
	private double gyroAngle;
	private double highAngleLimit = 2;
	private double lowAngleLimit = -2;
	boolean useLimit;
	
	public DriveForTime(double timeInSeconds, boolean useLimitSwitch){
		super("DriveForTime");
		requires(Robot.driveSubsystem);

		this.timeInSeconds = timeInSeconds;
		
		if (useLimitSwitch) {
			useLimit = true;
		} else {
			useLimit = false;
		}
	}
	
	protected void initialize(){
		//TODO Auto-generated method stub
		timer.reset();
		timer.start();
		TimeForDistance =timer.get();
		
		targetAngle = 0;
		

	}

	@Override
	protected void execute() {

		if (useLimit && Robot.pnuematicsSubsystem.limitSwitchesPressed()) {
			end();
		} else {
		
		double turnSpeed = 0;
		
		gyroAngle = Robot.gyroSubsystem.GyroPosition();
		
		//SmartDashboard.putNumber("gyroAngle ", gyroAngle);
//		if (lowAngleLimit >= gyroAngle && gyroAngle >= highAngleLimit) {
//			if (gyroAngle > 0) {
//				turnSpeed = -0.5;
//
//			}
//			else {// (gyroAngle < (targetAngle)) {
//				turnSpeed = 0.5;
//
//			}
//		} else {
//			turnSpeed = 0;
//		}
		// TODO Auto-generated method stub
		//DriveStraightWithGyro.GyroPID(Robot.driveSubsystem, Robot.gyroSubsystem);
		//SmartDashboard.putNumber("turnSpeed", turnSpeed);
		Robot.driveSubsystem.tankDrive(-0.6+(turnSpeed/2), -0.6+(turnSpeed/2));
		//Robot.driveSubsystem.arcadeDrive(0, -0.8);
		TimeForDistance = timer.get();
		}
	}
	
	@Override
	
	protected boolean isFinished() {
		// TODO Auto-generated method stub

		if(TimeForDistance >= timeInSeconds){
			return true;
		}
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		timer.stop();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
}