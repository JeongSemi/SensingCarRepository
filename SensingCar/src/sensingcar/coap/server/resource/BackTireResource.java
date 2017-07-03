package sensingcar.coap.server.resource;

import com.pi4j.io.gpio.RaspiPin;
import hardware.motor.DCMotor;
import hardware.motor.PCA9685;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackTireResource extends CoapResource {
	//Field
	private static final Logger logger = LoggerFactory.getLogger(BackTireResource.class);
	private DCMotor dcMotorLeft;
	private DCMotor dcMotorRight;
	private PCA9685 pca9685;
	private final int maxStep = 4095;
	private final int minStep = 0;
	private int currStep;
	private String currDirection;
	
	//Constructor
	public BackTireResource() throws Exception {
		super("backtire");
		pca9685 = PCA9685.getInstance();
		dcMotorLeft = new DCMotor(RaspiPin.GPIO_00, RaspiPin.GPIO_01, pca9685, PCA9685.PWM_05);
		dcMotorRight = new DCMotor(RaspiPin.GPIO_02, RaspiPin.GPIO_03, pca9685, PCA9685.PWM_04);
		forward();
	}
	
	//Method
	public void forward() {
		dcMotorLeft.forward();
		dcMotorRight.forward();
		currDirection = "forward";
	}
	
	public void backward() {
		dcMotorLeft.backward();
		dcMotorRight.backward();
		currDirection = "backward";
	}

	public void setSpeed(int step) {
		if(step < minStep) step = minStep;
		if(step > maxStep) step = maxStep;
		currStep = step;
		dcMotorLeft.setSpeed(currStep);
		dcMotorRight.setSpeed(currStep);
	}
	
	public void stop() {
		dcMotorLeft.stop();
		dcMotorRight.stop();
	}

	@Override
	public void handleGET(CoapExchange exchange) {
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		//{ "command":"change", "direction":"forward", "speed":"1000" }
		//{ "command":"status" }
		try {
			String requestJson = exchange.getRequestText();
			JSONObject requestJsonObject = new JSONObject(requestJson);
			String command = requestJsonObject.getString("command");
			if(command.equals("change")) {
				String direction = requestJsonObject.getString("direction");
				int speed = Integer.parseInt(requestJsonObject.getString("speed"));
				if(direction.equals("forward")) {
					forward();
				} else if(direction.equals("backward")) {
					backward();
				}
				setSpeed(speed);
			} else if(command.equals("status")) {
			}
			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put("result", "success");
			responseJsonObject.put("direction", currDirection);
			responseJsonObject.put("speed", String.valueOf(currStep));
			String responseJson = responseJsonObject.toString();
			exchange.respond(responseJson);
		} catch(Exception e) {
			logger.info(e.toString());
			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put("result", "fail");
			String responseJson = responseJsonObject.toString();
			exchange.respond(responseJson);
		}
	}
}
