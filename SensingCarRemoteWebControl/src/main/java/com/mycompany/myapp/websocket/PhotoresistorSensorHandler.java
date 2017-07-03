package com.mycompany.myapp.websocket;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import javax.annotation.PostConstruct;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class PhotoresistorSensorHandler extends TextWebSocketHandler implements ApplicationListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(PhotoresistorSensorHandler.class);

	private List<WebSocketSession> list = new Vector<>(); // 멀티 스레드 환경에서는
															// ArrayList가 아닌
															// Vector를 사용한다. (더
	private CoapClient coapClient;
	private CoapObserveRelation coapObserveRelation;

	@PostConstruct
	public void init() {
		coapClient = new CoapClient();
		coapClient.setURI("coap://192.168.3.30/photoresistorsensor");
		coapObserveRelation = coapClient.observe(new CoapHandler() {

			@Override
			public void onLoad(CoapResponse response) {
				String json = response.getResponseText();
				JSONObject jsonObject = new JSONObject(json);
				double doubleT = Double.parseDouble(jsonObject.getString("photoresistor"));
				double value = ((int) (doubleT * 10)) / 10.0;
				jsonObject = new JSONObject();
				jsonObject.put("time", getTimeUTC(new Date().getTime()));
				jsonObject.put("value", value);
				json = jsonObject.toString();
				try {
					for (WebSocketSession session : list) {
						session.sendMessage(new TextMessage(json));
					}
				} catch (Exception e) {
				}

			}

			@Override
			public void onError() {

			}
		});

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOGGER.info("실행");
		list.add(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		LOGGER.info("실행");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		LOGGER.info("실행");
		list.remove(session);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextClosedEvent) {
			coapObserveRelation.proactiveCancel();
			coapClient.shutdown();
		}
	}

	public long getTimeUTC(long localTime) {
		long utcTime = 0;
		TimeZone tz = TimeZone.getDefault();
		try {
			int offset = tz.getOffset(localTime);
			utcTime = localTime + offset;
		} catch (Exception e) {

		}
		return utcTime;
	}

}
