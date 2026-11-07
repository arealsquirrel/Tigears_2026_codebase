package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class Turret {
    Servo turret_swivel;
    public DcMotor turret_flywheel;
    double position;
    Telemetry telemetry;
    // Limelight3A limelight;
    int tag_id_1;
    int tag_id_2;
    private static final boolean REVERSE = true;      // flip if servo turns the wrong way
    public Turret(HardwareMap hardware_map, Telemetry telemetry, int tag_id_1, int tag_id_2) {
        this.telemetry = telemetry;
        this.tag_id_1 = tag_id_1;
        this.tag_id_2 = tag_id_2;

        turret_swivel = hardware_map.get(Servo.class, "turret_servo");
        turret_flywheel = hardware_map.get(DcMotor.class, "flywheel_motor");
        /*
        limelight = hardware_map.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();

         */

        double servoPos = 0.5;
        turret_swivel.setPosition(servoPos);
        turret_flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void update() {
        // double pos = Math.abs(Math.sin(position += 0.0005));
        // turret_swivel.setPosition(Range.clip(pos, 0.0, 1.0));
        // telemetry.addData("position ", pos);

        /*
        LLResultTypes.FiducialResult tag = findTargetTag();
        if (tag != null) {
            double tx = tag.getTargetXDegrees(); // + = tag is to the right of crosshair
            double dir = REVERSE ? -1.0 : 1.0;

            position = 0.5 + dir * (tx / 180);
            position = Range.clip(position, 0, 1);
            turret_swivel.setPosition(position);

            telemetry.addData("Tag ID", tag.getFiducialId());
            telemetry.addData("tx (deg)", "%.2f", tx);
            telemetry.addData("ty (deg)", "%.2f", tag.getTargetYDegrees());
        } else {
            telemetry.addLine("No tag visible - holding position");
        }

        telemetry.addData("Servo pos", "%.3f", position);
        telemetry.update();
         */
    }

    /*
    private LLResultTypes.FiducialResult findTargetTag() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return null;

        List<LLResultTypes.FiducialResult> tags = result.getFiducialResults();
        if (tags == null || tags.isEmpty()) return null;

        //if (TARGET_TAG_ID < 0) return tags.get(0);

        for (LLResultTypes.FiducialResult t : tags) {
            // return t;
            if (t.getFiducialId() == tag_id_1 || t.getFiducialId() == tag_id_2) return t;
        }
        return null;
    }

     */
}
