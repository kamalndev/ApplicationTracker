package backend.servlets;

import java.io.IOException; 
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import backend.Database;
import backend.classes.*;

@WebServlet("/api/publicdashboard")
public class PublicDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            if (Database.conn == null) {
                Database.ConnectToDatabase();
            }
            
            List<JobApplication> allJobs = Database.getAllJobApps();
            
            Map<String, Company> companyMap = new HashMap<>();
            
            for (JobApplication app : allJobs) {
            	String companyName = app.getCompany();
            	
                if (companyName == null || companyName.isEmpty()) {
                    continue;
                }
                
                Company company = companyMap.get(companyName);
                if (company == null) {
                    company = new Company(companyName);
                    companyMap.put(companyName, company);
                }
                
                company.addApplication(app);
                                
            }
            
            List<Company> companies = new ArrayList<>(companyMap.values());

            out.print(gson.toJson(companies));
            
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
