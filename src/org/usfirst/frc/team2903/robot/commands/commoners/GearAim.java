package org.usfirst.frc.team2903.robot.commands.commoners;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2903.robot.Robot;
import org.usfirst.frc.team2903.robot.subsystems.GearPegPipeline2903;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class GearAim extends Command {

	private double centerX = 0.0;
	public static final int error = 3;
	public static final double maxSpeed = 0.6;
	public static final double minSpeed = 0.45;

	private Object imgLock = new Object();
	private VisionThread visionThread;

	public GearAim() {
		requires(Robot.driveSubsystem);

	}

	@Override
	protected void initialize() {
		AxisCamera camera = CameraServer.getInstance().addAxisCamera("10.29.3.56");
		
		camera.setResolution(Robot.IMG_WIDTH, Robot.IMG_HEIGHT);
	
		visionThread = new VisionThread(camera, new GearPegPipeline2903(), pipeline -> {
				if (!pipeline.filterContoursOutput().isEmpty()) {
					Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
					synchronized (imgLock) {
						centerX = r.x + (r.width / 2);
					}
				}
		});
		visionThread.start();
	}

	public double getCenterX() {
		double localCenterX;
		synchronized (imgLock) {
			localCenterX = this.centerX;
		}
		return localCenterX;
	}

	@Override
	protected void execute() {
		double localCenterX = getCenterX();
		SmartDashboard.putNumber("CenterX ", localCenterX);
	}

	@Override
	protected boolean isFinished() {
		double localCenterX = getCenterX();
		double distanceFromCenter = (localCenterX - (Robot.IMG_WIDTH / 2));
		double turn = distanceFromCenter / (Robot.IMG_WIDTH / 2);
		if (Math.abs(turn) > maxSpeed) {
			if (turn < 0)
				turn = -maxSpeed;
			else 
				turn = maxSpeed;
		} else if (Math.abs(turn) < minSpeed) {
			if (turn < 0)
				turn = -minSpeed;
			else 
				turn = minSpeed;
		}
			
		SmartDashboard.putNumber("turn (for vision) ", turn);
		SmartDashboard.putNumber("Distance from center ", distanceFromCenter);
		if (Math.abs(distanceFromCenter) > error) {
			
			Robot.driveSubsystem.arcadeDrive(0, turn);
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.driveSubsystem.arcadeDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
