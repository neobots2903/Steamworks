/**
 * 
 */
package org.usfirst.frc.team2903.robot.commands.commoners;

import org.usfirst.frc.team2903.robot.subsystems.Drive2903;
import org.usfirst.frc.team2903.robot.subsystems.Gyro2903;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author robotics
 *
 */
public class DriveStraightWithGyro extends Command {
	
	public static void GyroPID(Drive2903 driveSubsystem, Gyro2903 gyroSubsystem) {
		double targetAngle = 0;
		double gyroAngle = gyroSubsystem.GyroPosition() % 360;

		double leftSpeed;
//		double rightSpeed;
		// requires(Robot.driveSubsystem);

		if (gyroAngle != targetAngle ) {
			if (gyroAngle > (targetAngle)) {
				leftSpeed = -0.5;
//				rightSpeed = 0.5;
				driveSubsystem.arcadeDrive(0, leftSpeed);

			}
			else {// (gyroAngle < (targetAngle)) {
				leftSpeed = 0.5;
//				rightSpeed = -0.5;
				driveSubsystem.arcadeDrive(0, leftSpeed);

			}
//			if (targetAngle / 180 == 0) {
//				//turn right
//			}
//			else if(targetAngle / 180 == 1){
//				//turn left
//			}
			
		}

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
