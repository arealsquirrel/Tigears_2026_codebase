package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.Spindexer;


@TeleOp(name="Club Rush Teleop", group="Linear OpMode")
public class TeleopCode extends LinearOpMode {
    @Override
    public void runOpMode() {
        double intake_speed = 1.0f;
        double move_speed = 9;
        double rot_speed = 5;
        double slow_multiplier = 0.5;

        Intake intake = new Intake(hardwareMap, 0.0f);
        Spindexer spindexer = new Spindexer(hardwareMap, telemetry);
        Movement movement = new Movement(hardwareMap, telemetry, move_speed, move_speed, rot_speed);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();
            intake.update();
            spindexer.update();

            // ---------- GAMEPAD 1 ---------- //
            if (gamepad1.dpad_up) {
                intake.speed = intake_speed;
            } else if (gamepad1.dpad_down) {
                intake.speed = -intake_speed;
            } else {
                intake.speed = 0;
            }

            if(gamepad1.a) {
                movement.update(
                        -gamepad1.left_stick_y * slow_multiplier,
                        gamepad1.left_stick_x * slow_multiplier,
                        gamepad1.right_stick_x * slow_multiplier);
            } else {
                double rotate = gamepad1.right_trigger - gamepad1.left_trigger;
                movement.update(-rotate, gamepad1.right_stick_x, gamepad1.right_stick_y);
            }

                // ---------- GAMEPAD 2 ---------- //
            if(gamepad2.dpad_up) {
                spindexer.wack_ass_servo_move_param = 1;
                spindexer.front_intake_servo_raised = true;
            } else if (gamepad2.dpad_down) {
                spindexer.wack_ass_servo_move_param = -1;
                spindexer.front_intake_servo_raised = true;
            } else {
                spindexer.wack_ass_servo_move_param = 0;
            }

            // rotate right
            if(gamepad2.right_bumper) {
                spindexer.move_motor(90, 0.5);
            }

            if(gamepad2.left_bumper) {
                spindexer.move_motor(-90, 0.5);
            }

            spindexer.front_intake_servo_raised = gamepad2.a;
            spindexer.back_turret_servo_raised = gamepad2.b;
        }
    }
}
