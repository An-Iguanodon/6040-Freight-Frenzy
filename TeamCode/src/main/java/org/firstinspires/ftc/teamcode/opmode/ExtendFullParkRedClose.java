package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.command.DepositCommand;
import org.firstinspires.ftc.teamcode.command.FollowTrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class ExtendFullParkRedClose extends OpModeTemplate {
    @Override
    public void initialize() {
        initHardware(true);

        mecanumDrive.setPoseEstimate(new Pose2d(12, -63, -Math.PI/2));

        final TrajectorySequence toShippingHub =
                mecanumDrive.trajectorySequenceBuilder(new Pose2d(12, -63, -Math.PI/2))
                .strafeRight(32)
                .back(16)
                .build();

        final TrajectorySequence park =
                mecanumDrive.trajectorySequenceBuilder(new Pose2d(-12, -48, -Math.PI/2))
                .splineTo(new Vector2d(20, -63), 0)
                .forward(18)
                .build();

        schedule(new SequentialCommandGroup(
                new DepositCommand(deposit, deposit::extend).alongWith(
                        new FollowTrajectorySequence(mecanumDrive, toShippingHub)),
                new DepositCommand(deposit, deposit::deploy),
                new DepositCommand(deposit, deposit::retract).alongWith(
                        new FollowTrajectorySequence(mecanumDrive, park))
        ));
    }
}
