package org.usfirst.frc.team2903.robot.commands.commoners;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import org.usfirst.frc.team2903.robot.subsystems.LIDAR2903;

public class LIDARTest extends Command {

	private LIDAR2903 lidar;
	Port port = I2C.Port.kOnboard;
	
	public LIDARTest() {
		super("LIDARTest");
		lidar = new LIDAR2903(port); //Make constant in robotmap
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		lidar.start();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Distance ", lidar.getDistance());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		lidar.stop();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		lidar.stop();
	}

}
