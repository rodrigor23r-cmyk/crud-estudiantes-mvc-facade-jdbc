package com.example.service;

import java.util.List;

import com.example.models.DetallesEstudiante;
import com.example.models.Estudiante;

public interface EstudianteService {
	public abstract List<Estudiante> getEstudiantes();

	public abstract void altaEstudiante(Estudiante estudiante, List<String> direccionesCorreo,
			List<String> numerosTelefono);
	public abstract DetallesEstudiante getDetallesEstudiante(int idEstudiante);
}
