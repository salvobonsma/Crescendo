package org.ironriders.constants;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class Pivot {
    public static final double TOLERANCE = 0.5;
    public static final double ENCODER_OFFSET = 150;
    public static final int CURRENT_LIMIT = 40;

    public static final String DASHBOARD_PREFIX = "pivot/";

    public enum State {
        GROUND(30),
        AMP(30),
        LAUNCHER(246);

        final int position;

        State(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

    public static class Control {
        public static final double P = 0.02;
        public static final double I = 0;
        public static final double D = 0;

        public static final TrapezoidProfile.Constraints PROFILE =
                new TrapezoidProfile.Constraints(300, 280);
    }
}
