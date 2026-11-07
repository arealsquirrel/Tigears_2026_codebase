package org.firstinspires.ftc.teamcode.debug_teleops;

import static org.firstinspires.ftc.teamcode.config.SET_FLYWHEEL_SPEED;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.Turret;

@TeleOp(name="DEBUG turret", group="Linear OpMode")

public class debug_turret extends LinearOpMode {

    @Override
    public void runOpMode() {
        Turret turret = new Turret(hardwareMap, telemetry,30, 31);
        // Servo servo = hardwareMap.get(Servo.class, "servo");

        boolean auto_aim = false;

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();
            turret.turret_flywheel.setPower((gamepad1.right_bumper ? 0 : 1) * SET_FLYWHEEL_SPEED);

            if(auto_aim) turret.update();

            auto_aim = gamepad1.a;
        }
    }
}
