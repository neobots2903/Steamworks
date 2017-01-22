# Stronghold
FRC 2016 Game 2016

## Gyro2903 Methods
* void Calibrate(float angleErrorMargin)
  * this should initialize and reset the gyro.  Called before each autonomous and teleop mode (if needed)  The parameter is the amount of error allowed in validating angle goals.
* float GetCurrentAngle()
  * this should return the current value of the gyro and store it.
* float GetAngleChange()
  * this should get the gyro distance changed since last time GetCurrentAngle called
* void SetTargetAngle(float angle)
  * this should set a target angle.
* bool IsTargetReached()
  * this should return true if reached or false if not.  If current angle is within the margin specified when Calibrate was called, then true will be returned.

## Drive Methods
* void SetDriveMode(DriveMode mode)
  * Sets the driving mode
    * Arcade
      * Joysticks
      * Controller
      * One stick
      * Two sticks
    * Tank
      * Joysticks
      * Controller
* void Turn(float degrees)
  * Turn the robot this number of degrees
* void Drive(float distance)
  * Drive the robot straight for this distance

## Arm Methods
* void RaiseArms()
* void LowerArms()

## Shooter Methods
* void Initialize()
  * Sets speed to 0
  * sets angle to 0
* void StopShooter()
  * sets shooter speed to 0
* void SetDirection(bool direction)
  * direction = true to shoot boulder
  * direction = false to pick up boulder
* void SetSpeed(float speed)
  * 0 stops shooter
* void RaiseShooter(float degrees)
  * Set shooter angle to degrees and start it moving.  If value passed is lower than the current position, the shooter does not move.
* void LowerShooter(float degrees)
  * Set shooter angle to degrees and start it moving.  If value passed is higher than than current position, the shooter does not move.
* void MoveShooter()
  * Called during polling loop to get the shooter to the correct position.  Will only move the shooter if IsShooterAtAngle() returns false.
* bool IsShooterAtAngle()
  * if shooter has reached angle specified in Raise and Lower shooter, returns true.  
* void ResetShooter()
  * sets angle to 0 and starts the shooter moving.  
* bool IsShooterReset()
  * return true if shooter is in start position, false otherwise
* float GetShooterAngle()
  * returns the current shooter angle
