package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Log {
    public FtcDashboard dashboard;
    public MultipleTelemetry data;

    public Log(FtcDashboard dash, Telemetry... telem){
        this.dashboard = dash;
        data = new MultipleTelemetry(telem);
    }
}
