package org.usfirst.frc.team2903.robot.subsystems;

import org.usfirst.frc.team2903.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Gear2903 extends Subsystem {

	// gear delivery system
	public void openArms() {
		Robot.pnuematicsSubsystem.openarms();
	}

	public void closeArms() {
		Robot.pnuematicsSubsystem.closearms();
	}

	@Override
	protected void initDefaultCommand() {
	}

}
