package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class Deposit extends SubsystemBase {
    private HardwareMap hwMap;
    private DcMotorEx liftMotor;
    private Servo depositServo;

    private int targetPos;
    public static int offset = 0;

    ElapsedTime timer = new ElapsedTime();

    private static final int RETRACTED_POS = 100;
    public static int EXTEND_POS = 5500;

    private static final double RETRACTED_POSITION = 0.39;
    private static final double MOVING_POSITION = 0.75;
    private static final double DEPOSIT_POSITION = 0.05;

    private static final double DEPOSIT_TIME = 10;

    private enum State {
        RETRACTED,
        MOVING,
        DEPOSIT,
        RETRACTING
    }

    private State state = State.RETRACTED;
    private double liftMotorPower = 1;

    public Deposit(HardwareMap hwMap, boolean resetEncoders) {
        this.hwMap = hwMap;

        liftMotor = hwMap.get(DcMotorEx.class, "liftMotor");
        depositServo = hwMap.get(Servo.class, "depositServo");

        if(resetEncoders) {
            liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        liftMotor.setTargetPosition(RETRACTED_POS);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        depositServo.setPosition(RETRACTED_POSITION);
    }

    @Override
    public void register() {
        super.register();
    }

    @Override
    public void periodic() {
        liftMotor.setTargetPosition(targetPos + offset);
        liftMotor.setPower(liftMotorPower);

        switch(state) {
            case RETRACTED:
                depositServo.setPosition(RETRACTED_POSITION);
                break;
            case RETRACTING:
                depositServo.setPosition(MOVING_POSITION);
                if (Math.abs(
                        liftMotor.getCurrentPosition() - liftMotor.getTargetPosition())
                        < 100) {
                    state = State.RETRACTED;
                }
                break;
            case MOVING:
                depositServo.setPosition(MOVING_POSITION);
                break;
            case DEPOSIT:
                depositServo.setPosition(DEPOSIT_POSITION);

                if (timer.seconds() > DEPOSIT_TIME) {
                    targetPos = RETRACTED_POS;
                    state = State.RETRACTING;
                }
                break;

        }
    }

    public void goToPos(int targetPos) {
        this.targetPos = targetPos;
        liftMotorPower = 1.0;
        state = State.MOVING;
    }

    public void extend() {
        goToPos(EXTEND_POS);
    }

    public void retract() {
        targetPos = RETRACTED_POS;
        state = State.RETRACTING;
    }

    public void deploy() {
        if (state == State.RETRACTING || state == State.RETRACTED) {
            return;
        }

        timer.reset();
        state = State.DEPOSIT;
    }

    public void setDepositServo(double pos) {
        depositServo.setPosition(pos);
    }

    public void adjustTargetPos(int difference) {
        targetPos += difference;
    }

    public double getTargetPos() {
        return targetPos;
    }

    public double getCurrentPos() {
        return liftMotor.getCurrentPosition();
    }

    public double getLiftMotorPower() {
        return liftMotor.getPower();
    }

    public double getOffset() {
        return offset;
    }

    public boolean isBusy() {
        return liftMotor.isBusy() || state == State.DEPOSIT;
    }

}
