package com.mycompany.myapp.dao;

import java.util.List;

import com.mycompany.myapp.dto.GasSensor;

public interface GasSensorDao {
	public List<GasSensor> selectByAll();
	public int insert(GasSensor gasSensor);
}
