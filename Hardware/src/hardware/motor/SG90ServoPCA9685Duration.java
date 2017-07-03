package hardware.motor;

import com.pi4j.io.gpio.Pin;

public class SG90ServoPCA9685Duration {

    //Field
    private PCA9685 pca9685;
    private Pin pin;
    private int angle;
    private int minDuration;
    private int maxDuration;

    //Constructor
    public SG90ServoPCA9685Duration(PCA9685 pca9685, Pin pin) {

        //0도(0.8ms): 800us
        //180도(2.7ms): 2700us
        this(pca9685, pin, 800, 2700);
    }

    public SG90ServoPCA9685Duration(PCA9685 pca9685, Pin pin, int minDuration, int maxDuration) {
        this.pca9685 = pca9685;
        this.pin = pin;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }
    //Method

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        if (angle < 0) {
            angle = 0;
        } else if (angle > 180) {
            angle = 180;
        }
        this.angle = angle;
        int duration = minDuration + (int) (angle * (maxDuration - minDuration) / 180.0);
        this.pca9685.setDuration(pin, duration);
    }

    public int getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    public static void main(String[] args) throws Exception {
        PCA9685 pca9685 = PCA9685.getInstance();
        SG90ServoPCA9685Duration servo = new SG90ServoPCA9685Duration(pca9685, PCA9685.PWM_00);

        for (int i = 10; i <= 170; i += 10) {
            servo.setAngle(i);
            Thread.sleep(500);
        }
        for (int i = 170; i >= 10; i -= 10) {
            servo.setAngle(i);
            Thread.sleep(500);
        }
        servo.setAngle(180);
    }
}
