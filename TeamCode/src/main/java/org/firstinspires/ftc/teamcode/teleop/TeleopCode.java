package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


@TeleOp(name="Teleop code!!", group="Linear OpMode")
public class TeleopCode extends LinearOpMode {
    @Override
    public void runOpMode() {
        // the deviceName is the device as labeled on the control hub
        // //DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "motorName");

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();
            // motor.setPower(0.1); // make that motor move!
        }
    }
}
