package com.example.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.example.models.Estudiante;

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
		
		public ResultSet getFacultades(Connection connection) {
			
			ResultSet rs = null;
			String query = "SELECT * FROM universidad.facultades";
			
			Statement stmt = null;
			
			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				LOG.info("Facultades recuperadas exitosamente desde el DAO" + rs);
			} catch (SQLException e) {
				LOG.severe("Error recuperando departamentos porque: " + e.getMessage());
				e.printStackTrace();
			}
			
			return rs;
		}

		public void altaEstudiante(Estudiante estudiante, List<String> direccionesCorreo, List<String> numerosTelefono,
				Connection connection2) throws SQLException {
			

			String query1 = "INSERT INTO estudiantes (`nombre`, `primerApellido`, `segundoApellido`, `fechaMatriculacion`, `beca`, `genero`, `facultades_Id`, `numTotalAsignaturas`)"
					+ " VALUES(?,?,?,?,?,?,?,?)";
			// Sentencias preparadas: prepared statement. Como los procedimientos almacenados
			String query2 = "INSERT INTO correos (`email`, `estudiantes_id`) VALUES (?,?)";
			String query3 = "INSERT INTO telefonos (`numero`, `estudiantes_id`) VALUES (?,?)";
			
			// todo debe hacerse en el marco de una transacción.
			try {
				// iniciar transacción
				connection.setAutoCommit(false);
				
				PreparedStatement stmt1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
				
				stmt1.setString(1, estudiante.nombre());
				stmt1.setString(2, estudiante.primerApellido());
				stmt1.setString(3, estudiante.segundoApellido());
				stmt1.setDate(4, Date.valueOf(estudiante.fechaMatriculacion()));
				stmt1.setDouble(5, estudiante.beca().doubleValue());
				stmt1.setString(6, estudiante.genero().name());
				stmt1.setInt(7, estudiante.facultades_Id());
				stmt1.setInt(8, estudiante.numTotalAsignaturas());
				// total de filas afectadas por la ejecución de la sentencia SQL. Si es 0, no se ha insertado ningún registro, 
				// lo que indica que ha habido un error en la inserción.
				int totalFilas = stmt1.executeUpdate();
				
				if (totalFilas != 0) {
					// recuperar el id del estudiante recién insertado para usarlo en las tablas de correos y teléfonos
					long lastInsertedId = 0L;
					ResultSet rs = stmt1.getGeneratedKeys();
					if (rs.next()) { //es obligatorio llamar a next() para posicionar el cursor en el primer registro del ResultSet, que es donde se encuentra el id generado. Si no se llama a next(), el cursor estará antes del primer registro y no se podrá recuperar el id.
						lastInsertedId = rs.getLong(1);
					}
					
					if (direccionesCorreo != null && direccionesCorreo.size() > 0) {
						
						PreparedStatement stmt2 = connection.prepareStatement(query2);

						stmt2.setInt(2, Math.toIntExact(lastInsertedId));

	// esto es ineficiente porque se ejecuta una sentencia SQL por cada correo electrónico, lo que puede generar una gran cantidad de sentencias SQL si el estudiante tiene muchos correos electrónicos. Además, cada ejecución de la sentencia SQL implica una comunicación con la base de datos, lo que puede ralentizar el proceso de inserción.
//						for (String correo : direccionesCorreo) {
//							stmt2.setString(1, correo);
//							stmt2.executeUpdate();
//						}
						
						for(String correo : direccionesCorreo) {
							stmt2.setString(1, correo);
							stmt2.addBatch();
						}
						stmt2.executeBatch();
					}
					
						if (numerosTelefono != null && numerosTelefono.size() > 0) {
						
						PreparedStatement stmt3 = connection.prepareStatement(query3);

						stmt3.setInt(2, Math.toIntExact(lastInsertedId));
						
						for(String telefono : numerosTelefono) {
							stmt3.setString(1, telefono);
							stmt3.addBatch();
						}
						stmt3.executeBatch();
					}

					
					
				}
				
				connection.commit();
				
			} catch (Exception e) {
				LOG.severe("Error en la transacción de alta de estudiante porque: " + e.getMessage());
				e.printStackTrace();
				connection.rollback();
				LOG.info("Transacción de alta de estudiante revertida");
			} finally {
				connection.setAutoCommit(true);
			}
			
		}

		public ResultSet getDetallesEstudiante(int idEstudiante, Connection connection) {
			
			ResultSet rs = null;
			
			String query = "SELECT f.nombre, c.email, t.numero FROM estudiantes e \r\n"
					+ "					LEFT JOIN facultades f ON e.facultades_id = f.id \r\n"
					+ "					LEFT JOIN correos c ON e.id = c.estudiantes_id \r\n"
					+ "					LEFT JOIN telefonos t ON e.id = t.estudiantes_id \r\n"
					+ "					WHERE e.id = ?";
			
			PreparedStatement stmt = null;
			
			try {
				stmt = connection.prepareStatement(query, 
						ResultSet.TYPE_SCROLL_INSENSITIVE, 
						ResultSet.CONCUR_UPDATABLE); 
				
				stmt.setInt(1, idEstudiante);
				
				rs = stmt.executeQuery();
		
				
			} catch (Exception e) {
				LOG.severe("!!error al recuperar los detalles del estudiante desde el DAO!! " + e.getMessage());
				e.printStackTrace();
			}
			return rs;
		}
}
