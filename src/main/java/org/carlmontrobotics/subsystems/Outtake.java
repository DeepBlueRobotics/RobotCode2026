// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics.subsystems;

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
  public SparkFlex outtake;
  public SparkFlex feeder;
  private SparkClosedLoopController pidController;
  /** Creates a new Outtake. */
  public Outtake() {
    outtake = MotorControllerFactory.createSparkFlex(OuttakeC.OUTTAKE_ID);
    feeder = MotorControllerFactory.createSparkFlex(OuttakeC.FEEDER_ID);

    final SparkFlexConfig outtakeConfig = new SparkFlexConfig();
    outtakeConfig.idleMode(IdleMode.kCoast);
    outtakeConfig.closedLoop.pid(OuttakeC.OUTTAKE_KP, OuttakeC.OUTTAKE_KI, OuttakeC.OUTTAKE_KD);
  }

  public void spinOuttake(double input) {
    pidController.setSetpoint(input, ControlType.kDutyCycle);
  }

  public void spinFeeder(double input) {
    feeder.set(input);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
