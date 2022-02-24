package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class TeleOp extends OpModeTemplate{

    @Override
    public void initialize() {
        initHardware(false);

    }

    @Override
    public void run() {
        super.run();

        mecanumDrive.setDrivePower(
                new Pose2d(-gamepad1.left_stick_y,
                        -gamepad1.left_stick_x,
                        -gamepad1.right_stick_x)
        );

        double rawIntakePower = -gamepad2.left_stick_y;
        intake.setPower(Math.signum(rawIntakePower) * rawIntakePower * rawIntakePower);


    }
}
