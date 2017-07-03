package hardware.test;

import com.pi4j.io.gpio.RaspiPin;
import hardware.buzzer.ActiveBuzzer;
import hardware.sensor.UltrasonicSensor;

public class DistanceBuzzerTest {

    public static void main(String[] args) throws InterruptedException {
        ActiveBuzzer activeBuzzer = new ActiveBuzzer(RaspiPin.GPIO_02);
        UltrasonicSensor sensor = new UltrasonicSensor(RaspiPin.GPIO_00, RaspiPin.GPIO_01);

        while (true) {
            int distance = sensor.getDistance();
            if (distance <= 20) {
                activeBuzzer.on();
            }
                Thread.sleep(1000);
                activeBuzzer.off();
        }
    }

}
