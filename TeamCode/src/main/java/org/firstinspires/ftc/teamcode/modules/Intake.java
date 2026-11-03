package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public double speed;
    HardwareMap hardware_map;
    public DcMotor intake_motor;

    public Intake(HardwareMap hardware_map, double speed) {
        // moving = false;
        this.speed = speed;
        this.hardware_map = hardware_map;
        intake_motor = hardware_map.get(DcMotor.class, "intake_motor");
    }

    public void update() {
        intake_motor.setPower(speed);
    }
}
