package org.ironriders.constants;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class Pivot {
    public static final double TOLERANCE = 0.5;
    public static final double GEARING = 500;
    public static final double ENCODER_OFFSET = 0;
    public static final int CURRENT_LIMIT = 20;

    public static final String DASHBOARD_PREFIX = "pivot/";

    public enum State {
        GROUND(0),
        AMP(0),
        SHOOTER(0);

        final int position;

        State(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

    public static class Limit {
        public static final float REVERSE = 0;
        public static final float FORWARD = 30;
    }

    public static class Control {

        public static final double P = 0.075;
        public static final double I = 0;
        public static final double D = 0;

        public static final TrapezoidProfile.Constraints PROFILE =
                new TrapezoidProfile.Constraints(70, 50);
    }
}