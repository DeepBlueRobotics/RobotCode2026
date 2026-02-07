// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics.subsystems;

import org.carlmontrobotics.lib199.MotorConfig;
import org.carlmontrobotics.lib199.MotorControllerFactory;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import static org.carlmontrobotics.Constants.ClimberC.*;
import static org.mockito.Mockito.withSettings;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  /** Creates a new Climber. */

  private SparkMax masterMotor;
  private SparkMax followMotor;
  private SparkBaseConfig masterConfig;
  private SparkBaseConfig followConfig;
  private SparkClosedLoopController pidController;
  private RelativeEncoder relativeEncoder;

  public Climber() {
    configureMotors();
    masterMotor = MotorControllerFactory.createSparkMax(MASTER_MOTOR_ID, MotorConfig.NEO, masterConfig);
    followMotor = MotorControllerFactory.createSparkMax(FOLLOW_MOTOR_ID, MotorConfig.NEO, followConfig);
    pidController = masterMotor.getClosedLoopController();
  } 

  private void configureMotors(){
    masterConfig = MotorControllerFactory.sparkConfig(MotorConfig.NEO); 
    followConfig = MotorControllerFactory.sparkConfig(MotorConfig.NEO);
    masterConfig.encoder.positionConversionFactor(CONVERSION_ROTATION_TO_METERS)
                        .velocityConversionFactor(CONVERSION_ROTATION_TO_METERS/60);
    masterConfig.closedLoop.pid(kP, kI, kD)
                            .feedForward.sva(kS, kV, kA);
    followConfig.apply(masterConfig)
                .follow(MASTER_MOTOR_ID);
  }

  public void moveMotors(double percentage){
    masterMotor.set(percentage);
  }
  
  public void setVoltage(double voltage){
    masterMotor.setVoltage(voltage);
  }
   
  public void setGoal(double goal){
    pidController.setSetpoint(goal, ControlType.kPosition);
  }

  public double getGoal() {
    return pidController.getSetpoint();
  }

  public double getPosition(){
    return relativeEncoder.getPosition();
  }

  public double getVelocity(){
    return relativeEncoder.getVelocity();
  }

  public void stop(){
    pidController.setSetpoint(0, ControlType.kDutyCycle);
  }
  @Override
  public void periodic() {}

}
