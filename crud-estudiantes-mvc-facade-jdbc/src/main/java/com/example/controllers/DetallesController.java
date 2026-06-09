package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.example.models.DetallesEstudiante;
import com.example.models.Estudiante;
import com.example.service.EstudianteService;
import com.example.service.EstudianteServiceImpl;


@WebServlet("/DetallesController")
public class DetallesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger("DetallesController");
	
    public DetallesController() {
        super();
        // TODO Auto-generated constructor stub
    }


    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idEstudiante = Integer.parseInt(request.getParameter("idEstudiante"));
		LOG.info("ID del estudiante: " + idEstudiante);
		EstudianteService estudianteServicio = new EstudianteServiceImpl();
		estudianteServicio.getDetallesEstudiante(idEstudiante);
		
		// mostrar la vista de detalles del empleado, con los datos obtenidos del servicio
		List<Estudiante> estudiantes = estudianteServicio.getEstudiantes();
		
		Estudiante estudiante = estudiantes.stream()
				.filter(e -> e.id() == idEstudiante)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + idEstudiante));
		
		request.setAttribute("estudiante", estudiante);
		DetallesEstudiante detallesEstudiante = estudianteServicio.getDetallesEstudiante(idEstudiante);
		
		request.setAttribute("detallesEstudiante", detallesEstudiante);
		request.getRequestDispatcher("views/detallesEstudiante.jsp").forward(request, response);
	}




	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
