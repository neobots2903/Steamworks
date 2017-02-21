
package org.usfirst.frc.team2903.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2903.robot.commands.Teleop;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveToPositionTest;
import org.usfirst.frc.team2903.robot.commands.commoners.DriveWithLIDAR;
import org.usfirst.frc.team2903.robot.commands.commoners.GearAim;
import org.usfirst.frc.team2903.robot.commands.commoners.TurnWithGyro;
import org.usfirst.frc.team2903.robot.commands.groups.DriveForDistanceTest;
import org.usfirst.frc.team2903.robot.commands.groups.DriveInAOneFootSquare;
import org.usfirst.frc.team2903.robot.commands.groups.DriveInAOneSecondSquare;

import org.usfirst.frc.team2903.robot.subsystems.CameraVision2903;
import org.usfirst.frc.team2903.robot.subsystems.Drive2903;
import org.usfirst.frc.team2903.robot.subsystems.Gear2903;
import org.usfirst.frc.team2903.robot.subsystems.Gyro2903;
import org.usfirst.frc.team2903.robot.subsystems.LIDAR2903;
import org.usfirst.frc.team2903.robot.subsystems.MiniPID2903;
import org.usfirst.frc.team2903.robot.subsystems.Shooter2903;

import org.usfirst.frc.team2903.robot.subsystems.PickUp2903;
import org.usfirst.frc.team2903.robot.subsystems.Pnuematics2903;
import org.usfirst.frc.team2903.robot.subsystems.Climber2903;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

//	CANTalon leftFrontMotor;
//	CANTalon leftRearMotor;
//	CANTalon rightFrontMotor;
//	CANTalon rightRearMotor;
	
	public static Drive2903 driveSubsystem;
	public static Shooter2903 shooterSubsystem;
	public static Gyro2903 gyroSubsystem;
	public static MiniPID2903 minipidSubsystem;
	public static Pnuematics2903 pnuematicsSubsystem;
	public static Gear2903 gearSubsystem;
	public static LIDAR2903 lidarSubsystem;
	public static PickUp2903 pickupSubsystem;
	public static Climber2903 climberSubsystem;
	
	Command autonomousCommand;
	SendableChooser<Command> autoChooser;
	Command teleopCommand;
	public static CameraVision2903 cameraSubsystem;

	public static Joystick joyOp = new Joystick(0);
	Button triggerKick = new JoystickButton(joyOp, 1);

	public static Joystick joy1 = new Joystick(1);

	public static Port lidarPort = I2C.Port.kOnboard;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		pnuematicsSubsystem = new Pnuematics2903 ();

		driveSubsystem = new Drive2903();
		Gyro2903.GYRO_TYPE gyroType = Gyro2903.GYRO_TYPE.SPI;
		gyroSubsystem = new Gyro2903(gyroType);
		minipidSubsystem = new MiniPID2903(3.5, 0, 0);
		gearSubsystem = new Gear2903();
		shooterSubsystem = new Shooter2903();
		pickupSubsystem = new PickUp2903();
		climberSubsystem = new Climber2903();
		lidarSubsystem = new LIDAR2903(lidarPort);
		
		SmartDashboard.putNumber("kP", minipidSubsystem.getP());
		SmartDashboard.putNumber("kI", minipidSubsystem.getI());
		SmartDashboard.putNumber("kD", minipidSubsystem.getD());
		
		SmartDashboard.putNumber("LIDAR Distance From Object", 0);

//		cameraSubsystem = new CameraVision2903();

//		shooterSubsystem = new Shooter2903();
		
		autoChooser = new SendableChooser<Command>();
		try {
			autoChooser.addDefault("DriveForDistanceTest", new DriveForDistanceTest());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		autoChooser.addObject("DriveWithLIDAR", new DriveWithLIDAR());
		autoChooser.addObject("GearAim", new GearAim());
		autoChooser.addObject("DriveInAOneFootSquare", new DriveInAOneFootSquare());
		autoChooser.addObject("DriveInAOneSecondSquare", new DriveInAOneSecondSquare());
		autoChooser.addObject("DriveWithLIDAR", new DriveWithLIDAR());
		autoChooser.addObject("TurnWithGyro", new TurnWithGyro(90));
		autoChooser.addObject("DriveToPosition", new DriveToPositionTest(12));
		SmartDashboard.putData("AutoChooser", autoChooser);

		teleopCommand = new Teleop();

		//CameraServer server = CameraServer.getInstance();
		//server.setQuality(50);
		//server.startAutomaticCapture();

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
			autonomousCommand = (Command) autoChooser.getSelected();
			autonomousCommand.start();
			
//			rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
//			leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
//			rightRearMotor.changeControlMode(TalonControlMode.Follower);
//			leftRearMotor.changeControlMode(TalonControlMode.Follower);
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
		
//		rightFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
//		leftFrontMotor.changeControlMode(TalonControlMode.PercentVbus);
//		rightRearMotor.changeControlMode(TalonControlMode.PercentVbus);
//		leftRearMotor.changeControlMode(TalonControlMode.PercentVbus);
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
