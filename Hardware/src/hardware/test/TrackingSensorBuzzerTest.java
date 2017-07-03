package hardware.test;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import hardware.buzzer.ActiveBuzzer;
import hardware.sensor.TrackingSensor;
import java.io.IOException;

public class TrackingSensorBuzzerTest {

    public static void main(String[] args) throws IOException {
        TrackingSensor ts = new TrackingSensor(RaspiPin.GPIO_00);
        ActiveBuzzer bz = new ActiveBuzzer(RaspiPin.GPIO_01);

        ts.setGpioPinListenerDigital((e) -> {
            if (e.getState() == PinState.LOW) {
                bz.on();
            } else {
                bz.off();
            }
        });

        System.out.println("Ready");
        System.in.read();

    }
}
