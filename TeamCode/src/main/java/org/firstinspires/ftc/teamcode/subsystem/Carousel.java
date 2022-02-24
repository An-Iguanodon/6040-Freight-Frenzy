package org.firstinspires.ftc.teamcode.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Carousel extends SubsystemBase {
    private HardwareMap hwMap;
    private CRServo carouselServo;

    private double power;

    public Carousel(HardwareMap hwMap) {
        this.hwMap = hwMap;

        carouselServo = hwMap.get(CRServo.class, "carouselServo");
    }

    @Override
    public void register() {
        super.register();
    }

    @Override
    public void periodic() {
        carouselServo.setPower(power);
    }

    public void blueSpin() {
        power = 1.0;
    }

    public void redSpin() {
        power = -1.0;
    }

    public void stop() {
        power = 0.0;
    }

    public void setPower(double power) {
        this.power = power;
    }
}
