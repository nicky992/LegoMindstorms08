package edu.kit.lego08.states.bridge;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SonarService;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import edu.kit.lego08.states.linefollow.TurnLeftState;
import edu.kit.lego08.states.linefollow.TurnRightState;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class BridgeForwardState extends State {
    private static BridgeForwardState instance = null;
    private SonarService sonarService;
    private MotorControl motorControl = new MotorControl();

    private BridgeForwardState() {
        // States shall be used as singleton
    }

    public static BridgeForwardState getInstance() {
        if (instance == null) {
            instance = new BridgeForwardState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        sonarService = new SonarService();
        sonarService.start();

        LCD.clear();
        LCD.drawString("State: Bruecke", 0, 5);
    }

    @Override
    public void onExit() {
        sonarService.stopService();
    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();

        motorControl.forward(1000);
        Delay.msDelay(1000);

        if (sonarService.getDistance(3) > 0.2) {
            if (sonarService.getDistance(1) < 0.2) {
                requestNextState(BridgeLeftState.getInstance());
                Sound.playTone(500, 400);
            } else if (sonarService.getDistance(5) < 0.2) {
                requestNextState(BridgeRightState.getInstance());
                Sound.playTone(500, 400);
            }
        }
    }
}
