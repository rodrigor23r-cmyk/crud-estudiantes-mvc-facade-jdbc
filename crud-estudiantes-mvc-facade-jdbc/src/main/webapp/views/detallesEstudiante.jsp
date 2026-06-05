<%@page import="com.example.models.DetallesEstudiante"%>
<%@page import="com.example.models.Estudiante"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalles del estudiante</title>
</head>
<body>
<%
	Estudiante estudiante = (Estudiante) request.getAttribute("estudiante");
	DetallesEstudiante detallesEstudiante = (DetallesEstudiante) request.getAttribute("detallesEstudiante");
%>
<h1>Detalles del estudiante</h1>
<p>Nombre: <%=estudiante.nombre()%></p>
<p>Apellidos: <%=estudiante.primerApellido() %> <%=estudiante.segundoApellido()%></p>
<p>Género: <%=estudiante.genero().name() %></p>
<p>Fecha de matriculación: <%=estudiante.fechaMatriculacion()%></p>
<p>Beca: <%=estudiante.beca()%></p>
<p>Número de asignaturas: <%=estudiante.numTotalAsignaturas()%></p>
<p>Facultad: <%=detallesEstudiante.nombreFacultad()%></p>

<div>
		<h3>Teléfonos:</h3>
		<ul>
            <% for (String numero : detallesEstudiante.numerosTelefono()) { %>
                <li><%=numero%></li>
            <% } %>
        </ul>
	</div>
	
	<div>
		<h3>Correos:</h3>
		<ul>
            <% for (String email : detallesEstudiante.direccionesCorreo()) { %>
                <li><%=email%></li>
            <% } %>
        </ul>
	</div>

</body>
</html>