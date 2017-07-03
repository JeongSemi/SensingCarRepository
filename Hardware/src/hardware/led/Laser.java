package hardware.led;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Laser {
 //Field
    private GpioPinDigitalOutput laserPin;
    private String status = "off";

    //Constructor
    public Laser(Pin laserPinNo) {
        GpioController gpioController = GpioFactory.getInstance();
        laserPin = gpioController.provisionDigitalOutputPin(laserPinNo, PinState.HIGH);
        laserPin.setShutdownOptions(false, PinState.HIGH);
    }

    //Method
    public void on() {
        laserPin.low();
        status = "on";
    }

    public void off() {
        laserPin.high();
        status = "off";
    }

    public String getStatus() {
        return status;
    }

    public static void main(String[] args) throws InterruptedException {
        Laser test = new Laser(RaspiPin.GPIO_25);

        for (int i = 0; i < 5; i++) {
            test.on();
            Thread.sleep(1000);
            test.off();
            Thread.sleep(1000);
        }
    }
}
