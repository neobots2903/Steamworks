package org.usfirst.frc.team2903.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class CameraVision2903 extends Subsystem {

	double[] defaultValue = new double[0];

	NetworkTable table;

	double[] areas;
	double[] centerYs;
	double[] centerXs;
	double[] heights;
	double[] widths;
	double[] boundingTop;

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public double GetBiggestArea() {
		double biggest = 0;
		for (int i = 0; i < areas.length; i++) {
			if (areas[i] > biggest) {
				biggest = areas[i];
			}
		}
		return biggest;
	}

	public void GetImageTable() {
		table = NetworkTable.getTable("GRIP/imageResults");

		areas = table.getNumberArray("area", defaultValue);
		centerYs = table.getNumberArray("centerY", defaultValue);
		centerXs = table.getNumberArray("centerX", defaultValue);
		heights = table.getNumberArray("height", defaultValue);
		widths = table.getNumberArray("width", defaultValue);

	}

	public double GetOffsetX(int index) {

		double[] offsetX;

		offsetX = centerXs.clone();
		int y = offsetX.length;
		for (int i = 0; i < y; i++) {
			offsetX[i] -= 320;
		}
		return offsetX[index];
	}

	public double GetHorizontalAngle(int index) {

		double HorizontalAngle = java.lang.Math.atan(GetOffsetX(index / 460));

		return HorizontalAngle;
	}

	public double GetOffsetBounding(int index) {

		double[] offsetBounding;
		offsetBounding = boundingTop.clone();

		int z = offsetBounding.length;
		for (int i = 0; i < z; i++) {
			offsetBounding[i] -= 240;
		}
		return offsetBounding[index];
	}

	public double GetVerticalAngle(int index) {

		double VerticalAngle = java.lang.Math.atan(GetOffsetBounding(index / 460));

		return VerticalAngle;
	}

	public void displayCameraValues() {
		GetImageTable();

		boundingTop = heights.clone();

		int x = boundingTop.length;
		for (int i = 0; i < x; i++) {
			boundingTop[i] /= 2;
			boundingTop[i] += centerYs[i];
		}

		System.out.print("areas: ");
		for (double area : areas) {
			System.out.print(area + " ");
		}

		System.out.print("centerYs: ");
		for (double centerY : centerYs) {
			System.out.print(centerY + " ");
		}

		System.out.print("centerXs: ");
		for (double centerX : centerXs) {
			System.out.print(centerX + " ");
		}

		System.out.print("heights: ");
		for (double height : heights) {
			System.out.print(height + " ");
		}

		System.out.print("widths: ");
		for (double width : widths) {
			System.out.print(width + " ");
		}
		System.out.println();
		Timer.delay(1);

	}

	public int GetBiggestAreaIndex() {
		double biggest = 0;
		int index = 0;

		GetImageTable();

		for (int i = 0; i < areas.length; i++) {
			if (areas[i] > biggest) {
				biggest = areas[i];
				index = i;
			}
		}
		return index;

	}

}
