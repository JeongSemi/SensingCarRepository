package sensingcar.coap.server.resource;

import hardware.motor.PCA9685;
import hardware.motor.SG90ServoPCA9685Duration;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontTireResource extends CoapResource {
	//Field
	private static final Logger logger = LoggerFactory.getLogger(FrontTireResource.class);
	private PCA9685 pca9685;
	private SG90ServoPCA9685Duration servoMotor;
	private final int maxAngle = 130;
	private final int minAngle = 50;
	private int currAngle;
	
	//Constructor
	public FrontTireResource() throws Exception {
		super("fronttire");
		pca9685 = PCA9685.getInstance();
		servoMotor = new SG90ServoPCA9685Duration(pca9685, PCA9685.PWM_00);
		setAngle(90);
	}
	
	//Method
	private void setAngle(int angle) {
		if(angle < minAngle) angle = minAngle;
		if(angle > maxAngle) angle = maxAngle;
		servoMotor.setAngle(angle);
		currAngle = angle;
	}
	
	@Override
	public void handleGET(CoapExchange exchange) {
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		//{ "command":"change", "angle":"90" }
		//{ "command":"status" }
		try {
			String requestJson = exchange.getRequestText();
			JSONObject requestJsonObject = new JSONObject(requestJson);
			String command = requestJsonObject.getString("command");
			if(command.equals("change")) {
				int angle = Integer.parseInt(requestJsonObject.getString("angle"));
				setAngle(angle);
			} else if(command.equals("status")) {
			}
			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put("result", "success");
			responseJsonObject.put("angle", String.valueOf(currAngle));
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
