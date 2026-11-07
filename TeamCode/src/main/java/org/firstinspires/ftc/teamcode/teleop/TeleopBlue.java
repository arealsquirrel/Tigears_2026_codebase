package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.teamcode.config.INTAKE_SPEED;
import static org.firstinspires.ftc.teamcode.config.MOVE_SPEED;
import static org.firstinspires.ftc.teamcode.config.ROT_SPEED;
import static org.firstinspires.ftc.teamcode.config.SLOW_MULTIPLIER;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.Movement;
import org.firstinspires.ftc.teamcode.modules.Spindexer;
import org.firstinspires.ftc.teamcode.modules.Turret;


@TeleOp(name="Teleop Blue", group="Linear OpMode")
public class TeleopBlue extends LinearOpMode {
    @Override
    public void runOpMode() {
        Intake intake = new Intake(hardwareMap, 0.0f);
        Spindexer spindexer = new Spindexer(hardwareMap, telemetry);
        Movement movement = new Movement(hardwareMap, telemetry, MOVE_SPEED, MOVE_SPEED, ROT_SPEED);
        // Turret turret = new Turret(hardwareMap, telemetry, 40, 43);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();
            intake.update();
            spindexer.update();
            // turret.update();

            // ---------- GAMEPAD 1 ---------- //
            if (gamepad1.dpad_up) {
                intake.speed = INTAKE_SPEED;
            } else if (gamepad1.dpad_down) {
                intake.speed = -INTAKE_SPEED;
            } else {
                intake.speed = 0;
            }

            if(gamepad1.a) {
                movement.update(
                        -gamepad1.left_stick_y * SLOW_MULTIPLIER,
                        gamepad1.left_stick_x * SLOW_MULTIPLIER,
                        gamepad1.right_stick_x * SLOW_MULTIPLIER);
            } else {
                double rotate = gamepad1.right_trigger - gamepad1.left_trigger;
                if(rotate == 0) { rotate = gamepad1.left_stick_x; }
                movement.update(-rotate, gamepad1.right_stick_x, gamepad1.right_stick_y);
            }

            // ---------- GAMEPAD 2 ---------- //
            spindexer.front_intake_servo_raised = gamepad2.a;
            spindexer.back_turret_servo_raised = gamepad2.b;

            if(gamepad2.dpad_up) {
                spindexer.wack_ass_servo_move_param = 1;
                spindexer.front_intake_servo_raised = true;
            } else if (gamepad2.dpad_down) {
                spindexer.wack_ass_servo_move_param = -1;
                spindexer.front_intake_servo_raised = true;
            } else {
                spindexer.wack_ass_servo_move_param = 0;
            }

            // rotate
            if(gamepad2.right_bumper) {
                spindexer.moving = true;
                if(spindexer.spindexor_speed < 0)
                    spindexer.spindexor_speed *= -1;
            }

            if(gamepad2.left_bumper) {
                spindexer.moving = true;
                if(spindexer.spindexor_speed > 0)
                    spindexer.spindexor_speed *= -1;
            }
        }
    }
}
