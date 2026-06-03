package com.example.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.example.dao.DBConexion;
import com.example.models.Facultad;

public class FacultadServiceImpl implements FacultadService {

	private static Logger LOG = Logger.getLogger("FacultadServiceImpl");
	
	@Override
	public List<Facultad> getFacultades() {
		
		List<Facultad> facultades = new ArrayList<Facultad>();
		
		try (DBConexion dbConexion = new DBConexion("root", "Temp2026");
				Connection connection = dbConexion.getConexion();) {
			
			ResultSet rs = dbConexion.getFacultades(connection);
			
			while (rs.next()) {
				
				facultades.add(Facultad.builder()
						.id(rs.getInt("id"))
						.nombre(rs.getString("nombre"))
						.build());
			}
			
		} catch (Exception e) {
			
			LOG.severe("!!error al recuperar las facultades desde el servicio!! " + e.getMessage());

		}
		
		
		
		return facultades;
	}

}
