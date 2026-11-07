package org.firstinspires.ftc.teamcode.debug_teleops;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Movement;

@TeleOp(name="DEBUG movement", group="Linear OpMode")
public class debug_movement extends LinearOpMode {
    @Override
    public void runOpMode() {
        double move_speed = 4;
        double rot_speed = 2;

        double slow_multiplier = 0.5;

        int what_change = 0;

        Movement movement = new Movement(hardwareMap, telemetry, move_speed, move_speed, rot_speed);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.update();
            telemetry.addData("movement speed", move_speed);
            telemetry.addData("rotation speed", rot_speed);

            double rotate = gamepad1.right_trigger - gamepad1.left_trigger;
            if(rotate == 0) { rotate = gamepad1.left_stick_x; }
            movement.update(-gamepad1.left_stick_y, gamepad1.left_stick_x, rotate);


            if(gamepad1.a) { what_change = ((what_change+1)%3); }

            telemetry.addLine("press A to change what changed, use the up and down on the dpad to change stuff around till you like how it feels");
            if(what_change == 0) {
                telemetry.addLine("[x] move speed\n[ ] rotation speed\n[ ] slow roll mult");
                if(gamepad1.dpad_up) { move_speed += 0.0001; }
                if(gamepad1.dpad_down) { move_speed -= 0.0001; }
            } else if (what_change == 1) {
                telemetry.addLine("[ ] move speed\n[x] rotation speed\n[ ] slow roll mult");
                if(gamepad1.dpad_up) { rot_speed += 0.0001; }
                if(gamepad1.dpad_down) { rot_speed -= 0.0001; }
            } else if (what_change == 2){
                telemetry.addLine("[ ] move speed\n[ ] rotation speed\n[x] slow roll mult");
                if(gamepad1.dpad_up) { slow_multiplier += 0.0001; }
                if(gamepad1.dpad_down) { slow_multiplier -= 0.0001; }
            }

            movement.maxRateY = move_speed;
            movement.maxRateX = move_speed;
            movement.maxRateRX = rot_speed;
        }
    }
}
