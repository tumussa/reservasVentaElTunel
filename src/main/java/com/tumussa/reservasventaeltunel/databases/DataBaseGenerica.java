package com.tumussa.reservasventaeltunel.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseGenerica {

	protected String host;
	protected int port;
	protected String dataBase;
	protected String user;
	protected String pass;
	protected Connection cn;


	public static final String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
	public static final String DRIVER_SQL_SERVER = "com.odbc.oracle.SQLServer";


	protected DataBaseGenerica(){

	}

	public DataBaseGenerica(String host, int port, String dataBase, String user, String pass) {
		super();
		this.host = host;
		this.port = port;
		this.dataBase = dataBase;
		this.user = user;
		this.pass = pass;
	}

	/**
	 * Método que carga el driver especificado en DataBase.DRIVER_MYSQL en memoria.
	 * @return boolean - True si se cargó correctamente o false en caso contrario.
	 */
	public boolean loadDriver() {

		return this.loadDriver(this.DRIVER_MYSQL);
	}


	/**
	 * Método que carga un driver especificado.
	 * @param driver - String que representa la clase a cargar en  memoria que usará la BD.
	 * @return boolean - True si se cargó correctamente o false en caso contrario.
	 */
	public boolean loadDriver(String driver) {

		boolean load = false;
		try {
			Class.forName(driver);
			load = true;//Si no peta por la excepción y sigue ejecutando, es que se cargó correctamente.

		} catch (ClassNotFoundException e) {
			//Si peta por la excepción, no entra en el try y load sigue valiendo un false, por lo que devuelve ese valor.
		}
		return load;
	}




	/**
	 * Método que contruye la url de conexión a partir de los atributos del objeto.
	 * @return String - Cadena que devuelve la url de conexión a la base de datos.
	 */
	public String getURL() {
		return "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dataBase;
	}



	/**
	 * Método que establece una conexión con la base de datos especificada en los atributos de la clase.
	 * @return boolean - True si la conexión se estableció correctamente y false en caso contrario.
	 */
	public boolean createConnection() {
		boolean conectedOK = false;
		this.cn = null;
		try {
			this.cn = DriverManager.getConnection(this.getURL(), this.user, this.pass);
			conectedOK = true;
		} catch (SQLException e) {
			// Si peta no hacemos nada, devolvemos un false.
		}

		return  conectedOK;

	}

	/**
	 * Método que dice si tenemos una conexión establecida o no. (Para establecer una conexión tenemos que usar el
	 * método .createConnection()).
	 * @return boolean. True si hay conexión y false si la conexión es  null.
	 */
	public boolean isConnected() {
		return this.cn != null;
	}


	/**
	 * Método que ejecuta una sentecia de tipo INSERT, UPDATE o DELETE.
	 * @param sql - String con la consulta SQL.
	 * @param cn - Objeto de la clase Connection que ya tenga una conexión establecida.
	 * @return int - El número de filas afectadas por la consulta o -1 si se produjo un error.
	 */
	public int ejecutarUpdate(String sql) {

		Statement st;
		int filasAfectadas = -1;
		try {
			if (this.cn != null) {
				st = this.cn.createStatement();
				filasAfectadas = st.executeUpdate(sql);					
			}
		} catch (SQLException e) {
			//Devolvemos un -1 si falla, porque en la documentación de  mi método, establecemos que al devolver -1
			//es porque hubo un error al ejecutar la sentencia.
		}
		return filasAfectadas;

	}



	/**
	 * Método que ejecuta una instrucción SQL de tipo SELECT y devuelve el resultSet.
	 * @param sql - String con la consulta SQL de tipo SELECT.
	 * @return ResultSet - Resultado de la consulta SQL. Devolverá null si se produjo algún error.
	 */
	public ResultSet ejecutarQuery(String sql) {
		ResultSet rs = null;
		PreparedStatement pst = null;

		if (this.isConnected()) {
			try {
				pst = this.cn.prepareStatement(sql);
				rs = pst.executeQuery();
			} catch (SQLException e) {
				//TODO Quitar este mensaje cuando ya esté todo terminado...
				System.err.println("Error con la sonsulta sql. Excepción= " + e);
			}
		}

		return rs;
	}


	/**
	 * Método que se encarga de cerrar una conexión y capturar las excepciones que puedan darse.
	 * @return boolean - True si se cierra bien, false si no.
	 */
	public boolean closeConnection() {
		boolean todoCorrecto = false;
		try {
			this.cn.close();
			todoCorrecto = true;
		} catch (SQLException e) {

		}
		return todoCorrecto;
	}



	/**
	 * Método que convierte a String un objeto de clase ResultSet
	 * @param rs - ResultSet que queremos convertir a String
	 * @return String - Cadena que representa el {@link ResultSet}.
	 */
	public String rsToString(ResultSet rs) {
		String tabla="\n";
		try {
			//Construimos la cabecera
			int columnas = rs.getMetaData().getColumnCount();
			for (int i = 1; i <= columnas; i++) {
				tabla += rs.getMetaData().getColumnLabel(i) + "\t";
			}
			tabla += "\n-------------------------";
			//Ahora vamos a construir cada fila del resultSet recorriendo todas las columnas y después todas las filas.
			while (rs.next()) {
				String fila = "\n";
				for (int i = 1; i <= columnas; i++) {
					fila += rs.getString(i) + "\t";					
				}
				tabla += fila;
			}
			tabla+= "\n-------------------------";

		} catch (SQLException e) {

		}


		return tabla;
	}






}
