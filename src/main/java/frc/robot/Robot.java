/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.SpeedController;

import com.analog.adis16470.frc.ADIS16470_IMU;
import edu.wpi.first.wpilibj.AnalogGyro;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends IterativeRobot {
  private DifferentialDrive m_myRobot;

  private static final int m_joystick_channel = 0;

  private SpeedController m_left_shoot;
  private int left_shoot_channel = 0;
  private SpeedController m_right_shoot;
  private int right_shoot_channel = 0;
  private double shoot_speed;

  private SpeedControllerGroup m_left_drive;
  private SpeedController m_front_left;
  private int front_left_channel = 0;
  private SpeedController m_back_left;
  private int back_left_channel = 0;
  

  private SpeedControllerGroup m_right_drive;
  private SpeedController m_front_right;
  private int front_right_channel = 0;
  private SpeedController m_back_right;
  private int back_right_channel = 0;

  private Joystick m_joystick;
  private int speed_channel = 1;
  private int rotation_channel = 4;

  private AnalogGyro m_gyro = new AnalogGyro(0);
  private static final double kAngleSetpoint = 0.0;
  private static final double kP = 0.005; 
  private static final double kVoltsPerDegreePerSecond = 0.0128;

  
  @Override
  public void robotInit() {

    //Setup the parameters for the drive motors.
    m_front_left = new PWMVictorSPX(front_left_channel);
    m_back_left = new PWMVictorSPX(back_left_channel);
    m_front_right = new PWMVictorSPX(front_right_channel);
    m_back_right = new PWMVictorSPX(back_right_channel);
    m_left_drive = new SpeedControllerGroup(m_front_left, m_back_left);
    m_right_drive = new SpeedControllerGroup(m_front_right, m_back_right);
    m_myRobot = new DifferentialDrive(m_left_drive, m_right_drive);    

    //Setup the parameters for the shooter motors.
    m_left_shoot = new PWMVictorSPX(left_shoot_channel);
    m_right_shoot = new PWMVictorSPX(right_shoot_channel);
    m_left_shoot.setInverted(true);
    m_right_shoot.setInverted(false);
    
    //Setup the josticks for controlling the robot
    m_joystick = new Joystick(m_joystick_channel);   //sets which joystick channel the program is to use. This allows for 5 joysticks to potentially be used on the computer.
    m_joystick.setXChannel(speed_channel);    //sets which axis on the controller is designated as the 'X' channel, specifically used for speed.
    m_joystick.setYChannel(rotation_channel);    //sets which axis on the controller is designated as the 'Y' channel, specifically used for rotation.

  }

  @Override   //The override changes the implementation of teleopPeriodic to the code we have written below. If we don't override this, the robot does what the original function does.
  public void teleopPeriodic() {
    
    m_myRobot.arcadeDrive(m_joystick.getX(), m_joystick.getY());    //drive the wheels using the joystick's X and Y channel values.

    //use the A button to shoot the ball.
    if(m_joystick.getRawButton(1)) Shoot(shoot_speed);//if the button is pressed, set the shoot speed to the constant above.
    else Shoot(0);    //if the button isn't pressed, set the shoot speed to zero.

  }

  void Shoot(double speed){
    m_left_shoot.set(speed);
    m_right_shoot.set(speed);
  }
}
