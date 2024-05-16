package isw.ayudantia.medical;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/health")
public class HealthCheckServlet extends HttpServlet {

    @Resource(lookup = "java:/MedicalDS")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2000)) {
                resp.getWriter().write("Database connection is valid");
            } else {
                resp.getWriter().write("Database connection is not valid");
            }
        } catch (SQLException e) {
            resp.getWriter().write("Error checking database connection: " + e.getMessage());
        }
    }
}