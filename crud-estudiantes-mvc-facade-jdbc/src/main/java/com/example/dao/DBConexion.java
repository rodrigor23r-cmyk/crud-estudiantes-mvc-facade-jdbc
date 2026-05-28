package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConexion implements AutoCloseable {
	
	private String username;
	private String password;
	
	// esta variable se inicializa con null:
	private Connection connection;
	// Logger para mostrar mensajes de conexión. println no es recomendable para mostrar mensajes de conexión, es mejor usar un logger porque si el servidor se cae,
	// el logger puede guardar los mensajes en un archivo de log para su posterior análisis, mientras que println solo muestra los mensajes en la consola y se perderían si el servidor se cae.
	private static final Logger LOG = Logger.getLogger("DBConexion");
	
	
	// contructor con dos parámetros
	public DBConexion(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	// Método que establece con la BD
	public Connection getConexion () throws ClassNotFoundException {
		
//		Si estas utilizanso SSL quizas debas añadir: empresa-crud-empleados?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
// probar cambiar localhost por 127.0.0.1 => "jdbc:mysql://127.0.0.1:3306/empresa-crud-empleados"
		
		String urlConexion = "jdbc:mysql://localhost:3306/universidad";
		Properties info = new Properties();
		
		info.put("user", this.username);
		info.put("password", this.password);
		
		
		try {
			// Cargar el driver de MySQL. Esto es necesario para que el DriverManager pueda encontrar el driver y establecer la conexión. Si no se carga el driver, 
			// el DriverManager no podrá establecer la conexión y lanzará una excepción.
			Class.forName("com.mysql.cj.jdbc.Driver"); // en la carpeta de librerías del proyecto, debe estar el conector de MySQL (mysql-connector-java-8.0.33.jar)
			// y dentro del conector, debe estar la clase com.mysql.cj.jdbc.Driver, que es la que se carga en esta línea de código.
			this.connection = DriverManager.getConnection(urlConexion, info);
			LOG.info("Conectado exitosamente desde DAO");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOG.info("!!error de conexión a la BD desde el DAO!!");
			e.printStackTrace();
		}
		
		return this.connection;
	}
	
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	// Método que recupera todos los registros de la tabla .
		public ResultSet getEstudiantes (Connection connection) {
			
			String query = "SELECT * FROM universidad.estudiantes";
			
			ResultSet rs = null;
			
			Statement stmt = null;
			
			try {
				stmt = connection.createStatement();
				
				rs = stmt.executeQuery(query);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			return rs;
		}
}
