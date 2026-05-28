package com.example.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.example.dao.DBConexion;
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
						.id(rs.getInt("idestudiantes"))
						.nombre(rs.getString("nombre"))
						.primereApellido(rs.getString("primereApellido"))
						.segundoApellido(rs.getString("segundoApellido"))
						.fechaMatriculacion(rs.getDate("fechaMatriculacion").toLocalDate())
						.becas(new BigDecimal(rs.getDouble("becas")))
						.genero(Genero.valueOf(rs.getString("genero")))
						.facultades_Idfacultades(rs.getInt("facultades_Idfacultades"))
						.build());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			LOG.severe("!!error al recuperar los estudiantes desde el servicio!!" + e.getMessage());

		}
		
		
		
		
		return estudiantes;
	}

}
