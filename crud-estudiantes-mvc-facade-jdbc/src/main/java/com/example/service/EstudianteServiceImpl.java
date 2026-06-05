package com.example.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.example.dao.DBConexion;
import com.example.models.DetallesEstudiante;
import com.example.models.Estudiante;
import com.example.models.Genero;

public class EstudianteServiceImpl implements EstudianteService {

	private static final Logger LOG = Logger.getLogger("EstudianteServiceImpl");
	@Override
	public List<Estudiante> getEstudiantes() {
		
List<Estudiante> estudiantes = new ArrayList<Estudiante>();
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConexion();) {
			
			ResultSet rs = dbConexion.getEstudiantes(connection);
			
			while (rs.next()) {
				
				estudiantes.add(Estudiante.builder()
						.id(rs.getInt("id"))
						.nombre(rs.getString("nombre"))
						.primerApellido(rs.getString("primerApellido"))
						.segundoApellido(rs.getString("segundoApellido"))
						.fechaMatriculacion(rs.getDate("fechaMatriculacion").toLocalDate())
						.beca(new BigDecimal(rs.getDouble("beca")))
						.genero(Genero.valueOf(rs.getString("genero")))
						.facultades_Id(rs.getInt("facultades_Id"))
						.numTotalAsignaturas(rs.getInt("numTotalAsignaturas"))
						.build());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			LOG.severe("!!error al recuperar los estudiantes desde el servicio!!" + e.getMessage());

		}
		
		
		
		
		return estudiantes;
	}
	@Override
	public void altaEstudiante(Estudiante estudiante, List<String> direccionesCorreo, List<String> numerosTelefono) {
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConexion();) {
			
			dbConexion.altaEstudiante(estudiante, direccionesCorreo, numerosTelefono, connection);
			
		} catch (Exception e) {
			LOG.severe("!!error al dar de alta el estudiante desde el servicio!! " + e.getMessage());
		}
		
		
		
	}
	
	@Override
	public DetallesEstudiante getDetallesEstudiante(int idEstudiante) {

		DetallesEstudiante detallesEstudiante = null;
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConexion();) {
			
		
			
			ResultSet rs = dbConexion.getDetallesEstudiante(idEstudiante, connection);
			
			String nombreFacultad = null;
			Set<String> direccionesCorreo = new HashSet<String>();
			Set<String> numerosTelefono = new HashSet<String>();
			
			if (rs.next()) {
				
					nombreFacultad = rs.getString("nombre");
			}
			rs.beforeFirst();
			
			while (rs.next()) {
					
					direccionesCorreo.add(rs.getString("email"));
			}
			rs.beforeFirst();
			
			while (rs.next()) {
					numerosTelefono.add(rs.getString("numero"));
			}
			
			detallesEstudiante = new DetallesEstudiante(nombreFacultad, direccionesCorreo, numerosTelefono);
			
			LOG.info("Detalles del estudiante recuperados exitosamente desde el servicio: " + detallesEstudiante);
			
		} catch (Exception e) {
			LOG.severe("!!error al recuperar los detalles del estudiante desde el servicio!!" + e.getMessage());
			e.printStackTrace();
		}
		
		return detallesEstudiante;
	}
	
	
}
