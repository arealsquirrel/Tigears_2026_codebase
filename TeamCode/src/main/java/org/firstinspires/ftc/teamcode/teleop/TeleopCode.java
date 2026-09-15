package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.modules.Movement;


@TeleOp(name="Club Rush Teleop", group="Linear OpMode")
public class TeleopCode extends LinearOpMode {
    @Override
    public void runOpMode() {


        /*
        Movement movement = new Movement(hardwareMap, telemetry, 4,2.5,2.5);

        // the deviceName is the device as labeled on the control hub
        // //DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "motorName");

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();
            // motor.setPower(0.1); // make that motor move!

            double y = gamepad1.left_stick_y * -1;        // forward/backward
            double x = gamepad1.left_stick_x * 1.1;   // strafe correction
            double rotate = gamepad1.right_stick_x;
            movement.move_robot(x,y,rotate);
        }
         */
    }
}
