<%@page import="com.example.models.Genero"%>
<%@page import="com.example.models.DetallesEstudiante"%>
<%@page import="com.example.models.Estudiante"%>
<%@page import="com.example.models.Facultad"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<style>
		.formulario {
			max-width: 500px;
		}
        .input-grande {
            width: 100%;         /* Se adapta al ancho de la pantalla */
        }
    </style>
    
<meta charset="UTF-8">
<title>Formulario de estudiante</title>
</head>

<body>
	<h1>Formulario de Alta/Modificación de estudiante</h1>
	<fieldset class="formulario">

	<%
		Estudiante estudiante = (Estudiante) request.getAttribute("estudiante");
		DetallesEstudiante detallesEstudiante = (DetallesEstudiante) request.getAttribute("detallesEstudiante");

		List<Facultad> facultades = (List<Facultad>) request.getAttribute("facultades");
		System.out.println("genero:   " + estudiante.genero());
	%>

		<legend>Formulario de Gestión de Estudiante</legend>
		<form action="<%= estudiante == null ? "AltaController" : "UpdateController"%>" method="post">
		    <input type="hidden" id="idEstudiante" name="idEstudiante" value="<%=estudiante.id() %>">
			<div><label for="nombre">Nombre: </label><input type="text" id="nombre" name="nombre" required placeholder="introducir nombre" value="<%=estudiante != null ? estudiante.nombre() : ""%>"></div><br>
			<div><label for="primerApellido">Primer Apellido: </label><input type="text" id="primerApellido" name="primerApellido" required placeholder="introducir primer apellido" value="<%=estudiante != null ? estudiante.primerApellido() : ""%>"></div><br>
			<div><label for="segundoApellido">Segundo Apellido: </label><input type="text" id="segundoApellido" name="segundoApellido" placeholder="introducir segundo apellido" value="<%=estudiante != null ? estudiante.segundoApellido() : ""%>"></div><br>
			<div><label for="fechaMatriculacion">Fecha de Matriculación: </label><input type="date" id="fechaMatriculacion" name="fechaMatriculacion" required placeholder="introducir fecha aquí!" value="<%=estudiante != null ? estudiante.fechaMatriculacion() : ""%>"></div><br>
			<div><label for="beca">Beca: </label><input type="text" id="beca" name="beca" required value="<%=estudiante != null ? String.format("%.2f", estudiante.beca()).replace(',', '.') : ""%>"></div><br>
			<div><fieldset><legend>Género</legend>
				<label for="hombre">Hombre: </label>
				<input type="radio" id="hombre" required name="genero" value="HOMBRE" <%=estudiante != null && estudiante.genero().name().equals("HOMBRE") ? "checked" : "" %>>
				<label for="mujer">Mujer: </label>
				<input type="radio" id="mujer" required name="genero" value="MUJER" <%=estudiante != null && estudiante.genero().name().equals("MUJER") ? "checked" : "" %>>
				<label for="otro">Otro: </label>
				<input type="radio" id="otro" required name="genero" value="OTRO" <%=estudiante != null && estudiante.genero().name().equals("OTRO") ? "checked" : "" %>>
			</fieldset></div><br>
			


			<div><label for="facultades">Facultades: </label>
			<select id="facultades" name="facultades" required>
			<option></option>
			<%
				for (Facultad facultad : facultades) {
			%>
				<option value= "<%=facultad.id()%>" 
				<%= estudiante != null && facultad.nombre().equals(detallesEstudiante.nombreFacultad()) ? "selected" : "" %>
				><%=facultad.nombre()%></option>
			<%
				}
			%>
			</select></div><br>
			<div><label for="numTotalAsignaturas">Número Total de Asignaturas: </label><input type="text" id="numTotalAsignaturas" name="numTotalAsignaturas" required value="<%=estudiante != null ? estudiante.numTotalAsignaturas() : "" %>"></div><br>
			
			<div><label for="correos">Correos: </label>
			<input type="text" id="correos" name="correos" class="input-grande" placeholder="uno o varios separados por (;)" value="<%=
			detallesEstudiante.direccionesCorreo() != null && !detallesEstudiante.direccionesCorreo().isEmpty() && !detallesEstudiante.direccionesCorreo().contains(null) ? String.join(";", detallesEstudiante.direccionesCorreo()) : "" %>">
			</div><br>
			<div><label for="telefonos">Números de teléfono: </label>
			<input type="text" id="telefonos" name="telefonos" class="input-grande" placeholder="uno o varios separados por (;)" value="<%=
			detallesEstudiante.numerosTelefono() != null && !detallesEstudiante.numerosTelefono().isEmpty() && !detallesEstudiante.numerosTelefono().contains(null) ? String.join(";", detallesEstudiante.numerosTelefono()): ""%>">
			</div><br>
			
			<input type="submit" value="Enviémos el formulario">
		</form>
		
		
	</fieldset>
</body>
</html>