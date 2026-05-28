package com.example.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record Estudiante(

			int id, 
			String nombre, 
			String primereApellido, 
			String segundoApellido,
			LocalDate fechaMatriculacion, 
			BigDecimal becas,
			Genero genero,
			int facultades_Idfacultades
			) {
		
	}
