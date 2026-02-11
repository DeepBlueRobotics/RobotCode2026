// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics.commands.Climber;

import static org.carlmontrobotics.Constants.ClimberC.*;

import org.carlmontrobotics.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.Command;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Climb extends Command {
  /** Creates a new Climb. */
  Climber climber;
  int bestCounterEver;
  public Climb(Climber climber, int level) {
    this.climber = climber;
    bestCounterEver = 0;
    addRequirements(climber);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  private void climbL1(){
    if(climber.atGoal()){
       climber.setGoal(L1_CLIMB_HEIGHT);
      }
      else if(!(climber.getGoal() == L1_CLIMB_HEIGHT)){
        climber.setGoal(MAX_HEIGHT_GOAL);
      }
    }

  private void climbL3(){
    if(climber.atGoal() && bestCounterEver > 6){
    return;
    }
    if(climber.getGoal() == MAX_HEIGHT_GOAL && climber.atGoal()){
      climber.setGoal(MIN_HEIGHT_GOAL);
      bestCounterEver++;
    }
    if(climber.getGoal() == MIN_HEIGHT_GOAL && climber.atGoal()){
      climber.setGoal(MAX_HEIGHT_GOAL);
      bestCounterEver++;
    }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
