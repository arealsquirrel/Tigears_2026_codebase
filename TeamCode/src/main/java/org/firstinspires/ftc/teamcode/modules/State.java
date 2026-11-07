package org.firstinspires.ftc.teamcode.modules;
/**
 * USAGE:
 * extend State_Promise to implement those methods,
 * then instantialize State, then use an ExecutorService
 * or something like a threadpool to manage it and the other
 * simultaneous data collection functions required for this.
 */

abstract class State_Promise {
    abstract void go_to_watching_position();
    abstract boolean has_ball_collection_path();
    abstract void run_ball_collection();
    abstract boolean has_target_path();
    abstract void run_target_path();
    abstract boolean spindexer_has_more_space(); // can block on classification due to promise handed to run().
    abstract void shoot_ball(int i);
    abstract boolean can_shoot_ball_at(Turret.vec a); // only x and y should matter here since balls are on the ground.
    abstract Turret.vec path_endpoint();
    abstract boolean reached_target_point();
    abstract boolean has_path(); //expect to always be true since there are a LOT of balls
    abstract void mirror_watching_position();
    abstract boolean get_HIVE_state();
    abstract void set_watching_position(boolean hive_state);
}

public class State implements Runnable {
    public enum STATES {
        INIT(-1),
        WATCHING_POSITION(0),
        DETERMINING_PATH(1),
        COLLECTING_BALLS(2),
        RETURNING_TO_WATCH(6);
        final int id;
        STATES(int i) {
            id=i;
        }
    };
    public static STATES get(int i) {
        for (STATES s : STATES.values()) if (s.id == i) return s;
        throw new UnsupportedOperationException("bro we don't have this");
    }
    private State() { throw new UnsupportedOperationException("Use State(State_Promise ps)"); }
    State(State_Promise ps) { sp = java.util.Objects.requireNonNull(ps); } 
    static State_Promise sp;
    static boolean can_shoot;
    static int shot;
    /**
     * Writing this is like a crime against humanity,
     * but like we have literally no time :skull:
     * also pls respect invariants of code...
     * this is very much so a 'do-it-yourself'
     * skeleton class meant only for HLD.
     */
    public void run() {
        STATES s = get(-1);
        while (true) {
            switch (s) {
                case INIT:
                    s = STATES.INIT;
                    sp.go_to_watching_position();
                    s = STATES.WATCHING_POSITION;
                case WATCHING_POSITION:
                    // SHould be guaranteed that we can shoot from here?
                    if (sp.has_ball_collection_path()) sp.run_ball_collection();
                    else break;
                    s = STATES.DETERMINING_PATH;
                case DETERMINING_PATH:
                    if (sp.has_target_path()) sp.run_target_path();
                    else break;
                    s = STATES.COLLECTING_BALLS;
                case COLLECTING_BALLS:
                    // In this version since the turret approx might be a bit shit
                    // when its moving since we don't have time to fit the constant
                    // for shooting delay, it is imperative that the robot is still
                    // while shooting.
                    // Additional invariant: another thread will asynchronously determine
                    // whether we can shoot a spindexed ball at that position and
                    // if so, we will shoot it.
                    if (sp.reached_target_point()) if (can_shoot) {sp.shoot_ball(shot); can_shoot = false;}
                    if (!sp.spindexer_has_more_space() || !sp.has_path()) {
                        sp.set_watching_position(sp.get_HIVE_state());
                        s = STATES.RETURNING_TO_WATCH;
                        break;
                    }
                    sp.run_target_path();
                    break;
                case RETURNING_TO_WATCH:
                    sp.go_to_watching_position();
                    s = STATES.WATCHING_POSITION;
                    break;
            }
        }
    }
}
