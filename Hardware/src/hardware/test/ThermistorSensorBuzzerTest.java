package hardware.test;

import com.pi4j.io.gpio.RaspiPin;
import hardware.buzzer.ActiveBuzzer;
import hardware.converter.PCF8591;
import hardware.sensor.ThermistorSensor;

public class ThermistorSensorBuzzerTest {
    public static void main(String[] args) {
        ActiveBuzzer buzzer = new ActiveBuzzer(RaspiPin.GPIO_00);
        PCF8591 pcf8591 = new PCF8591(0x48, PCF8591.AIN0);
        ThermistorSensor sensor = new ThermistorSensor(pcf8591);
        
        while(true){
            
        }
    }

}
