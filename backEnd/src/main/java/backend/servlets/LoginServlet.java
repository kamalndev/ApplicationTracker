package backend.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import backend.Database;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            LoginRequest loginRequest = gson.fromJson(request.getReader(), LoginRequest.class);

            String username = loginRequest.email;
            String password = loginRequest.password;

            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Username and password are required");
                return;
            }

            if (Database.conn == null) {
                Database.ConnectToDatabase();
            }

            boolean isAuthenticated = Database.checkUser(username, password);

            if (isAuthenticated) {
            	out.print("{\"success\": true}");
            } else {
            	out.print("{\"success\": false}");
            }

        } catch (JsonSyntaxException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON format: " + e.getMessage());
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");

        Map<String, Object> ErrorResponseMap = new HashMap<>();
        ErrorResponseMap.put("success", false);
        ErrorResponseMap.put("message", message);
        response.getWriter().print(new Gson().toJson(ErrorResponseMap));
    }

    private static class LoginRequest {
        private String email;
        private String password;
    }
}