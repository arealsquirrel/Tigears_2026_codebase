package org.firstinspires.ftc.teamcode.debug_teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.Turret;

@TeleOp(name="DEBUG turret", group="Linear OpMode")

public class debug_turret extends LinearOpMode {

    @Override
    public void runOpMode() {
        Turret turret = new Turret(hardwareMap, telemetry);
        // DcMotor motor = hardwareMap.get(DcMotor.class, "motor");

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();
            // motor.setPower((gamepad1.a ? 0 : 1) * 0.5);
            turret.update();
        }
    }
}
