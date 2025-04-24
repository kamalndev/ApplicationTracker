package backEnd.servlets.auth;

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
import backEnd.DatabaseConnection.src.Database;

@WebServlet("/api/register")
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
            RegisterRequest registerRequest = gson.fromJson(request.getReader(), RegisterRequest.class);

            String username = registerRequest.username;
            String password = registerRequest.password;

            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Username and password are required");
                return;
            }

            if (Database.conn == null) {
                Database.ConnectToDatabase();
            }

            Database.addUser(username, password);


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

    private static class RegisterRequest {
        private String username;
        private String password;
    }
}
