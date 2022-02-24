package org.firstinspires.ftc.teamcode.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Intake subsystem
 */
public class Intake extends SubsystemBase {
    private HardwareMap hwMap;
    private DcMotorEx intakeMotor1;
    private DcMotorEx intakeMotor2;

    private double power;

    public Intake(HardwareMap hwMap) {
        this.hwMap = hwMap;

        intakeMotor1 = hwMap.get(DcMotorEx.class, "intakeMotor1");
        intakeMotor2 = hwMap.get(DcMotorEx.class, "intakeMotor2");
        intakeMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void register() {
        super.register();
    }

    @Override
    public void periodic() {
        intakeMotor1.setPower(power);
        intakeMotor2.setPower(power);
    }

    public void intake() {
        power = 1.0;
    }

    public void outtake() {
        power = -1.0;
    }

    public void stop() {
        power = 0.0;
    }

    public void setPower(double power) {
        this.power = power;
    }
}
