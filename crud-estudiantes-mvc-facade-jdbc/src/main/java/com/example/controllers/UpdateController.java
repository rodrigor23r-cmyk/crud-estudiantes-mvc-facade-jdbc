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
import java.util.logging.Logger;

import com.example.models.Facultad;
import com.example.models.Genero;
import com.example.models.DetallesEstudiante;
import com.example.models.Estudiante;
import com.example.service.FacultadService;
import com.example.service.FacultadServiceImpl;
import com.example.service.EstudianteService;
import com.example.service.EstudianteServiceImpl;


@WebServlet("/UpdateController")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger("UpdateController");

    public UpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idEstudiante = Integer.parseInt(request.getParameter("idEstudiante"));
		LOG.info("ID del estudiante: " + idEstudiante);
		EstudianteService estudianteServicio = new EstudianteServiceImpl();
		estudianteServicio.getDetallesEstudiante(idEstudiante);
		
		// mostrar la vista de detalles del estudiante, con los datos obtenidos del servicio
		List<Estudiante> estudiantes = estudianteServicio.getEstudiantes();
		
		Estudiante estudiante = estudiantes.stream()
				.filter(e -> e.id() == idEstudiante)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + idEstudiante));
		
		request.setAttribute("estudiante", estudiante);
		DetallesEstudiante detallesEstudiante = estudianteServicio.getDetallesEstudiante(idEstudiante);
		
		request.setAttribute("detallesEstudiante", detallesEstudiante);
		
		FacultadService facultadServicio = new FacultadServiceImpl();
		
		List<Facultad> facultades = null;
		// falta un try catch
		try {
			facultades = facultadServicio.getFacultades();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		request.setAttribute("facultades", facultades);
		
		request.getRequestDispatcher("views/formularioEstudiante.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int idEstudiante = Integer.parseInt(request.getParameter("idEstudiante"));
		String nombre = request.getParameter("nombre");
		String primerApellido = request.getParameter("primerApellido");
		String segundoApellido = request.getParameter("segundoApellido") == null ? "": request.getParameter("segundoApellido");
		LocalDate fechaMatriculacion = LocalDate.parse(request.getParameter("fechaMatriculacion"));
		Genero genero = Genero.valueOf(request.getParameter("genero"));
		BigDecimal beca = BigDecimal.valueOf(Double.valueOf(request.getParameter("beca")));
		int facultades_id = Integer.parseInt(request.getParameter("facultades"));
		int numTotalAsignaturas = Integer.parseInt(request.getParameter("numTotalAsignaturas"));
		List<String> direccionesCorreo = new ArrayList<>();
		List<String> numerosTelefono = new ArrayList<>();
				
		String cadenaCorreos = request.getParameter("correos").equals("") ? null : request.getParameter("correos");
		String cadenaTelefonos = request.getParameter("telefonos").equals("") ? null : request.getParameter("telefonos");
		
		if (cadenaCorreos != null) {
		    String[] arrayCorreos = cadenaCorreos.split(";");
		    direccionesCorreo = Arrays.asList(arrayCorreos);
		}
		
		if (cadenaTelefonos != null) {
		    String[] arrayTelefonos = cadenaTelefonos.split(";");
		    numerosTelefono =Arrays.asList(arrayTelefonos);		    
		}
		
		Estudiante estudiante = Estudiante.builder()
				.id(idEstudiante)
				.nombre(nombre)
				.primerApellido(primerApellido)
				.segundoApellido(segundoApellido) 
				.fechaMatriculacion(fechaMatriculacion)
				.beca(beca)
				.genero(genero) 
				.facultades_Id (facultades_id)
				.numTotalAsignaturas(numTotalAsignaturas)
				.build();
	
		
		// llamar a la capa de servicios para que llame a DAO para insertar el nuevo estudiante
		
		EstudianteService estudianteService = new EstudianteServiceImpl();
		
		try {
			estudianteService.modificarEstudiante(estudiante, direccionesCorreo, numerosTelefono);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// como se va a redirigir a la vista del listado de estudiantes, se recupera el listado actualizado de 
		// estudiantes para mostrarlo en la vista.
		List<Estudiante> estudiantes = estudianteService.getEstudiantes();
		request.setAttribute("estudiantes", estudiantes);
		
		 request.getRequestDispatcher("index.jsp").forward(request, response);
		// response.sendRedirect("index.jsp");
	}
}


