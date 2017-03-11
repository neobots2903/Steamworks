package org.usfirst.frc.team2903.robot.subsystems;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Vision2903 extends Subsystem {

    private AxisCamera camera;

	// Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Vision2903(String string) {
		// TODO Auto-generated constructor stub
//		camera = CameraServer.getInstance().addAxisCamera(string);

	}

	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	public AxisCamera getCamera() {
		return camera;
	}

	public void setResolution(int imgWidth, int imgHeight) {
		camera.setResolution(imgWidth, imgHeight);
	}
}

