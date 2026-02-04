// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics.subsystems;

import org.carlmontrobotics.lib199.MotorConfig;
import org.carlmontrobotics.lib199.MotorControllerFactory;

import org.carlmontrobotics.Constants.OuttakeC;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.SparkClosedLoopController;

public class Outtake extends SubsystemBase {
  SparkFlex outtake;
  private double kP;
  private double kI;
  private double kD;
  private SparkClosedLoopController pidController;
  /** Creates a new Outtake. */
  public Outtake(double kP, double kI, double kD) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    outtake = MotorControllerFactory.createSparkFlex(OuttakeC.OUTTAKE_ID);

    final SparkFlexConfig outtakeConfig = new SparkFlexConfig();
    outtakeConfig.idleMode(IdleMode.kCoast);
    outtakeConfig.closedLoop.pid(kP,kI,kD).feedbackSensor(FeedbackSensor.kAbsoluteEncoder);
  }

  public void spinOuttake(double input) {
    pidController.setReference(input, ControlType.kDutyCycle);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
