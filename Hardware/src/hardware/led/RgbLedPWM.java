package hardware.led;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class RgbLedPWM {

    //Field
    private GpioPinPwmOutput redPin;
    private GpioPinPwmOutput greenPin;
    private GpioPinPwmOutput bluePin;
    private int[] currColorSet = new int[3];

    public int[] getCurrColorSet() {
        return currColorSet;
    }

    //Constructor
    public RgbLedPWM(Pin redPinNo, Pin greenPinNo, Pin bluePinNo) {
        GpioController gpioController = GpioFactory.getInstance();
        //소프트웨어 PWM 출력핀 객체 생성
        redPin = gpioController.provisionSoftPwmOutputPin(redPinNo);
        greenPin = gpioController.provisionSoftPwmOutputPin(greenPinNo);
        bluePin = gpioController.provisionSoftPwmOutputPin(bluePinNo);

        //제어단계를 255단계로 설정
        redPin.setPwmRange(255);
        greenPin.setPwmRange(255);
        bluePin.setPwmRange(255);
        //발광하지 않도록 초기화
        ledColorSet(0, 0, 0);
    }

    //Method
    public void red(int value) {
        value = 255 - value;
        redPin.setPwm(value);
    }

    public void green(int value) {
        value = 255 - value;
        greenPin.setPwm(value);
    }

    public void blue(int value) {
        value = 255 - value;
        bluePin.setPwm(value);
    }

    public void ledColorSet(int r, int g, int b) {
        currColorSet[0] = r;
        currColorSet[1] = g;
        currColorSet[2] = b;

        r = 255 - r;
        g = 255 - g;
        b = 255 - b;

        redPin.setPwm(r);
        greenPin.setPwm(g);
        bluePin.setPwm(b);
    }

    public static void main(String[] args) throws Exception {
        RgbLedPWM test = new RgbLedPWM(RaspiPin.GPIO_04, RaspiPin.GPIO_05, RaspiPin.GPIO_06);

        test.ledColorSet(255, 0, 0);
        Thread.sleep(1000);
        test.ledColorSet(0, 255, 0);
        Thread.sleep(1000);
        test.ledColorSet(0, 0, 255);
        Thread.sleep(1000);
        test.ledColorSet(0, 255, 255);
        Thread.sleep(1000);
        test.ledColorSet(255, 0, 255);
        Thread.sleep(1000);
        test.ledColorSet(255, 255, 0);
        Thread.sleep(1000);

        test.ledColorSet(0, 0, 0);

        System.out.println("Ready");
        System.in.read();
    }

}
