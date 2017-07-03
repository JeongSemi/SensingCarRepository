package hardware.test;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import hardware.button.Button;
import hardware.buzzer.ActiveBuzzer;
import java.io.IOException;

public class ButtonActiveBuzzerTest {

    public static void main(String[] args) throws IOException {
        Button button = new Button(RaspiPin.GPIO_01);
        ActiveBuzzer activeBuzzer = new ActiveBuzzer(RaspiPin.GPIO_00);

        button.setGpioPinListenerDigital(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState() == PinState.LOW) {
                    activeBuzzer.on();
                }
                else{
                    activeBuzzer.off();
                }
            }
        });
        System.out.println("ready");
        System.in.read();
    }
}
