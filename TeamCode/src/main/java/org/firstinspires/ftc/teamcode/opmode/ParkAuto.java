package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class ParkAuto extends OpModeTemplate {
    @Override
    public void initialize() {
        initHardware(true);

        TrajectorySequence parkSequence =
                mecanumDrive.trajectorySequenceBuilder(new Pose2d(0.0, 0.0, 0.0))
                        .forward(30.0)
                        .build();

        waitForStart();

        mecanumDrive.followTrajectorySequence(parkSequence);

    }
}
