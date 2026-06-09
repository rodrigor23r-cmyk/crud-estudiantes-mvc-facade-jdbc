package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.example.service.EstudianteService;
import com.example.service.EstudianteServiceImpl;


@WebServlet("/DeleteController")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int idEstudiante = Integer.parseInt(request.getParameter("idEstudiante"));
		
		// conectar con la capa de servicios
		EstudianteService estudianteService = new EstudianteServiceImpl();
		// Eliminar el empleado con el id obtenido del request
		estudianteService.deleteEstudiante(idEstudiante);
		
		response.sendRedirect("index.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
