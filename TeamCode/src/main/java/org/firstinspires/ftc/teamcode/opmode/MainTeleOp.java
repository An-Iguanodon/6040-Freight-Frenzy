package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Log;
import org.firstinspires.ftc.teamcode.Testing;
import org.firstinspires.ftc.teamcode.subsystem.Deposit;

@TeleOp
@Config
public class MainTeleOp extends OpModeTemplate{

    private double TEST_POS = 0;

    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();

    @Override
    public void initialize() {
        initHardware(true);

        new GamepadButton(opGamepad, GamepadKeys.Button.LEFT_BUMPER).whenPressed(() -> deposit.extend());
        new GamepadButton(opGamepad, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(deposit::deploy);
        new GamepadButton(opGamepad, GamepadKeys.Button.A).whenPressed(deposit::retract);

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

        if(gamepad2.dpad_up) {
            TEST_POS += 0.01;
        }
        if(gamepad2.dpad_down) {
            TEST_POS -= 0.01;
        }
        if(gamepad2.b) {
            deposit.setDepositServo(TEST_POS);
        }

        dashboardTelemetry.addData("Current Position:", deposit.getCurrentPos());
        dashboardTelemetry.addData("Target Position:", deposit.getTargetPos());
        dashboardTelemetry.addData("Lift Motor Power:", deposit.getLiftMotorPower());
        dashboardTelemetry.addData("Offset:", deposit.getOffset());
        dashboardTelemetry.addData("Test Position:", TEST_POS);
        dashboardTelemetry.update();

    }
}
