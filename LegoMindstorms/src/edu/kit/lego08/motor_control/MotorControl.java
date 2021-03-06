package edu.kit.lego08.motor_control;

import edu.kit.lego08.sensors.SensorUtils;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class MotorControl {

    private static DifferentialPilot pilot = new DifferentialPilot(6.88f, 12.0f, Motor.A, Motor.D);
    private double speed = 0;

    public MotorControl() {
        setFastSpeed();
        pilot.setAcceleration(60);
    }

    public void setFastSpeed() {
        pilot.setTravelSpeed(20);
    }

    public void setColorSpeed() {
        pilot.setTravelSpeed(30);
    }
    
    public void setSlowSpeed() {
        pilot.setTravelSpeed(6);
    }

    public void steerRight() {
        stop(true);
        pilot.steer(120);
    }

    public void steerLeft() {
        stop(true);
        pilot.steer(-120);
    }

    public void steerRightBackward() {
        stop(true);
        pilot.steerBackward(-110);
    }

    public void steerLeftBackward() {
        stop(true);
        pilot.steerBackward(200);
    }

    public void steer(int angle) {
        //stop(true);
        
        pilot.steer(angle);
    }

    public void turnRight() {
        stop(true);
        setSlowTurn();
        pilot.rotateLeft();
        resetTurnSpeed();
    }

    private void setSlowTurn() {
        // TODO Auto-generated method stub
        speed = pilot.getTravelSpeed();
        pilot.setTravelSpeed(10);
    }

    private void resetTurnSpeed() {
        pilot.setTravelSpeed(speed);
    }

    public void turnRight(int angle) {
        stop(true);
        setSlowTurn();
        pilot.rotate(2 * angle, true);
        resetTurnSpeed();
    }

    public void turnLeft() {
        stop(true);
        setSlowTurn();
        pilot.rotateRight();
        resetTurnSpeed();
    }

    public void resetGyro() {
        SensorUtils.resetGyro();
    }

    public void turnLeft(int angle) {
        // 5.95 is factor for how much the motors have to rotate to rotate the
        // roboter 1 degree
        stop(true);
        setSlowTurn();
        pilot.rotate(-2 * angle, true);
        resetTurnSpeed();
    }

    public void turnRightAndWait(int angle) {
        stop(true);
        setSlowTurn();
        SensorUtils.resetGyro();
        pilot.rotateLeft();
        while (Math.abs(SensorUtils.getGyroAngle()) < angle) {

        }
        stop(true);
        resetTurnSpeed();
    }

    public void turnLeftAndWait(int angle) {
        stop(true);
        setSlowTurn();
        SensorUtils.resetGyro();
        pilot.rotateRight();
        while (Math.abs(SensorUtils.getGyroAngle()) < angle) {

        }
        stop(true);
        resetTurnSpeed();
    }

    public void forward() {
        stop(true);

        pilot.forward();
    }

    public void forwardTimed(int millis, boolean waitForStop) {

        stop(true);
        pilot.forward();
        Delay.msDelay(millis);
        stop(waitForStop);
    }

    public void forwardDistance(double distance) {
        stop(true);
        pilot.travel(distance);
    }

    public void backwardDistance(double distance) {
        stop(true);
        pilot.travel(-distance);
    }

    public void backward() {
        stop(true);
        pilot.backward();

    }

    public void backwardTimed(int millis, boolean waitForStop) {
        stop(false);
        pilot.backward();
        Delay.msDelay(millis);
        stop(waitForStop);
    }

    public boolean isMoving() {
        Delay.msDelay(5);
        return pilot.isMoving();
    }

    public void stop(boolean waitForStop) {
        pilot.quickStop();
    }

}
