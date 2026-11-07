package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class Turret {
    Servo turret_swivel;
    double position;
    Telemetry telemetry;
    Limelight3A limelight;
    private static final boolean REVERSE = true;      // flip if servo turns the wrong way
    public Turret(HardwareMap hardware_map, Telemetry telemetry) {
        this.telemetry = telemetry;
        turret_swivel = hardware_map.get(Servo.class, "turret_servo");
        limelight = hardware_map.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();

        double servoPos = 0.5;
        turret_swivel.setPosition(servoPos);

    }

    public void update() {
        // double pos = Math.abs(Math.sin(position += 0.0005));
        // turret_swivel.setPosition(Range.clip(pos, 0.0, 1.0));
        // telemetry.addData("position ", pos);

        LLResultTypes.FiducialResult tag = findTargetTag();
        if (tag != null) {
            double tx = tag.getTargetXDegrees(); // + = tag is to the right of crosshair
            double dir = REVERSE ? -1.0 : 1.0;

            position = 0.5 + dir * (tx / 180);
            position = Range.clip(position, 0, 1);
            turret_swivel.setPosition(position);

            telemetry.addData("Tag ID", tag.getFiducialId());
            telemetry.addData("tx (deg)", "%.2f", tx);
            telemetry.addData("ty (deg)", "%.2f", tag.getTargetYDegrees());
        } else {
            telemetry.addLine("No tag visible - holding position");
        }

        telemetry.addData("Servo pos", "%.3f", position);
        telemetry.update();
    }

    private LLResultTypes.FiducialResult findTargetTag() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return null;

        List<LLResultTypes.FiducialResult> tags = result.getFiducialResults();
        if (tags == null || tags.isEmpty()) return null;

        //if (TARGET_TAG_ID < 0) return tags.get(0);

        for (LLResultTypes.FiducialResult t : tags) {
            // return t;
            if (t.getFiducialId() == 30) return t;
        }
        return null;
    }

    /**
     * move this somewhere if u wanna.
     */
    static class tp {
        double theta,phi;
        tp(double t, double p) {
            theta = t;
            phi = p;
        }
    }
    
    static class vec {
        double x,y,z;
        vec(double X,double Y,double Z) {
            x=X;y=Y;z=Z;
        }
    }

    static vec add(vec a, vec b) {
        return new vec(a.x+b.x,a.y+b.y,a.z+b.z);
    }
    static vec sub(vec a, vec b) {
        return new vec(a.x-b.x,a.y-b.y,a.z-b.z);
    }
    static vec mult_scalar(vec a, double b) {
        return new vec(a.x*b, a.y*b, a.z*b);
    }
    static vec div_scalar(vec a, double b) {
        return new vec(a.x/b, a.y/b, a.z/b);
    }
    static double dot(vec a, vec b) {
        return (a.x*b.x+a.y*b.y+a.z*b.z);
    }
    static double mag(vec a) {
        return Math.sqrt(y2(a.x) + y2(a.y) + y2(a.z));
    }
    /**
     * since i keep doing ^2 and y is right under it...
     * @param a value
     * @return square of value
     */
    static double y2(double a) { return Math.pow(a,2); }
    //todo
    static vec cross(vec a, vec b) {
        throw new UnsupportedOperationException("goaway bro im not gonna do this yet");
        // return new vec(a.x-b.x,a.y-b.y,a.z-b.z);
    }

    //return nan if impossible.
    static final double g = 9.80665;
    static tp compute_rough_approx(vec p, vec t, vec v, double s) {
        vec d = sub(t,p);
        double T = mag(d)/s;
        for (int i = 0; i < 10; ++i) {
            vec u = add(sub(div_scalar(d, T),v),new vec(0,0,g*T/2));
            double f = dot(u,u) - y2(s);
            if (Math.abs(f) < 1e-6*s*s) break;
            vec du = add(div_scalar(d, -T*T),new vec(0,0,g/2));
            double fp = 2*dot(u,du);
            if (fp < 1e-9) return new tp(Double.NaN, Double.NaN);
            T = T - f/fp;
        }
        vec u = add(sub(div_scalar(d, T),v),new vec(0,0,g*T/2));
        if (Math.abs(dot(u,u) - y2(s)) > 1e-6*s*s) return new tp(Double.NaN, Double.NaN);

        return new tp(Math.atan2(u.y, u.x), Math.atan2(u.z,mag(new vec(u.x,u.y,0))));
    }
}
