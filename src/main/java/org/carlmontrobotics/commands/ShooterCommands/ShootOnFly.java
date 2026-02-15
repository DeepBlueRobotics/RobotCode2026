// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics.commands.ShooterCommands;

import java.nio.channels.Pipe;

import org.carlmontrobotics.subsystems.Drivetrain;
import org.carlmontrobotics.subsystems.Shooter;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ShootOnFly extends Command {
  private Drivetrain dt;
  private Shooter shooter;
  private boolean isRed;
  private Pose2d current2dPose;
  private Pose3d current3dPose;
  private Pose3d goalPose;
  private double[] velocityVectors;
  private double shooterVelocity;
  private Translation3d path;

  /** Creates a new ShootOnFly. */
  public ShootOnFly(Drivetrain dt, Shooter shooter) {
    addRequirements(this.dt = dt, this.shooter = shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isRed = DriverStation.getAlliance()
      .map(a -> a == DriverStation.Alliance.Red)
      .orElse(false);   // default when unknown
    goalPose = isRed ? new Pose3d(11.916, 4.038, 1.8237877672, new Rotation3d()) : new Pose3d(4.618, 4.038, 1.8237877672, new Rotation3d());
    current2dPose = dt.getDrivetrainPosition();
    current3dPose = new Pose3d(current2dPose);
    velocityVectors = dt.getDrivetrainVelocity();
    shooterVelocity = shooter.getVelocity();
    velocityVectors[0] += shooterVelocity*Math.cos(current2dPose.getRotation().getRadians());
    velocityVectors[1] += shooterVelocity*Math.sin(current2dPose.getRotation().getRadians());
    path = goalPose.getTranslation().minus(current3dPose.getTranslation());
    
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
