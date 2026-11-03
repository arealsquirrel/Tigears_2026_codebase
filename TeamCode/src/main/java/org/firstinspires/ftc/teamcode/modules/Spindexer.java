package org.firstinspires.ftc.teamcode.modules;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Spindexer {
    HardwareMap hardware_map;
    // DcMotor spindexor_motor;
    Servo front_intake_servo;
    public boolean front_intake_servo_raised;
    Servo back_turret_servo;
    public boolean back_turret_servo_raised;
    Servo wack_ass_servo_intake;
    public int wack_ass_servo_move_param;
    int current_slot_index;
    Telemetry telemetry;
    DcMotor spindexor_motor;
    NormalizedColorSensor colorSensor;

    static final int TICKS_PER_REV = 540;
    static final double GEAR_RATIO = 1.0; // external gearing after the motor (e.g. 2.0 for a 2:1 reduction)
    public Spindexer(HardwareMap hardware_map, Telemetry telemetry) {
        this.hardware_map = hardware_map;
        this.telemetry = telemetry;

        current_slot_index = 0;

        front_intake_servo = hardware_map.get(Servo.class, "front_intake_servo");
        front_intake_servo.setDirection(Servo.Direction.REVERSE); // Reverses 0.0 <-> 1.0 behavior

        back_turret_servo = hardware_map.get(Servo.class, "back_turret_servo");
        back_turret_servo.setDirection(Servo.Direction.REVERSE); // Reverses 0.0 <-> 1.0 behavior

        wack_ass_servo_intake = hardware_map.get(Servo.class, "intake_servo");
        wack_ass_servo_intake.setDirection(Servo.Direction.REVERSE);

        spindexor_motor = hardware_map.get(DcMotor.class, "spindexor_motor");
        spindexor_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // helps it hold position
        spindexor_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spindexor_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        colorSensor = hardware_map.get(NormalizedColorSensor.class, "color_sensor");
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        colorSensor.setGain(10);
    }

    public void update() {

        if(front_intake_servo_raised) {
            front_intake_servo.setPosition(0.55);
        } else {
            front_intake_servo.setPosition(0);
        }

        if(back_turret_servo_raised) {
            back_turret_servo.setPosition(0.24);
        } else {
            back_turret_servo.setPosition(0.6);
        }

        wack_ass_servo_intake.setPosition(wack_ass_servo_move_param*0.1 + 0.5);

        // double pos = Math.abs(Math.sin(position += 0.0005));

        if(spindexor_motor.isBusy()) {
            telemetry.addLine("SPINDEXER ROTATING TO POSITION");
        } else {
            spindexor_motor.setPower(0);
            spindexor_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        telemetry.addLine()
                .addData("Red", "%.3f", colors.red)
                .addData("Green", "%.3f", colors.green)
                .addData("Blue", "%.3f", colors.blue);
    }

    public void move_motor(double degrees, double power) {
        if(spindexor_motor.isBusy())
            return;

        int ticks = (int) Math.round(TICKS_PER_REV * GEAR_RATIO * degrees / 360.0);
        spindexor_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spindexor_motor.setTargetPosition(ticks);
        spindexor_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spindexor_motor.setPower(power);
    }
}
