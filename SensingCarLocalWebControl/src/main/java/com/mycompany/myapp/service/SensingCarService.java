package com.mycompany.myapp.service;

public interface SensingCarService {
	public void changeUltrasonicSensorAngle(int angle);
	public int getUltrasonicSensorDistance();
	public double getGasSensorValue() throws Exception;
}
