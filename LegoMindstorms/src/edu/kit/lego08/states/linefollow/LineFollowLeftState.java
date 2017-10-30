package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;

public class LineFollowLeftState extends State {
    private static LineFollowLeftState instance = null;

    private LineFollowLeftState() {
        // States shall be used as singleton
    }

    public static LineFollowLeftState getInstance() {
        if (instance == null) {
            instance = new LineFollowLeftState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        //TODO: Move left
    }

    @Override
    public void onExit() {
        //TODO: Stop moving left
    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();
        LCD.clear();
        LCD.drawString("Linienfolgen: Links", 0, 5);

        if (!SensorUtils.isColorBlack()) {
            requestNextState(LineFollowRightState.getInstance());
        }
    }
}
