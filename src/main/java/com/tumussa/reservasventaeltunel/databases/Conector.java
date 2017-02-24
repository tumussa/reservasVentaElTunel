package com.tumussa.reservasventaeltunel.databases;

import java.util.ResourceBundle;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Conector {

	public static DriverManagerDataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		//Creamos un "usador" de recursos del archivo src/main/resources/db.properties
		ResourceBundle res = ResourceBundle.getBundle("db");
		
		dataSource.setDriverClassName(res.getString("jdbc-driver"));
		dataSource.setUrl(res.getString("jdbc-url"));
		dataSource.setUsername(res.getString("jdbc-user"));
		dataSource.setPassword(res.getString("jdbc-pass"));
		
		return dataSource;
	}
	
	
}
