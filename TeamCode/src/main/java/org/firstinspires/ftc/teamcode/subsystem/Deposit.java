package org.firstinspires.ftc.teamcode.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Deposit extends SubsystemBase {
    private HardwareMap hwMap;
    private DcMotorEx liftMotor;
    private ServoEx stopper;

    private double power;

    public Deposit(HardwareMap hwMap) {
        this.hwMap = hwMap;

        liftMotor = hwMap.get(DcMotorEx.class, "liftMotor");
    }

    @Override
    public void register() {
        super.register();
    }

    @Override
    public void periodic() {
        liftMotor.setPower(power);
    }

    public void extend() {
        power = 1.0;
    }

    public void retract() {
        power = -1.0;
    }

    public void stop() {
        power = 0.0;
    }

    public void setPower(double power) {
        this.power = power;
    }
}
