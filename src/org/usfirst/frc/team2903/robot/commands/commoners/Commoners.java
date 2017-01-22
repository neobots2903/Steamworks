package org.usfirst.frc.team2903.robot.commands.commoners;

public class Commoners {
	
	/*
	 * LiftAndCrossPortcullis will attempt to get the robot through the
	 * portcullis defense.  If autoDrive is specified as true, the 
	 * robot will use the drive subsystem to move the robot forward
	 * to the portcullis.  If the vision subsystem is functioning,
	 * it may be possible to use that subsystem during this operation as well
	 * 
	 * NOTE: This routine will not return until the job is complete or stop pressed.
	 */
	public static void LiftAndCrossPortcullis(){
		 /*
		  * Pseudo-code
		  * while (not emergency stop or (arm not in position and drive distance not reached)
		  * 	if arm lift not complete
		  * 		lift arms
		  * 	end if
		  * 	if arm lift is complete and drive distance not reached
		  * 		drive forward for the specified distance using the drive subsystem
		  * 		encoder
		  * 	end if
		  * 	if arm lift complete
		  * 		set arm lift to complete
		  * 	end if
		  * 	if	drive distance is reached
		  * 		set drive distance to reached
		  * 	end if
		  * end while
		  */
	}

	public static void CrossRampart(){
		
	}
	
	public static void OpenSallyPort(){
		
	}
	
	public static void CrossLowBar() {
		
	}

	public static void CrossMoat() {
		
	}
	
	public static void ClimbRockWall() {
		
	}
	
	public static void CrossRoughTerrain() {
		
	}
	
	public static void LowerDrawbridge() {
		
	}

	public static void CrossChevalDeFrise()
	{
		
	}
	
	public static void DriveAndTurn(float driveDistance, float turnDistance){
		
	}
	
	public static void ShootHighGoal() {
		
	}
	
	public static void ClimbCastle()
	{
		
	}
}
