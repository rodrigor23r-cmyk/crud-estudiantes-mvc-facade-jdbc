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

		<legend>Formulario de Gestión de Estudiante</legend>
		<form action="AltaController" method="post">
			<div><label for="nombre">Nombre: </label><input type="text" id="nombre" name="nombre" required placeholder="introducir nombre"></div><br>
			<div><label for="primerApellido">Primer Apellido: </label><input type="text" id="primerApellido" name="primerApellido" required placeholder="introducir primer apellido"></div><br>
			<div><label for="segundoApellido">Segundo Apellido: </label><input type="text" id="segundoApellido" name="segundoApellido" placeholder="introducir segundo apellido"></div><br>
			<div><label for="fechaMatriculacion">Fecha de Matriculación: </label><input type="date" id="fechaMatriculacion" name="fechaMatriculacion" required placeholder="introducir fecha aquí!"></div><br>
			<div><label for="beca">Beca: </label><input type="text" id="beca" name="beca" required></div><br>
			<div><fieldset><legend>Género</legend>
				<label for="hombre">Hombre: </label>
				<input type="radio" id="hombre" required name="genero" value="HOMBRE">
				<label for="mujer">Mujer: </label>
				<input type="radio" id="mujer" required name="genero" value="MUJER">
				<label for="otro">Otro: </label>
				<input type="radio" id="otro" required name="genero" value="OTRO">
			</fieldset></div><br>
			

			<%
				List<Facultad> facultades = (List<Facultad>) request.getAttribute("facultades");
			%>
			<div><label for="facultades">Facultades: </label>
			<select id="facultades" name="facultades" required>
			<option></option>
			<%
				for (Facultad facultad : facultades) {
			%>
				<option value= "<%=facultad.id()%>"><%=facultad.nombre()%></option>
			<%
				}
			%>
			</select></div><br>
			<div><label for="numTotalAsignaturas">Número Total de Asignaturas: </label><input type="text" id="numTotalAsignaturas" name="numTotalAsignaturas" required></div><br>
			<div><label for="correos">Correos: </label>
			<input type="text" id="correos" name="correos" class="input-grande" placeholder="uno o varios separados por (;)">
			</div><br>
			<div><label for="telefonos">Números de teléfono: </label>
			<input type="text" id="telefonos" name="telefonos" class="input-grande" placeholder="uno o varios separados por (;)">
			</div><br>
			
			<input type="submit" value="Enviémos el formulario">
		</form>
		
		
	</fieldset>
</body>
</html>