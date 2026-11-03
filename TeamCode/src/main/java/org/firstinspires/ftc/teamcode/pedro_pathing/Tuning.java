package org.firstinspires.ftc.teamcode.pedro_pathing;

import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.pedro_pathing.procedures.ForesightTuner;
import org.firstinspires.ftc.teamcode.pedro_pathing.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.pedro_pathing.procedures.OTOSTuner;

public class Tuning {
    @Tuner
    public static Procedure mecanumTuner() {
        return new MecanumTuner();
    }

    @Tuner
    public static Procedure otosTuner() {
        return new OTOSTuner();
    }

    /*
    @Tuner
    public static Procedure foresightTuner() {
        return new ForesightTuner((hardwareMap) -> new PinpointLocalizer(hardwareMap, Constants.localizerConfig), (hardwareMap) -> new Mecanum(hardwareMap, Constants.drivetrainConfig));
    }
    
     */
}
