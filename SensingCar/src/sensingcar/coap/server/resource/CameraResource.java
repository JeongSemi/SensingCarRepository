package sensingcar.coap.server.resource;

import hardware.motor.PCA9685;
import hardware.motor.SG90ServoPCA9685Duration;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CameraResource extends CoapResource {
	//Field
	private static final Logger logger = LoggerFactory.getLogger(CameraResource.class);
	private SG90ServoPCA9685Duration leftRightMotor;
	private SG90ServoPCA9685Duration upDownMotor;
	private PCA9685 pca9685;
	private final int minLeftRight = 10;
	private final int maxLeftRight = 170;
	private final int minUpDown = 10;
	private final int maxUpDown = 100;
	private int currLeftRight;
	private int currUpDown;
					
	//Constructor
	public CameraResource() throws Exception {
		super("camera");
		pca9685 = PCA9685.getInstance();
		leftRightMotor = new SG90ServoPCA9685Duration(pca9685, PCA9685.PWM_14);
		upDownMotor = new SG90ServoPCA9685Duration(pca9685, PCA9685.PWM_15);
		turnLeftRight(90);
		turnUpDown(10);
	}
	
	//Method
	private void turnLeftRight(int angle) {
		if(angle < minLeftRight) angle = minLeftRight;
		if(angle > maxLeftRight) angle = maxLeftRight;
		leftRightMotor.setAngle(angle);
		currLeftRight = angle;
	}
	
	private void turnUpDown(int angle) {
		if(angle < minUpDown) angle = minUpDown;
		if(angle > maxUpDown) angle = maxUpDown;
		upDownMotor.setAngle(angle);
		currUpDown = angle;
	}
	
	@Override
	public void handleGET(CoapExchange exchange) {
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		//{ "command":"change", "leftright":"90", "updown":"10" }
		//{ "command":"status" }
		try {
			String requestJson = exchange.getRequestText();
			JSONObject requestJsonObject = new JSONObject(requestJson);
			String command = requestJsonObject.getString("command");
			if(command.equals("change")) {
				int leftright = Integer.parseInt(requestJsonObject.getString("leftright"));
				int updown = Integer.parseInt(requestJsonObject.getString("updown"));
				turnLeftRight(leftright);
				turnUpDown(updown);
			} else if(command.equals("status")) {
			}
			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put("result", "success");
			responseJsonObject.put("leftright", String.valueOf(currLeftRight));
			responseJsonObject.put("updown", String.valueOf(currUpDown));
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
