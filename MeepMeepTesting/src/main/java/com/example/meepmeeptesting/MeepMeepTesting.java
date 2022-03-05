package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        // Declare a MeepMeep instance
        // With a field size of 800 pixels
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Required: Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(40, 52.56702632542596, Math.toRadians(180), Math.toRadians(273.8062499999999), 10.075)
                // Option: Set theme. Default = ColorSchemeRedDark()
                .setColorScheme(new ColorSchemeRedDark())
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, -63, -Math.PI/2))
                                .strafeRight(24)
                                .back(15)
                                .addDisplacementMarker(() -> {
                                    //deposit.extend();
                                })
                                .waitSeconds(4)
                                .addDisplacementMarker(() -> {
                                    //deposit.deploy();
                                })
                                .waitSeconds(0.5)
                                .addDisplacementMarker(() -> {
                                    //deposit.retract();
                                })
                                .splineTo(new Vector2d(20, -63), 0)
                                .forward(24)
                                .build()
                );

        // Set field image
        meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
                .setDarkMode(true)
                // Background opacity from 0-1
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}