package org.usfirst.frc.team2903.robot.subsystems;

	import java.util.TimerTask;
	import edu.wpi.first.wpilibj.I2C;
	import edu.wpi.first.wpilibj.Timer;
	import edu.wpi.first.wpilibj.I2C.Port;
	import edu.wpi.first.wpilibj.PIDSource;
	import edu.wpi.first.wpilibj.PIDSourceType;

	public class LIDAR2903 implements PIDSource{
		private I2C i2c;
		private byte[] distance;
		private java.util.Timer updater;
		
		private final int LIDAR_ADDR = 0x62;
		private final int LIDAR_CONFIG_REGISTER = 0x00;
		private final int SIG_COUNT_VAL_REGISTER = 0x02;
		private final int ACQ_CONFIG_REGISTER = 0x04;
		private final int THRESHOLD_BYPASS_REGISTER = 0x1c;
		private final int LIDAR_DISTANCE_REGISTER = 0x8f;
		
		private final int RESET_FPGA_CMD = 0x00;
		private final int DISTANCE_NOBIAS_CMD = 0x03;
		private final int DISTANCE_BIAS_CMD = 0x04;
		
		public LIDAR2903(Port port) {
			i2c = new I2C(port, LIDAR_ADDR);
			
			distance = new byte[2];
			
			updater = new java.util.Timer();
			
			// reset
			i2c.write(LIDAR_CONFIG_REGISTER, RESET_FPGA_CMD);
			
			// configure balanced performance
			i2c.write(SIG_COUNT_VAL_REGISTER, 0x80);
			i2c.write(ACQ_CONFIG_REGISTER, 0x08);
			i2c.write(THRESHOLD_BYPASS_REGISTER, 0x00);
			
		}
		
		// Distance in cm
		public int getDistance() {
			int distanceVal= 0;
			for (int i = 0; i < 2; i++) {
				int shift = (2 - 1 -i ) * 8;
				distanceVal += (distance[i] & 0x000000FF) << shift;
			}
//			return (int)Integer.toUnsignedLong(distance[0] << 8) + Byte.toUnsignedInt(distance[1]);
			return distanceVal;
		}

		public double pidGet() {
			return getDistance();
		}
		
		// Start 10Hz polling
		public void start() {
			updater.scheduleAtFixedRate(new LIDARUpdater(), 0, 100);
		}
		
		// Start polling for period in milliseconds
		public void start(int period) {
			updater.scheduleAtFixedRate(new LIDARUpdater(), 0, period);
		}
		
		public void stop() {
			updater.cancel();
			updater = new java.util.Timer();
		}
		
		// Update distance variable
		public void update() {
			i2c.write(LIDAR_CONFIG_REGISTER, DISTANCE_BIAS_CMD); // Initiate measurement
			Timer.delay(0.04); // Delay for measurement to be taken
			i2c.read(LIDAR_DISTANCE_REGISTER, 2, distance); // Read in measurement
			Timer.delay(0.005); // Delay to prevent over polling
		}
		
		// Timer task to keep distance updated
		private class LIDARUpdater extends TimerTask {
			public void run() {
				while(true) {
					update();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			// TODO Auto-generated method stub
			return null;
		}
	}
