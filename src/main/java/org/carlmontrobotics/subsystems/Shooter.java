// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.carlmontrobotics.Constants.Shooterc.*;

import org.carlmontrobotics.lib199.MotorConfig;
import org.carlmontrobotics.lib199.MotorControllerFactory;
import org.carlmontrobotics.lib199.MotorControllerType;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  private SparkFlex masterShooter;
  private SparkFlex followShooter;
  private RelativeEncoder masterEncoder;
  private RelativeEncoder followEncoder;
  private SparkBaseConfig masterConfig;
  private SparkBaseConfig followConfig;
  private SparkClosedLoopController pidController;
  private double shooterGoal;
  private boolean manualMode = false;

  public Shooter() {
    configureMotors();
    masterShooter = MotorControllerFactory.createSparkFlex(masterID, MotorConfig.NEO_VORTEX, masterConfig);
    followShooter = MotorControllerFactory.createSparkFlex(followerID, MotorConfig.NEO_VORTEX, followConfig);
  }

  private void configureMotors() {
    masterConfig = MotorControllerFactory.createConfig(MotorControllerType.SPARK_FLEX);
    masterConfig.idleMode(IdleMode.kCoast)
                .encoder
                  .positionConversionFactor(gearReduction) //rotations
                  .velocityConversionFactor(gearReduction); //rotations per minute
    
    followConfig.apply(masterConfig);
    followConfig.follow(masterID, true);

    masterConfig.inverted(masterInverted)
                .closedLoop.pid(kP, kI, kD);
  }

  public void setVoltage(double voltage) {
    pidController.setSetpoint(voltage, ControlType.kVoltage);
  }

  public void setVoltagePercentage(double percentage) {
    pidController.setSetpoint(percentage, ControlType.kDutyCycle);
  }

  public void setRPMGoal(double goal) {
    shooterGoal = goal;
    pidController.setSetpoint(goal, ControlType.kVelocity);
  }

  public void stop() {
    pidController.setSetpoint(0, ControlType.kDutyCycle);
  }

  public void turnOnManualMode() {
    if (!manualMode) {
      manualMode = true;
      pidController.setSetpoint(0, ControlType.kDutyCycle);
    }
  }

  public double getRPMGoal() {
    return shooterGoal;
  }

  /**Gets RPM */
  public double getVelocity() {
    return masterEncoder.getVelocity();
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
