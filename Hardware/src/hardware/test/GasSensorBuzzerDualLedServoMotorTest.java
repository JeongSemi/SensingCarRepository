package hardware.test;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import hardware.buzzer.ActiveBuzzer;
import hardware.converter.PCF8591;
import hardware.led.DualColorLed;
import hardware.motor.SG90Servo;
import hardware.sensor.GasSensor;

public class GasSensorBuzzerDualLedServoMotorTest {

    public static void main(String[] args) throws Exception {
        PCF8591 pcf8591 = new PCF8591(0x48, PCF8591.AIN0);
        GasSensor test = new GasSensor(pcf8591, RaspiPin.GPIO_00);
        DualColorLed led = new DualColorLed(RaspiPin.GPIO_04, RaspiPin.GPIO_05);
        ActiveBuzzer buzzer = new ActiveBuzzer(RaspiPin.GPIO_06);
        SG90Servo servoMotor = new SG90Servo(RaspiPin.GPIO_01, 8, 27);

//        test.setGpioPinListenerDigital(new GpioPinListenerDigital() {
//            @Override
//            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
////                System.out.println("************************ "+event.getState()+" ****************************");
//                if (event.getState() == PinState.LOW) {
//                    buzzer.on();
//                    led.red();
//                    servoMotor.setAngle(180);
//                } else {
//                    buzzer.off();
//                    led.green();
//                    servoMotor.setAngle(0);
//                }
//            }
//        });
//
//        System.out.println("Ready");
//        System.in.read();
        while (true) {
            int value = test.getValue();
            System.out.println(value);
            if (value > 200) {
                buzzer.on();
                led.red();
                servoMotor.setAngle(180);
            } else {
                buzzer.off();
                led.green();
                servoMotor.setAngle(0);
            }
            Thread.sleep(1000);
        }
    }
}
