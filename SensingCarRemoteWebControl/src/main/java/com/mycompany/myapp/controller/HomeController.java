package com.mycompany.myapp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/")
	public String home(Model model) throws IOException {
		// model.addAttribute("cameraUrl",
		// "http://192.168.3.30:50001?action=stream");

		CoapClient coapClient = new CoapClient();
		JSONObject resJsonObject = null;
		CoapResponse coapResponse = null;
		String resJson = null;

		JSONObject reqJsonObject = new JSONObject();
		reqJsonObject.put("command", "status");
		String reqJson = reqJsonObject.toString();

		// -----------------------------------------------------------------------
		coapClient.setURI("coap://192.168.3.30/camera");
		coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		resJson = coapResponse.getResponseText();

		resJsonObject = new JSONObject(resJson);
		model.addAttribute("leftright", resJsonObject.getString("leftright"));
		model.addAttribute("updown", resJsonObject.getString("updown"));

		// -----------------------------------------------------------------------
		coapClient.setURI("coap://192.168.3.30/rgbled");
		coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		resJson = coapResponse.getResponseText();

		resJsonObject = new JSONObject(resJson);
		model.addAttribute("red", resJsonObject.getString("red"));
		model.addAttribute("green", resJsonObject.getString("green"));
		model.addAttribute("blue", resJsonObject.getString("blue"));

		// -----------------------------------------------------------------------
		coapClient.setURI("coap://192.168.3.30/laseremitter");
		coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		resJson = coapResponse.getResponseText();

		resJsonObject = new JSONObject(resJson);
		model.addAttribute("laseremitterStatus", resJsonObject.getString("status"));

		// -----------------------------------------------------------------------
		coapClient.setURI("coap://192.168.3.30/buzzer");
		coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		resJson = coapResponse.getResponseText();

		resJsonObject = new JSONObject(resJson);
		model.addAttribute("buzzerStatus", resJsonObject.getString("status"));

		// -----------------------------------------------------------------------
		coapClient.setURI("coap://192.168.3.30/ultrasonicsensor");
		coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		resJson = coapResponse.getResponseText();

		resJsonObject = new JSONObject(resJson);
		model.addAttribute("distance", resJsonObject.getString("distance"));
		model.addAttribute("ultrasonicsensorAngle", resJsonObject.getString("angle"));

		// -----------------------------------------------------------------------
		coapClient.setURI("coap://192.168.3.30/lcd");
		coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		resJson = coapResponse.getResponseText();

		resJsonObject = new JSONObject(resJson);
		model.addAttribute("lcdline0", resJsonObject.getString("line0"));
		model.addAttribute("lcdline1", resJsonObject.getString("line1"));

		// -----------------------------------------------------------------------
		coapClient.setURI("coap://192.168.3.30/fronttire");
		coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		resJson = coapResponse.getResponseText();

		resJsonObject = new JSONObject(resJson);
		model.addAttribute("fronttireAngle", resJsonObject.getString("angle"));

		// -----------------------------------------------------------------------
		coapClient.setURI("coap://192.168.3.30/backtire");
		coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		resJson = coapResponse.getResponseText();

		resJsonObject = new JSONObject(resJson);
		model.addAttribute("direction", resJsonObject.getString("direction"));
		model.addAttribute("speed", resJsonObject.getString("speed"));
		// -----------------------------------------------------------------------
		coapClient.shutdown();
		return "controlpanel";
	}

	@RequestMapping("/camera")
	public void camera(String command, String leftright, String updown, HttpServletResponse response)
			throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", command);
		jsonObject.put("leftright", leftright);
		jsonObject.put("updown", updown);
		String reqJson = jsonObject.toString();

		CoapClient coapClient = new CoapClient();
		coapClient.setURI("coap://192.168.3.30/camera");
		CoapResponse coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		String resJson = coapResponse.getResponseText();
		coapClient.shutdown();

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(resJson);
		pw.flush();
		pw.close();
	}

	@RequestMapping("/rgbled")
	public void rgbled(String command, String red, String green, String blue, HttpServletResponse response)
			throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", command);
		jsonObject.put("red", red);
		jsonObject.put("green", green);
		jsonObject.put("blue", blue);
		String reqJson = jsonObject.toString();

		CoapClient coapClient = new CoapClient();
		coapClient.setURI("coap://192.168.3.30/rgbled");
		CoapResponse coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		String resJson = coapResponse.getResponseText();
		coapClient.shutdown();

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(resJson);
		pw.flush();
		pw.close();
	}

	@RequestMapping("/laseremitter")
	public void laseremitter(String command, String status, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", command);
		jsonObject.put("status", status);
		String reqJson = jsonObject.toString();

		CoapClient coapClient = new CoapClient();
		coapClient.setURI("coap://192.168.3.30/laseremitter");
		CoapResponse coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		String resJson = coapResponse.getResponseText();
		coapClient.shutdown();

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(resJson);
		pw.flush();
		pw.close();
	}

	@RequestMapping("/buzzer")
	public void buzzer(String command, String status, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", command);
		jsonObject.put("status", status);
		String reqJson = jsonObject.toString();

		CoapClient coapClient = new CoapClient();
		coapClient.setURI("coap://192.168.3.30/buzzer");
		CoapResponse coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		String resJson = coapResponse.getResponseText();
		coapClient.shutdown();

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(resJson);
		pw.flush();
		pw.close();
	}

	@RequestMapping("/ultrasonicsensor")
	public void ultrasonicsensor(String command, String angle, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", command);
		jsonObject.put("angle", angle);
		String reqJson = jsonObject.toString();

		CoapClient coapClient = new CoapClient();
		coapClient.setURI("coap://192.168.3.30/ultrasonicsensor");
		CoapResponse coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		String resJson = coapResponse.getResponseText();
		coapClient.shutdown();

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(resJson);
		pw.flush();
		pw.close();
	}

	@RequestMapping("/lcd")
	public void lcd(String command, String line0, String line1, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", command);
		jsonObject.put("line0", line0);
		jsonObject.put("line1", line1);
		String reqJson = jsonObject.toString();

		CoapClient coapClient = new CoapClient();
		coapClient.setURI("coap://192.168.3.30/lcd");
		CoapResponse coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		String resJson = coapResponse.getResponseText();
		coapClient.shutdown();

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(resJson);
		pw.flush();
		pw.close();
	}

	@RequestMapping("/fronttire")
	public void fronttire(String command, String angle, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", command);
		jsonObject.put("angle", angle);
		String reqJson = jsonObject.toString();

		CoapClient coapClient = new CoapClient();
		coapClient.setURI("coap://192.168.3.30/fronttire");
		CoapResponse coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		String resJson = coapResponse.getResponseText();
		coapClient.shutdown();

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(resJson);
		pw.flush();
		pw.close();
	}
	
	@RequestMapping("/backtire")
	public void backtire(String command, String direction, String speed, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", command);
		jsonObject.put("direction", direction);
		jsonObject.put("speed", speed);
		String reqJson = jsonObject.toString();

		CoapClient coapClient = new CoapClient();
		coapClient.setURI("coap://192.168.3.30/backtire");
		CoapResponse coapResponse = coapClient.post(reqJson, MediaTypeRegistry.APPLICATION_JSON);
		String resJson = coapResponse.getResponseText();
		logger.info(resJson);
		coapClient.shutdown();

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(resJson);
		pw.flush();
		pw.close();
	}
}
