package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.config.MOVE_SPEED;
import static org.firstinspires.ftc.teamcode.config.ROT_SPEED;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.Movement;

@Autonomous(name="Robot: Auto Drive By Time", group="Robot")
public class AutoCode extends LinearOpMode {

    @Override
    public void runOpMode() {
        Movement movement = new Movement(hardwareMap, telemetry, MOVE_SPEED, MOVE_SPEED, ROT_SPEED);

        waitForStart();

        ElapsedTime timer = new ElapsedTime();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive() && timer.seconds() < 0.3) {
            telemetry.update();
            movement.update(0, -1, 0);
        }

        movement.update(0, 0, 0);
    }
}
