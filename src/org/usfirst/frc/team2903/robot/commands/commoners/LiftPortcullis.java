package org.usfirst.frc.team2903.robot.commands.commoners;


import org.usfirst.frc.team2903.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class LiftPortcullis  extends Command {

	static double ArmTargetAngle;
	private double ArmMoveSpeed;
	
	// constructor which uses default values for speed and angle
	public LiftPortcullis()
	{
		super("LiftPortcullis()");
		
		requires(Robot.armSubsystem);
		
		// TODO: Set this to an appropriate value
		ArmTargetAngle = 45; 
		ArmMoveSpeed = 0.1;
	}

	// constructor which sets arm speed
	public LiftPortcullis(double armSpeed)
	{
		super("LiftPortcullis(double)");
		
		requires(Robot.armSubsystem);
		
		// TODO: Set this to an appropriate value
		ArmTargetAngle = 45; 
		ArmMoveSpeed = armSpeed;
	}

	// constructor which sets arm speed and target angle
	public LiftPortcullis(double armSpeed, double targetAngle)
	{
		super("LiftPortcullis(double,double)");
		
		requires(Robot.armSubsystem);
		
		// TODO: Set this to an appropriate value
		ArmTargetAngle = targetAngle; 
		ArmMoveSpeed = armSpeed;
	}
	
	@Override
	protected void initialize() {

		if (!Robot.armSubsystem.isCalibrated()) {
			// get the current value of the potentiometer
			// and use that as the reset value
			 Robot.armSubsystem.calibrate();
		}
		 Robot.armSubsystem.setTargetAngle(ArmTargetAngle);
		 Robot.armSubsystem.setMotorSpeed(ArmMoveSpeed);
		 Robot.armSubsystem.moveArm();
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		
		// TODO Auto-generated method stub
		return Robot.armSubsystem.isTargetReached();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.armSubsystem.stopArm();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
		
	}

}
