package backend.servlets;

import java.io.IOException; 
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import backend.Database;
import backend.classes.JobApplication;

@WebServlet("/api/personaldashboard")
public class PersonalDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            String userIdStr = request.getParameter("userId");
            if (userIdStr == null || userIdStr.isEmpty()) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Missing userId parameter");
                return;
            }
            
            int userId = Integer.parseInt(userIdStr);
            if (userId <= 0) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid userId parameter");
                return;
            }
            
            if (Database.conn == null) {
                Database.ConnectToDatabase();
            }
            
            List<JobApplication> jobApplications = Database.getJobApplicationsByUserId(userId);
            
            List<Map<String, Object>> applicationsList = new ArrayList<>();
            for (JobApplication app : jobApplications) {
                Map<String, Object> appMap = new HashMap<>();
                appMap.put("id", app.getID());
                appMap.put("company", app.getCompany());
                appMap.put("job_position", app.getJobPosition());
                appMap.put("description", app.getJobDescription());
                appMap.put("date", app.getDueDate());
                appMap.put("requirements", Arrays.asList(app.getRequirements()));
                appMap.put("status", app.getAppStatus());
                appMap.put("notes", app.getNotes());
                applicationsList.add(appMap);
            }
            
            out.print(gson.toJson(applicationsList));
            
        } catch (NumberFormatException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "userId must be an integer");
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");

        Map<String, Object> errorResponseMap = new HashMap<>();
        errorResponseMap.put("success", false);
        errorResponseMap.put("message", message);
        response.getWriter().print(new Gson().toJson(errorResponseMap));
    }
}
