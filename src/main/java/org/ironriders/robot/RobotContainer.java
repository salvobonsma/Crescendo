// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.ironriders.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import org.ironriders.commands.ClimberCommands;
import org.ironriders.commands.DriveCommands;
import org.ironriders.commands.LauncherCommands;
import org.ironriders.commands.RobotCommands;
import org.ironriders.constants.Identifiers;
import org.ironriders.constants.Manipulator;
import org.ironriders.constants.Pivot;
import org.ironriders.lib.Utils;
import org.ironriders.subsystems.*;

import static org.ironriders.constants.Auto.DEFAULT_AUTO;
import static org.ironriders.constants.Drive.CLIMBING_MODE_SPEED;
import static org.ironriders.constants.Teleop.Controllers.Joystick;

public class RobotContainer {
    private final DriveSubsystem drive = new DriveSubsystem();
    private final DriveCommands driveCommands = drive.getCommands();
    private final LauncherSubsystem launcher = new LauncherSubsystem();
    private final LauncherCommands launcherCommands = launcher.getCommands();
    private final PivotSubsystem pivot = new PivotSubsystem();
    private final ManipulatorSubsystem manipulator = new ManipulatorSubsystem();
    private final ClimberSubsystem climber = new ClimberSubsystem(drive);
    private final ClimberCommands climberCommands = climber.getCommands();
    @SuppressWarnings("unused")
    private final LightingSubsystem lighting = new LightingSubsystem();
    private final RobotCommands commands = new RobotCommands(drive, launcher, pivot, manipulator, climber);

    private final CommandXboxController primaryController =
            new CommandXboxController(Identifiers.Controllers.PRIMARY_CONTROLLER);
    private final CommandGenericHID secondaryController =
            new CommandGenericHID(Identifiers.Controllers.SECONDARY_CONTROLLER);

    private final SendableChooser<String> autoOptionsSelector = new SendableChooser<>();

    public RobotContainer() {
        for (String auto : AutoBuilder.getAllAutoNames()) {
            if (auto.equals("REGISTERED_COMMANDS")) continue;
            autoOptionsSelector.addOption(auto, auto);
        }
        autoOptionsSelector.setDefaultOption(DEFAULT_AUTO, DEFAULT_AUTO);
        SmartDashboard.putData("auto/Auto Option", autoOptionsSelector);

        configureBindings();
    }

    private void configureBindings() {
        if (RobotBase.isSimulation()) return;

        primaryController.a().onTrue(pivot.getCommands().set(Pivot.State.LAUNCHER));
        primaryController.b().onTrue(pivot.getCommands().set(Pivot.State.GROUND));
        primaryController.x().onTrue(manipulator.getCommands().set(Manipulator.State.EJECT_TO_LAUNCHER));
        primaryController.y().onTrue(manipulator.getCommands().set(Manipulator.State.GRAB));

//        // Primary Driver
//        drive.setDefaultCommand(
//                driveCommands.teleopCommand(
//                        () -> -controlCurve(primaryController.getLeftY()),
//                        () -> -controlCurve(primaryController.getLeftX()),
//                        () -> -controlCurve(primaryController.getRightX())
//                )
//        );
//
//        primaryController.leftTrigger().onTrue(commands.launch());
//        primaryController.rightTrigger().onTrue(commands.startGroundPickup()).onFalse(commands.endGroundPickup());
//
//        primaryController.leftBumper().whileTrue(climberCommands.set(-1));
//        primaryController.rightBumper().whileTrue(climberCommands.set(1));
//
//
//        primaryController.a().onTrue(commands.setClimbingMode(true));
//        primaryController.b().onTrue(commands.setClimbingMode(false));
//
//        // Secondary Controller
//        secondaryController.button(1).onTrue(driveCommands.setHeadingMode(Drive.HeadingMode.SPEAKER_LEFT));
//        secondaryController.button(2).onTrue(driveCommands.setHeadingMode(Drive.HeadingMode.STRAIGHT));
//        secondaryController.button(4).onTrue(driveCommands.setHeadingMode(Drive.HeadingMode.SPEAKER_RIGHT));
//
//        secondaryController.button(5).onTrue(driveCommands.setHeadingMode(Drive.HeadingMode.STAGE_LEFT));
//        secondaryController.button(6).onTrue(driveCommands.setHeadingMode(Drive.HeadingMode.STRAIGHT));
//        secondaryController.button(8).onTrue(driveCommands.setHeadingMode(Drive.HeadingMode.STAGE_RIGHT));
//
//        secondaryController.button(13).onTrue(launcherCommands.deactivate());
//        secondaryController.button(17).onTrue(launcherCommands.initialize());
    }

    private double controlCurve(double input) {
        return Utils.controlCurve(input, Joystick.EXPONENT, Joystick.DEADBAND) *
                (climber.getClimbingMode() ? CLIMBING_MODE_SPEED : 1);
    }

    public Command getEnableCommand() {
        return pivot.getCommands().reset();
    }

    public Command getAutonomousCommand() {
        return pivot.getCommands().set(Pivot.State.LAUNCHER);
//        return AutoBuilder.buildAuto(autoOptionsSelector.getSelected());
    }
}
