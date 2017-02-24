package com.tumussa.reservasventaeltunel.databases;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseReservas extends DataBaseGenerica {

	public static final String BASE_DATOS = "reservas";
	
	public DataBaseReservas(){
		this.host = "localhost";
		this.port = 3306;
		this.dataBase = DataBaseReservas.BASE_DATOS;
		this.user = "root";
		this.pass = "";
	}
	
	
	public int addReserva(String nombre, String fecha, String hora, int comensales,String telefono){
		int filas = -1;
		String sql = "INSERT INTO reservas(nombre,fecha,hora,comensales,telefono) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement pst = cn.prepareStatement(sql);
			pst.setString(1, nombre);
			pst.setString(2, fecha);
			pst.setString(3, hora);
			pst.setInt(4, comensales);
			pst.setString(5,telefono);
			
			filas= pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Fallo en la sentecia SQL");
		}
				
		return filas;
	}
	
}
