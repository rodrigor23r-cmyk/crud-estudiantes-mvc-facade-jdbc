<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.models.Estudiante" %>
<%@ page import="com.example.service.EstudianteService" %>
<%@ page import="com.example.service.EstudianteServiceImpl" %>
<!DOCTYPE html>
<html>
<head>
<style>
  table {
    width: 100%;
    border-collapse: collapse; /* Evita que las líneas se vean dobles */
  }
  th, td {
    border: 1px solid #ccd1d1; /* Grosor, estilo (sólido) y color de la raya */
    padding: 8px;              /* Espacio interno para que no se pegue el texto */
    text-align: left;
  }
  th {
    background-color: #f2f2f2; /* Color de fondo para los encabezados */
  }
</style>
<meta charset="UTF-8">
<title>Universidad</title>
</head>
<body>
	<h1>Bienvenido a la Universidad</h1>

	<h1>Listado de estudiantes</h1>

<%-- No lo puedo hacer con un Servlet; en su lugar lo hago con un JSP. El JSP actúa como controlador para este ejemplo. --%>
<%
	// Se usa la interfaz como tipo y se instancia la clase implementadora
	EstudianteService service = new EstudianteServiceImpl();
	List<Estudiante> estudiantes = service.getEstudiantes();
%>

	<table>
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Primer Apellido</th>
				<th>Segundo Apellido</th>
				<th>Fecha de matriculación</th>
				<th>Beca</th>
				<th>Genero</th>
			</tr>
		</thead>
		<tbody>
			<!-- Recorremos la lista de empleados y mostramos sus datos en la tabla -->
        	<% for (Estudiante estudiante : estudiantes) { %>
                <tr>
			<td><%= estudiante.nombre() %></td>
            <td><%= estudiante.primereApellido() %></td>
            <td><%= estudiante.segundoApellido() != null ? estudiante.segundoApellido() : "" %></td>
            <td><%= estudiante.fechaMatriculacion() %></td>
            <td><%= estudiante.becas() %></td>
            <td><%= estudiante.genero() %></td>
                </tr>
          <% } %>
          <!--  tr>td{<=empleado.() %>}*6 -->
		</tbody>
	</table>


</body>
</html>