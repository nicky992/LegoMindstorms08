package edu.kit.lego08.sensors;

import lejos.hardware.Key;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class SensorUtils {
    private static SensorModes ultrasonicSensor = null;
    private static EV3ColorSensor colorSensor = null;
    private static EV3TouchSensor touch = null;
    private static Thread initThread = null;
    private static boolean isInitComplete = false;

    private SensorUtils() {
        // Utility classes should not be instantiated
    }

    private static void init() {
        if (!isInitComplete) {
            if (initThread != null) {
                try {
                    initThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                initThread = new Thread() {
                    public void run() {
                        ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S1);
                        colorSensor = new EV3ColorSensor(SensorPort.S3);
                        touch = new EV3TouchSensor(SensorPort.S2);
                        isInitComplete = true;
                    }
                };
                initThread.start();
            }
        }
    }

    public static boolean isTouchSonarPressed() {
        init();
        float[] sample = new float[touch.sampleSize()];
        touch.fetchSample(sample, 0);
        return sample[0] == 1;
    }

    public static boolean isKeyPressedAndReleased(Key k) {
        if (k.isDown()) {
            Sound.playTone(500, 20);
            while (k.isDown()) {
                // Wait for button release
            }
            Sound.playTone(600, 20);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isColorBackground() {
        init();
        int colorId = colorSensor.getColorID();
        LCD.drawString("Color" + colorId + " ", 0, 3);
        return colorId == Color.BLACK || colorId == Color.NONE || colorId == Color.BROWN || colorId == Color.DARK_GRAY;
    }

    public static boolean isColorLine() {
        init();
        int colorId = colorSensor.getColorID();
        LCD.drawString("Color" + colorId + " ", 0, 3);
        return colorId == Color.YELLOW || colorId == Color.WHITE || colorId == Color.LIGHT_GRAY || colorId == Color.BLUE;
    }

    public static boolean isColorMarker() {
        init();
        int colorId = colorSensor.getColorID();
        LCD.drawString("Color" + colorId + " ", 0, 3);
        return colorId == Color.RED || colorId == Color.MAGENTA || colorId == Color.PINK || colorId == Color.ORANGE;
    }

    public static int getColorId() {
        init();
        return colorSensor.getColorID();
    }

    public static float getDistance() {
        init();
        SampleProvider distance = ultrasonicSensor.getMode("Distance");
        float[] sample = new float[distance.sampleSize()];
        distance.fetchSample(sample, 0);
        return sample[0];
    }
}
