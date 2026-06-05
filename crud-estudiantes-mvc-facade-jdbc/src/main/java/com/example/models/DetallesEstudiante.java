package com.example.models;

import java.util.Set;

import lombok.Builder;

@Builder
public record DetallesEstudiante(String nombreFacultad, Set<String> direccionesCorreo, Set<String> numerosTelefono) {

}
