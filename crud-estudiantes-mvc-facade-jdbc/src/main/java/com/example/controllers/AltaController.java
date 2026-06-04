package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.models.Estudiante;
import com.example.models.Facultad;
import com.example.models.Genero;
import com.example.service.EstudianteService;
import com.example.service.EstudianteServiceImpl;
import com.example.service.FacultadService;
import com.example.service.FacultadServiceImpl;


@WebServlet("/AltaController")
public class AltaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AltaController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Se usa la interfaz como tipo y se instancia la clase implementadora
		FacultadService facultadServicio = new FacultadServiceImpl();
		
		List<Facultad> facultades = new ArrayList<Facultad>();
		// falta un try catch
		try {
			facultades = facultadServicio.getFacultades();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		request.setAttribute("facultades", facultades);
		
		request.getRequestDispatcher("views/formularioEstudiante.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String nombre = request.getParameter("nombre");
		String primerApellido = request.getParameter("primerApellido");
		String segundoApellido = request.getParameter("segundoApellido") == null ? "": request.getParameter("segundoApellido");
		LocalDate fechaMatriculacion = LocalDate.parse(request.getParameter("fechaMatriculacion"));
		BigDecimal beca = BigDecimal.valueOf(Double.valueOf(request.getParameter("beca")));
		Genero genero = Genero.valueOf(request.getParameter("genero"));
		int facultades_id = Integer.parseInt(request.getParameter("facultades"));
		int numTotalAsignaturas = Integer.parseInt(request.getParameter("numTotalAsignaturas"));
		
		List<String> direccionesCorreo = new ArrayList<>();
		List<String> numerosTelefono = new ArrayList<>();
				
		String cadenaCorreos = request.getParameter("correos");
		String cadenaTelefonos = request.getParameter("telefonos");
		
		if (cadenaCorreos != null ) {
		    String[] arrayCorreos = cadenaCorreos.split(";");
		    direccionesCorreo = Arrays.asList(arrayCorreos);
		}
		
		if (cadenaTelefonos != null ) {
		    String[] arrayTelefonos = cadenaTelefonos.split(";");
		    numerosTelefono =Arrays.asList(arrayTelefonos);		    
		}
		
		Estudiante estudiante = Estudiante.builder()
				.nombre(nombre)
				.primerApellido(primerApellido)
				.segundoApellido(segundoApellido) 
				.fechaMatriculacion(fechaMatriculacion) 
				.beca(beca)
				.genero(genero) 
				.facultades_Id(facultades_id)
				.numTotalAsignaturas(numTotalAsignaturas)
				.build();
	
		
		// llamar a la capa de servicios para que llame a DAO para insertar el nuevo estudiante
		
		EstudianteService estudianteServicio = new EstudianteServiceImpl();
		
		try {
			estudianteServicio.altaEstudiante(estudiante, direccionesCorreo, numerosTelefono);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// como se va a redirigir a la vista del listado de empleados, se recupera el listado actualizado de 
		// empleados para mostrarlo en la vista.
		List<Estudiante> estudiantes = estudianteServicio.getEstudiantes();
		request.setAttribute("estudiantes", estudiantes);
		
		//versión mala profe: request.getRequestDispatcher("index.jsp").forward(request, response);
		// versión buena Jeronimo: redirigir a la vista del listado de estudiantes para evitar el reenvío del formulario al refrescar la página
		response.sendRedirect("index.jsp");
		
	}

}
