package edu.kit.lego08.sensors;

import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class SensorUtils {
    private static SensorModes ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S1);
    private static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
    private static EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);

    private SensorUtils() {
        // Utility classes should not be instantiated
    }

    public static boolean isTouchSonarPressed() {
        float[] sample = new float[touch.sampleSize()];
        touch.fetchSample(sample, 0);
        return sample[0] == 1;
    }

    public static boolean isKeyPressedAndReleased(Key k) {
        if (k.isDown()) {
            while (k.isDown()) {
                // Wait for button release
                Delay.msDelay(10);
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean isColorBackground() {
        int colorId = colorSensor.getColorID();
        LCD.drawString("Color" + colorId + " ", 0, 3);
        return colorId == Color.BLACK || colorId == Color.NONE || colorId == Color.BROWN || colorId == Color.DARK_GRAY;
    }

    public static boolean isColorLine() {
        int colorId = colorSensor.getColorID();
        LCD.drawString("Color" + colorId + " ", 0, 3);
        return colorId == Color.YELLOW || colorId == Color.WHITE || colorId == Color.LIGHT_GRAY || colorId == Color.BLUE;
    }

    public static boolean isColorMarker() {
        int colorId = colorSensor.getColorID();
        LCD.drawString("Color" + colorId + " ", 0, 3);
        return colorId == Color.RED || colorId == Color.MAGENTA || colorId == Color.PINK || colorId == Color.ORANGE;
    }

    public static int getColorId() {
        return colorSensor.getColorID();
    }

    public static float getDistance() {
        SampleProvider distance = ultrasonicSensor.getMode("Distance");
        float[] sample = new float[distance.sampleSize()];
        distance.fetchSample(sample, 0);
        return sample[0];
    }
}
