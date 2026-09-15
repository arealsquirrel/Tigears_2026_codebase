package org.firstinspires.ftc.teamcode.modules;

import static org.firstinspires.ftc.teamcode.config.LIMELIGHT_NAME;
import static org.firstinspires.ftc.teamcode.config.LIMELIGHT_PIPELINE_APRIL_TAG;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.*;
import com.qualcomm.hardware.limelightvision.*;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class Limelight {
    private Limelight3A limelight;
    private  Telemetry telemetry;
    private int pipeline = LIMELIGHT_PIPELINE_APRIL_TAG;

    /**
     * sets the poll rate Hz to 100 and the default pipeline is 0
     *
     * @param hardwareMap the hardware map from the teleop class
     * @param _telemetry the telemetry from the teleop class
     */
    Limelight(HardwareMap hardwareMap, Telemetry _telemetry) {
        telemetry = _telemetry;
        limelight = hardwareMap.get(Limelight3A.class, LIMELIGHT_NAME);
        telemetry.addData("Status", "Limelight Initialized");

        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(pipeline);

        // start polling data from the camera
        limelight.start();
    }
}
