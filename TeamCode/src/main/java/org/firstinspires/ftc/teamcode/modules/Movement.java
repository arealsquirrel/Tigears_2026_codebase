package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Movement {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;

    DcMotor backRightMotor;
    DcMotor frontRightMotor;

    // movement paramters
    double yLimited = 0;
    double xLimited = 0;
    double rxLimited = 0;
    double maxRateY;     // Forward/backward acceleration rate
    double maxRateX;   // Strafing acceleration rate
    double maxRateRX;  // Turning acceleration rate

    HardwareMap hardwareMap;
    Telemetry telemetry;
    long lastTime;

    public Movement(HardwareMap t_hardwareMap, Telemetry t_telemetry, double t_max_rateX, double t_max_rateY, double t_max_rateRX) {
        hardwareMap = t_hardwareMap;
        telemetry = t_telemetry;

        maxRateX = t_max_rateX;
        maxRateY = t_max_rateY;
        maxRateRX = t_max_rateRX;

        frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        backRightMotor = hardwareMap.dcMotor.get("backRight");
        frontRightMotor = hardwareMap.dcMotor.get("frontRight");

        // trust me bro.
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        telemetry.addData("Status","wheels are good");
        lastTime = System.currentTimeMillis();
    }

    /*
        The difference between this and the move robot function is this is more smoothed,
        and is meant to be called with controller input. The reasoning is that using this with
        autonomous we are not constantly sending stop updates to the robot like with telop.
        This function is meant to be used when there is continuous movement update calls.
     */
    public void update(double x, double y, double rotate) {
        long currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - lastTime) / 1000.0;
        lastTime = currentTime;

        double maxDeltaY = maxRateY * deltaTime;
        double maxDeltaX = maxRateX * deltaTime;
        double maxDeltaRX = maxRateRX * deltaTime;

        yLimited += clip(y - yLimited, -maxDeltaY, maxDeltaY);
        xLimited += clip(x - xLimited, -maxDeltaX, maxDeltaX);
        rxLimited += clip(rotate - rxLimited, -maxDeltaRX, maxDeltaRX);

        move_robot(yLimited, xLimited, rxLimited);
    }

    /*
    so we can use this for autonomous without manually moving the wheels and with no smoothing
    between calls, because we do not need that for auto.
     */
    public void move_robot(double x, double y, double rotate) {
        /*
            this code is mostly from
            https://github.com/brandon-gong/ftc-mecanum
            very useful repo <3
         */

        // Normalize motor powers
        double denominator = Math.max(Math.abs(x) + Math.abs(y) + Math.abs(rotate), 1);

        double frontLeftPower = (x + y + rotate) / denominator;
        double frontRightPower = (y - x - rotate) / denominator;
        double backLeftPower = (y - x + rotate) / denominator;
        double backRightPower = (y + x - rotate) / denominator;

        // Apply power to motors
        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);


        /*
        this debug info is very useful. you can use it to see if the wheels are a programming
        issue or a build issue <3. Cross check with the diagram from the linked github to check
        if the wheel directions are correct.
         */
        telemetry.addData("WHEELS",
                String.format("front [left %f, right %f]", frontLeftPower, frontRightPower));

        telemetry.addData("WHEELS",
                String.format("front [left %f, right %f]", backLeftPower, backRightPower));
    }

    private double clip(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
