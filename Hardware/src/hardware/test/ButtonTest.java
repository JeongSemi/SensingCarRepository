package hardware.test;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import hardware.button.Button;
import hardware.led.DualColorLed;
import java.io.IOException;

public class ButtonTest {

    public static void main(String[] args) throws IOException {
        Button button = new Button(RaspiPin.GPIO_00);
        DualColorLed dcLed = new DualColorLed(RaspiPin.GPIO_02, RaspiPin.GPIO_01);

        dcLed.green();
        button.setGpioPinListenerDigital(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState() == PinState.LOW) {
                    dcLed.red();
                } else {
                    dcLed.green();
                }
            }
        });
        System.out.println("Ready");
        System.in.read();
    }
}
