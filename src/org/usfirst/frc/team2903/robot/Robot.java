
package org.usfirst.frc.team2903.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2903.robot.commands.groups.CrossMoat;
import org.usfirst.frc.team2903.robot.commands.Auto;
import org.usfirst.frc.team2903.robot.commands.Teleop;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveForward;
import org.usfirst.frc.team2903.robot.commands.commoners.Shoot;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import org.usfirst.frc.team2903.robot.commands.groups.DriveInAOneSecondSquare;
import org.usfirst.frc.team2903.robot.subsystems.CameraVision2903;
import org.usfirst.frc.team2903.robot.subsystems.Arm2903;
import org.usfirst.frc.team2903.robot.subsystems.Drive2903;

import org.usfirst.frc.team2903.robot.subsystems.Gyro2903;
import org.usfirst.frc.team2903.robot.subsystems.MiniPID2903;
import org.usfirst.frc.team2903.robot.subsystems.Shooter2903;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drive2903 driveSubsystem;
	public static Shooter2903 shooterSubsystem;
	public static Gyro2903 gyroSubsystem;
	public static MiniPID2903 minipidSubsystem;
	Command autonomousCommand;
	SendableChooser autoChooser;
	Command teleopCommand;
	public static CameraVision2903 cameraSubsystem;

	public static Joystick joyOp = new Joystick(0);
	Button triggerKick = new JoystickButton(joyOp, 1);

	public static Joystick joy1 = new Joystick(1);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// oi = new OI();

		driveSubsystem = new Drive2903();
		Gyro2903.GYRO_TYPE gyroType = Gyro2903.GYRO_TYPE.SPI;
		gyroSubsystem = new Gyro2903(gyroType);
		minipidSubsystem = new MiniPID2903(3.5, 0, 0);
		SmartDashboard.putNumber("kP", minipidSubsystem.getP());
		SmartDashboard.putNumber("kI", minipidSubsystem.getI());
		SmartDashboard.putNumber("kD", minipidSubsystem.getD());

//		cameraSubsystem = new CameraVision2903();

		shooterSubsystem = new Shooter2903();
		
		//autonomousCommand = new DriveForward (12);
		autoChooser = new SendableChooser();
		autoChooser.addObject("CrossMoat", new CrossMoat(false));
		autoChooser.addDefault("DriveInAOneSecondSquare", new DriveInAOneSecondSquare());
		autoChooser.addObject("DriveForward", new DriveForward(12));
		autoChooser.addObject("TurnWithGyro", new TurnWithGyro(90));
		SmartDashboard.putData("AutoChooser", autoChooser);

		teleopCommand = new Teleop();
//		CameraServer server = CameraServer.getInstance();
		//server.setQuality(50);
//		server.startAutomaticCapture();


	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
			autonomousCommand = (Command) autoChooser.getSelected();
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		teleopCommand.start();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
