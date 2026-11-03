package org.firstinspires.ftc.teamcode.debug_teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Intake;

@TeleOp(name="DEBUG intake", group="Linear OpMode")
public class debug_intake extends LinearOpMode {
    @Override
    public void runOpMode() {
        double intake_speed = 1.0f;
        Intake intake = new Intake(hardwareMap, intake_speed);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.update();
            telemetry.addData("Intake Speed ", intake_speed);
            telemetry.addLine("use the dpad on gamepad 2 to move the intake");
            telemetry.addLine("use the left and right bumpers to change the intake speed");

            if (gamepad1.dpad_up) {
                intake.speed = intake_speed;
            } else if (gamepad1.dpad_down) {
                intake.speed = -intake_speed;
            } else {
                intake.speed = 0;
            }

            intake.update();

            // ----------- CHANGING SHIT -----------
            if(gamepad1.left_bumper) intake_speed -= 0.001;
            if(gamepad1.right_bumper) intake_speed += 0.001;
        }
    }
}
