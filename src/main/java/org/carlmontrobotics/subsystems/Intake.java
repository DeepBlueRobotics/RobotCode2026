// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics.subsystems;

import org.carlmontrobotics.lib199.MotorControllerFactory;

import org.carlmontrobotics.Constants.IntakeC;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

public class Intake extends SubsystemBase {
  SparkFlex intake;
  /** Creates a new Intake. */
  public Intake() {
    intake = MotorControllerFactory.createSparkFlex(IntakeC.INTAKE_ID);

    final SparkFlexConfig intakeConfig = new SparkFlexConfig();
    intakeConfig.idleMode(IdleMode.kCoast);
    intake.configure(intakeConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void spinIntake(double intakeSpeed) {
    intake.set(intakeSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Intake Speed", intake.get());
  }
}
