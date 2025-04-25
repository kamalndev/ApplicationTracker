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

@WebServlet("/api/applications")
public class AddApplicationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            JobApplicationRequest appRequest = gson.fromJson(request.getReader(), JobApplicationRequest.class);
            int userId = appRequest.user_id;
            if (userId <= 0) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
                return;
            }
            
            if (Database.conn == null) {
                Database.ConnectToDatabase();
            }
            
            String requirementsStr JobApplicationRequest.;
            
            Date date = null;
            try {
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                java.util.Date parsedDate = format.parse(appRequest.application_deadline);
                date = new Date(parsedDate.getTime());
            } catch (Exception e) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, 
                    "Invalid date format. Use MM/DD/YYYY");
                return;
            }
            
            if (Database.conn == null) {
                Database.ConnectToDatabase();
            }
            
            Database.addJobApplication(
                appRequest.company,
                appRequest.jobPosition,
                appRequest.jobDescription,
                date,
                requirementsStr,
                appRequest.notes,
                appRequest.status,
                userId
            );
            
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", true);
            responseMap.put("message", "Application added successfully");
            
            out.print(gson.toJson(responseMap));
            
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
    
   
    private static class JobApplicationRequest {
        private int application_id;
        private String company;
        private String job_position;
        private String job_description;
        private String application_deadline;
        private String application_requirements;
        private String additional_info;
        private String application_status;
        private S
        private int user_id;
    }
}
