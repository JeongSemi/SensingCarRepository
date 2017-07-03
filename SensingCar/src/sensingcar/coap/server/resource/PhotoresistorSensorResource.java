package sensingcar.coap.server.resource;

import hardware.converter.PCF8591;
import hardware.sensor.PhotoresistorSensor;
import hardware.sensor.ThermistorSensor;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhotoresistorSensorResource extends CoapResource {
	//Field
	private static final Logger logger = LoggerFactory.getLogger(PhotoresistorSensorResource.class);
	private PCF8591 pcf8591;
	private PhotoresistorSensor photoresistorSensor;
	private double currValue;
	
	//Constructor
	public PhotoresistorSensorResource() throws Exception {
		super("photoresistorsensor");
		setObservable(true);
		getAttributes().setObservable();
		setObserveType(CoAP.Type.NON);
		
		pcf8591 = new PCF8591(0x48, PCF8591.AIN0);
		photoresistorSensor = new PhotoresistorSensor(pcf8591);
		
		Thread thread = new Thread() {
			@Override
			public void run() {
				while(true) {
					try {
						currValue = photoresistorSensor.getValue();
						changed();
						Thread.sleep(1000);
					} catch(Exception e) {
						logger.info(e.toString());
					}
				}
			}
		};
		thread.start();
	}
	
	//Method
	@Override
	public void handleGET(CoapExchange exchange) {
		JSONObject responseJsonObject = new JSONObject();
		responseJsonObject.put("photoresistor", String.valueOf(currValue));
		String responseJson = responseJsonObject.toString();
		exchange.respond(responseJson);
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		//{ "command":"status" }
		try {
			String requestJson = exchange.getRequestText();
			JSONObject requestJsonObject = new JSONObject(requestJson);
			String command = requestJsonObject.getString("command");
			if(command.equals("status")) {
			}
			JSONObject responseJsonObject = new JSONObject();
			responseJsonObject.put("result", "success");
			responseJsonObject.put("photoresistor", String.valueOf(currValue));
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
