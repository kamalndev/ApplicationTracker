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

@WebServlet("/api/getCompany")
public class GetCompanyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
   
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
    	
    }
    
    try {
        String appIdStr = request.getParameter("appId");
        if (appIdStr == null || appIdStr.isEmpty()) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Missing appId parameter");
            return;
        }
        
        int appId = Integer.parseInt(appIdStr);
        if (appId <= 0) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid appId parameter");
            return;
        }
        
        if (Database.conn == null) {
            Database.ConnectToDatabase();
        }
        
        String company = Database.getCompany(appId);
        
        Map<String, Object> companyMap = new HashMap<>();
        companyMap.put("name", company.getName());
        companyMap.put("jobPositions", company.getJobPositions());
        companyMap.put("jobApps", company.getJobApps());
        companyMap.put("numApps", company.getNumApps());
        
        out.print(gson.toJson(companyMap));
    	
    } catch (NumberFormatException e) {
        sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "appId must be an integer");
    } catch (Exception e) {
        sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        e.printStackTrace();
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
