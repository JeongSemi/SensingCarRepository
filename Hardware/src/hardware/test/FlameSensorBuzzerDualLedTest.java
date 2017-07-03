package hardware.test;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import hardware.buzzer.ActiveBuzzer;
import hardware.converter.PCF8591;
import hardware.led.DualColorLed;
import hardware.sensor.FlameSensor;

public class FlameSensorBuzzerDualLedTest {

    public static void main(String[] args) throws Exception {
        PCF8591 pcf8591 = new PCF8591(0x48, PCF8591.AIN0);
        FlameSensor test = new FlameSensor(pcf8591, RaspiPin.GPIO_00);
        DualColorLed led = new DualColorLed(RaspiPin.GPIO_04, RaspiPin.GPIO_05);
        ActiveBuzzer buzzer = new ActiveBuzzer(RaspiPin.GPIO_06);

        test.setGpioPinListenerDigital(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//                System.out.println("************************ "+event.getState()+" ****************************");
                if (event.getState() == PinState.LOW) {
                    buzzer.on();
                    led.red();
                } else {
                    buzzer.off();
                    led.green();
                }
            }
        });
        
        System.in.read();
        System.out.println("Ready");
    }

}
