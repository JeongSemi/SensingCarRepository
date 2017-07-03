package hardware.test;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import hardware.button.Button;
import hardware.motor.SG90Servo;
import java.io.IOException;

public class SG90ServoButtonTest {

    public static void main(String[] args) throws IOException {
        Button btn1 = new Button(RaspiPin.GPIO_00);
        Button btn2 = new Button(RaspiPin.GPIO_02);
        Button btn3 = new Button(RaspiPin.GPIO_03);
        SG90Servo servoMotor = new SG90Servo(RaspiPin.GPIO_01, 8, 27);

        btn1.setGpioPinListenerDigital(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState() == PinState.LOW) {
                    servoMotor.setAngle(0);
                }
            }
        });

        btn2.setGpioPinListenerDigital(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState() == PinState.LOW) {
                    servoMotor.setAngle(60);
                }
            }
        });

        btn3.setGpioPinListenerDigital(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if (event.getState() == PinState.LOW) {
                    servoMotor.setAngle(180);
                }
            }
        });

        System.out.println("Ready");
        System.in.read();
    }

}
