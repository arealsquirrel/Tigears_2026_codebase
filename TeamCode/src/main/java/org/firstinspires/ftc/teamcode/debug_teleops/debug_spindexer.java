package org.firstinspires.ftc.teamcode.debug_teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.modules.Spindexer;

@TeleOp(name="DEBUG spindexer", group="Linear OpMode")
public class debug_spindexer extends LinearOpMode {
    @Override
    public void runOpMode() {
        Spindexer spindexer = new Spindexer(hardwareMap, telemetry);

        waitForStart();

        double pos = 0;

        while (opModeIsActive()) {
            telemetry.update();
            spindexer.update();
            telemetry.addData("position ", pos);

            if(gamepad1.dpad_up) {
                spindexer.wack_ass_servo_move_param = 1;
            } else if (gamepad1.dpad_down) {
                spindexer.wack_ass_servo_move_param = -1;
            } else {
                spindexer.wack_ass_servo_move_param = 0;
            }

            spindexer.front_intake_servo_raised = gamepad1.a;
            spindexer.back_turret_servo_raised = gamepad1.b;
        }
    }
}
