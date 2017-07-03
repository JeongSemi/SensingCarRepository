package hardware.test;

import com.pi4j.io.gpio.RaspiPin;
import hardware.converter.PCF8591;
import hardware.led.RgbLedDigital;
import hardware.sensor.PhotoresistorSensor;

public class PhotoresistorSensorRgbTest {

    public static void main(String[] args) throws Exception {
        RgbLedDigital rgbLed = new RgbLedDigital(RaspiPin.GPIO_00, RaspiPin.GPIO_02, RaspiPin.GPIO_03);
        PCF8591 pcf8591 = new PCF8591(0x48, PCF8591.AIN0);
        PhotoresistorSensor sensor = new PhotoresistorSensor(pcf8591);

        
        while (true) {
            rgbLed.rgb(true, false, false);
            if (sensor.getValue() > 100) {
                rgbLed.rgb(false, true, false);
            }
            Thread.sleep(300);
        }
    }
}
