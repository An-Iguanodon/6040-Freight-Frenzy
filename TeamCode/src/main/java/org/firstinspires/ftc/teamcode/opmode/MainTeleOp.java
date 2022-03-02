package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Deposit;

@TeleOp
public class MainTeleOp extends OpModeTemplate{

    @Override
    public void initialize() {
        initHardware(true);

        new GamepadButton(opGamepad, GamepadKeys.Button.DPAD_UP).whenPressed(() -> Deposit.offset += 0.25);
        new GamepadButton(opGamepad, GamepadKeys.Button.DPAD_DOWN).whenPressed(() -> Deposit.offset -= 0.25);
    }

    @Override
    public void run() {
        super.run();

        mecanumDrive.setDrivePower(
                new Pose2d(-gamepad1.left_stick_y,
                        -gamepad1.left_stick_x,
                        -gamepad1.right_stick_x)
        );


        // double rawIntakePower = -gamepad2.left_stick_y;
        // intake.setPower(Math.signum(rawIntakePower) * rawIntakePower * rawIntakePower);


    }
}
