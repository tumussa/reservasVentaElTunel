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
	 * M�todo que carga el driver especificado en DataBase.DRIVER_MYSQL en memoria.
	 * @return boolean - True si se carg� correctamente o false en caso contrario.
	 */
	public boolean loadDriver() {

		return this.loadDriver(this.DRIVER_MYSQL);
	}


	/**
	 * M�todo que carga un driver especificado.
	 * @param driver - String que representa la clase a cargar en  memoria que usar� la BD.
	 * @return boolean - True si se carg� correctamente o false en caso contrario.
	 */
	public boolean loadDriver(String driver) {

		boolean load = false;
		try {
			Class.forName(driver);
			load = true;//Si no peta por la excepci�n y sigue ejecutando, es que se carg� correctamente.

		} catch (ClassNotFoundException e) {
			//Si peta por la excepci�n, no entra en el try y load sigue valiendo un false, por lo que devuelve ese valor.
		}
		return load;
	}




	/**
	 * M�todo que contruye la url de conexi�n a partir de los atributos del objeto.
	 * @return String - Cadena que devuelve la url de conexi�n a la base de datos.
	 */
	public String getURL() {
		return "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dataBase;
	}



	/**
	 * M�todo que establece una conexi�n con la base de datos especificada en los atributos de la clase.
	 * @return boolean - True si la conexi�n se estableci� correctamente y false en caso contrario.
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
	 * M�todo que dice si tenemos una conexi�n establecida o no. (Para establecer una conexi�n tenemos que usar el
	 * m�todo .createConnection()).
	 * @return boolean. True si hay conexi�n y false si la conexi�n es  null.
	 */
	public boolean isConnected() {
		return this.cn != null;
	}


	/**
	 * M�todo que ejecuta una sentecia de tipo INSERT, UPDATE o DELETE.
	 * @param sql - String con la consulta SQL.
	 * @param cn - Objeto de la clase Connection que ya tenga una conexi�n establecida.
	 * @return int - El n�mero de filas afectadas por la consulta o -1 si se produjo un error.
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
			//Devolvemos un -1 si falla, porque en la documentaci�n de  mi m�todo, establecemos que al devolver -1
			//es porque hubo un error al ejecutar la sentencia.
		}
		return filasAfectadas;

	}



	/**
	 * M�todo que ejecuta una instrucci�n SQL de tipo SELECT y devuelve el resultSet.
	 * @param sql - String con la consulta SQL de tipo SELECT.
	 * @return ResultSet - Resultado de la consulta SQL. Devolver� null si se produjo alg�n error.
	 */
	public ResultSet ejecutarQuery(String sql) {
		ResultSet rs = null;
		PreparedStatement pst = null;

		if (this.isConnected()) {
			try {
				pst = this.cn.prepareStatement(sql);
				rs = pst.executeQuery();
			} catch (SQLException e) {
				//TODO Quitar este mensaje cuando ya est� todo terminado...
				System.err.println("Error con la sonsulta sql. Excepci�n= " + e);
			}
		}

		return rs;
	}


	/**
	 * M�todo que se encarga de cerrar una conexi�n y capturar las excepciones que puedan darse.
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
	 * M�todo que convierte a String un objeto de clase ResultSet
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
			//Ahora vamos a construir cada fila del resultSet recorriendo todas las columnas y despu�s todas las filas.
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
