package org.firstinspires.ftc.teamcode.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Deposit extends SubsystemBase {
    private HardwareMap hwMap;
    private DcMotorEx liftMotor;
    private Servo depositServo;

    private double targetHeight;
    public static double offset = 0;

    ElapsedTime timer = new ElapsedTime();

    private static final double RETRACTED_HEIGHT = 0;
    private static final double LEVEL_1_HEIGHT = 0;
    private static final double LEVEL_2_HEIGHT = 0;
    private static final double LEVEL_3_HEIGHT = 0;

    private static final double RETRACTED_POSITION = 0;
    private static final double MOVING_POSITION = 0;
    private static final double DEPOSIT_POSITION = 0;

    private static final double DEPOSIT_TIME = 0;

    private enum State {
        RETRACTED,
        MOVING,
        DEPOSIT,
        RETRACTING
    }

    private State state = State.RETRACTED;
    private double power;

    public Deposit(HardwareMap hwMap, boolean resetEncoders) {
        this.hwMap = hwMap;

        liftMotor = hwMap.get(DcMotorEx.class, "liftMotor");
        depositServo = hwMap.get(Servo.class, "depositServo");

        if(resetEncoders) {
            liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        liftMotor.setTargetPosition(inchesToTicks(RETRACTED_HEIGHT));
    }

    @Override
    public void register() {
        super.register();
    }

    @Override
    public void periodic() {
        liftMotor.setTargetPosition(inchesToTicks(targetHeight + offset));
        liftMotor.setPower(1.0);

        switch(state) {
            case RETRACTED:
                depositServo.setPosition(RETRACTED_POSITION);
                break;
            case RETRACTING:
                depositServo.setPosition(MOVING_POSITION);

                if (Math.abs(
                        liftMotor.getCurrentPosition() - liftMotor.getTargetPosition())
                        < inchesToTicks(2.0)) {
                    state = State.RETRACTED;
                }
                break;
            case MOVING:
                depositServo.setPosition(MOVING_POSITION);
            case DEPOSIT:
                depositServo.setPosition(DEPOSIT_POSITION);

                if (timer.seconds() > DEPOSIT_TIME) {
                    targetHeight = RETRACTED_HEIGHT;
                    state = State.RETRACTING;
                }
                break;

        }
    }

    public void goToHeight(double targetHeight) {
        this.targetHeight = targetHeight;
        state = State.MOVING;
    }


    public void goToLevel1() {
        goToHeight(LEVEL_1_HEIGHT);
    }

    public void goToLevel2() {
        goToHeight(LEVEL_2_HEIGHT);
    }

    public void goToLevel3() {
        goToHeight(LEVEL_3_HEIGHT);
    }

    public void retract() {
        targetHeight = RETRACTED_HEIGHT;
        state = State.RETRACTING;
    }

    public void deploy() {
        if (state == State.RETRACTING || state == State.RETRACTED) {
            return;
        }

        timer.reset();
        state = State.DEPOSIT;
    }

    public boolean isBusy() {
        return liftMotor.isBusy() || state == State.DEPOSIT;
    }

    private static int inchesToTicks(double inches) {
        final double CIRCUMFERENCE = 17.3205081;

        final double TICKS_PER_ROTATION = 28 * 5.23;
        return (int) (inches / CIRCUMFERENCE * TICKS_PER_ROTATION);
    }
}
