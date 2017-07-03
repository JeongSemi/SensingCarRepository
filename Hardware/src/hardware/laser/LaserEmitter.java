package hardware.laser;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 *
 * @author blueskii
 */
public class LaserEmitter {
    private GpioPinDigitalOutput laserPin;
    private String status = "off";

    public LaserEmitter(Pin laserPinNo) {
        GpioController gpioController = GpioFactory.getInstance();
        laserPin = gpioController.provisionDigitalOutputPin(laserPinNo);
        stop();
    }

    public void shot() {
        laserPin.low();
        status = "on";
    }

    public void stop() {
        laserPin.high();
        status = "off";
    }
    
    public String getStatus() {
        return status;
    }

    public static void main(String[] args) throws InterruptedException {
        LaserEmitter laserEmitter = new LaserEmitter(RaspiPin.GPIO_25);
        for (int i = 0; i < 5; i++) {
            laserEmitter.shot();
            Thread.sleep(3000);
            laserEmitter.stop();
            Thread.sleep(1000);
        }
    }
}
