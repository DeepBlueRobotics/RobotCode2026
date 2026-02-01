// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics.subsystems;

import org.carlmontrobotics.lib199.MotorConfig;
import org.carlmontrobotics.lib199.MotorControllerFactory;

import org.carlmontrobotics.Constants.IntakeC;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;

public class Intake extends SubsystemBase {
  SparkMax intake;
  /** Creates a new Intake. */
  public Intake() {
    SparkMax intake = MotorControllerFactory.createSparkMax(IntakeC.INTAKE_ID, MotorConfig.NEO);
  }

  //FIXME add configs

  public void spinIntake(double intakeSpeed) {
    intake.set(intakeSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
