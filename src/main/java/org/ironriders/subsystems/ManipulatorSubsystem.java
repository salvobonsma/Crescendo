package org.ironriders.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.ironriders.commands.ManipulatorCommands;
import org.ironriders.constants.Identifiers;

import static com.revrobotics.CANSparkMax.IdleMode.kBrake;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import static org.ironriders.constants.Manipulator.*;
import static org.ironriders.constants.Robot.COMPENSATED_VOLTAGE;

public class ManipulatorSubsystem extends SubsystemBase {
    private final ManipulatorCommands commands;

    private final CANSparkMax motor = new CANSparkMax(Identifiers.Manipulator.MOTOR, kBrushless);

    public ManipulatorSubsystem() {
        motor.setSmartCurrentLimit(CURRENT_LIMIT);
        motor.enableVoltageCompensation(COMPENSATED_VOLTAGE);
        motor.setIdleMode(kBrake);

        SmartDashboard.putString(DASHBOARD_PREFIX + "state", "STOP");

        commands = new ManipulatorCommands(this);
    }

    public void set(State state) {
        switch (state) {
            case GRAB -> intake();
            case EJECT_TO_SHOOTER -> dischargeForShooter();
            case EJECT_TO_AMP -> dischargeForAmp();
            case STOP -> stop();
        }
        SmartDashboard.putString(DASHBOARD_PREFIX + "state", state.name());
    }

    private void intake() {
        motor.set(INTAKE_SPEED);
    }

    private void dischargeForShooter() {
        motor.set(DISCHARGE_FOR_SHOOTER_SPEED);
    }

    private void dischargeForAmp() {
        motor.set(DISCHARGE_FOR_AMP_SPEED);
    }

    private void stop() {
        motor.set(0);
    }

    public ManipulatorCommands getCommands() {
        return commands;
    }
}
