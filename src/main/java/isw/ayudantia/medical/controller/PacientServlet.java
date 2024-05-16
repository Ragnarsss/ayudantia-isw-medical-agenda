package isw.ayudantia.medical.controller;

import com.google.gson.Gson;


import isw.ayudantia.medical.dao.PacientDao;
import isw.ayudantia.medical.model.Pacient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;


@WebServlet(value = {"/pacient", "/pacients"})
public class PacientServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final PacientDao pacientDao = new PacientDao();
    private final Logger logger = Logger.getLogger(PacientServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getRequestURI().equals("/pacient")) {
            String rut = req.getParameter("rut");
            PacientDao pacientDao = new PacientDao();
            Pacient pacient = pacientDao.getPacient(rut);

            if (pacient == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Pacient not found.");

            }

            String pacientJsonString = this.gson.toJson(pacient);

            PrintWriter out = resp.getWriter();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(pacientJsonString);
            out.flush();
        } else if (req.getRequestURI().equals("/pacients")) {
            PacientDao pacientDao = new PacientDao();
            List<Pacient> pacients = List.of(pacientDao.getPacients());

            String pacientsJsonString = this.gson.toJson(pacients);

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(pacientsJsonString);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get pacient data from request
            String rut = req.getParameter("rut");
            String nombre = req.getParameter("nombre");
            String apellido = req.getParameter("apellido");
            String email = req.getParameter("email");
            Integer edad = Integer.parseInt(req.getParameter("edad"));
            String sexo = req.getParameter("sexo");

            // Create new pacient
            Pacient pacient = new Pacient.Builder()
                    .rut(rut)
                    .nombre(nombre)
                    .apellido(apellido)
                    .email(email)
                    .edad(edad)
                    .sexo(sexo)
                    .build();

            // Save pacient in database
            PacientDao pacientDao = new PacientDao();
            Pacient savedPacient = pacientDao.savePacient(pacient);

            //check if pacient was saved correctly
            if (savedPacient != null) {
                // Send response
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Pacient created successfully.");
            }

        } catch (Exception e) {
            // Handle error
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }
}