package backend;
import java.sql.Connection;   
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;
import backend.classes.JobApplication;
import java.text.SimpleDateFormat;

public class Database {
	// Enter your mySQL password and username here in format private static String SQLUsername = "
	private static String SQLUsername = "root";
	private static String SQLPassword = "@Qwertyuioplm2005";
	public static Connection conn = null;
	public static PreparedStatement ps;
	public static Statement st;
	
	
public static int addUser(String username, String password) {
		String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int checkUser(String username, String password) {
		String sql = "SELECT user_id FROM Users WHERE username = ? AND password = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
	
			ResultSet rs = ps.executeQuery();
	
			if (rs.next()) {
				return rs.getInt("user_id");
			} else {
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void deleteUserById(int userId) {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Should i change this stuff to have Date just as a string instead?
	public static void addJobApplication(String company, String jobPosition, String jobDescription, java.sql.Date deadline, String requirements, String info, String status, int userId) {
		String sql = """
		INSERT INTO JobApplications (
		company, job_position, job_description, application_deadline,
		application_requirements, additional_info, application_status, user_id
		) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
		""";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, company);
			ps.setString(2, jobPosition);
			ps.setString(3, jobDescription);
			ps.setDate(4, deadline);
			ps.setString(5, requirements);
			ps.setString(6, info);
			ps.setString(7, status);
			ps.setInt(8, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static List<JobApplication> getJobApplicationsByUserId(int userId) {
        String sql = "SELECT * FROM JobApplications WHERE user_id = ?";
		List<JobApplication> apps = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                	int appId = rs.getInt("application_id");
                    String company = rs.getString("company");
                    String position = rs.getString("job_position");
                    String description = rs.getString("job_description");
                    java.sql.Date deadline = rs.getDate("application_deadline");
                    
                    String formattedDueDate = "";
                    if (deadline != null) {
                        formattedDueDate = sdf.format(deadline);
                    }
                    
                    String requirements = rs.getString("application_requirements");
                    String status = rs.getString("application_status");
                    String notes = rs.getString("additional_info");
                    
					JobApplication currApp = new JobApplication(appId, company, position, description, formattedDueDate, requirements, status, notes);
					apps.add(currApp);
                }
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return apps;

	}
	
	public static List<JobApplication> getJobApplicationsByCompanyName(String companyName) {
        String sql = "SELECT * FROM JobApplications WHERE LOWER(company) = LOWER(?)";
        List<JobApplication> companyApplications = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, companyName);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                	int appId = rs.getInt("application_id");
                    String company = rs.getString("company");
                    String position = rs.getString("job_position");
                    String description = rs.getString("job_description");
                    java.sql.Date deadline = rs.getDate("application_deadline");
                    
                    String formattedDueDate = "";
                    if (deadline != null) {
                        formattedDueDate = sdf.format(deadline);
                    }
                    
                    String requirements = rs.getString("application_requirements");
                    String status = rs.getString("application_status");
                    String notes = rs.getString("additional_info");
                    
					JobApplication currApp = new JobApplication(appId, company, position, description, formattedDueDate, requirements, status, notes);
					companyApplications.add(currApp);
                }
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return companyApplications;

	}
	
	public static List<JobApplication> getAllJobApps(){
	    List<JobApplication> applications = new ArrayList<>();
	    String sql = "SELECT * FROM JobApplications";

	    try (PreparedStatement ps = conn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery()) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            
	        while (rs.next()) {
            	int appId = rs.getInt("application_id");
                String company = rs.getString("company");
                String position = rs.getString("job_position");
                String description = rs.getString("job_description");
                java.sql.Date deadline = rs.getDate("application_deadline");
                
                String formattedDueDate = "";
                if (deadline != null) {
                    formattedDueDate = sdf.format(deadline);
                }
                
                String requirements = rs.getString("application_requirements");
                String status = rs.getString("application_status");
                String notes = rs.getString("additional_info");
                
				JobApplication currApp = new JobApplication(appId, company, position, description, formattedDueDate, requirements, status, notes);
				applications.add(currApp);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return applications;
	}
	public static void updateApplicationStatus(int appId, String newStatus) {
        String sql = "UPDATE JobApplications SET application_status = ? WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, appId);
            ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void deleteApplicationById(int appId) {
        String sql = "DELETE FROM JobApplications WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appId);
            ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public static String getCompany(int appId) {
        String sql = "SELECT company FROM JobApplications WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("company");
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public static void setCompany(int appId, String newCompany) {
        String sql = "UPDATE JobApplications SET company = ? WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newCompany);
            ps.setInt(2, appId);
            ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public static String getJobPosition(int appId) {
        String sql = "SELECT job_position FROM JobApplications WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("job_position");
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public static void setJobPosition(int appId, String newPosition) {
        String sql = "UPDATE JobApplications SET job_position = ? WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPosition);
            ps.setInt(2, appId);
            ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static String getJobDescription(int appId) {
        String sql = "SELECT job_description FROM JobApplications WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("job_description");
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public static void setJobDescription(int appId, String newDescription) {
        String sql = "UPDATE JobApplications SET job_description = ? WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newDescription);
            ps.setInt(2, appId);
            ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static java.sql.Date getApplicationDeadline(int appId) {
        String sql = "SELECT application_deadline FROM JobApplications WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDate("application_deadline");
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public static void setApplicationDeadline(int appId, java.sql.Date newDeadline) {
        String sql = "UPDATE JobApplications SET application_deadline = ? WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, newDeadline);
            ps.setInt(2, appId);
            ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static String getApplicationRequirements(int appId) {
        String sql = "SELECT application_requirements FROM JobApplications WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("application_requirements");
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public static void setApplicationRequirements(int appId, String requirements) {
        String sql = "UPDATE JobApplications SET application_requirements = ? WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, requirements);
            ps.setInt(2, appId);
            ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static String getAdditionalInfo(int appId) {
        String sql = "SELECT additional_info FROM JobApplications WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("additional_info");
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public static void setAdditionalInfo(int appId, String info) {
        String sql = "UPDATE JobApplications SET additional_info = ? WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, info);
            ps.setInt(2, appId);
            ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static int getUserId(int appId) {
        String sql = "SELECT user_id FROM JobApplications WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("user_id");
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1;
	}

	public static void setUserId(int appId, int userId) {
        String sql = "UPDATE JobApplications SET user_id = ? WHERE application_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, appId);
            ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public static Set<Integer> getJobIDsForCompany(String companyName){
	    Set<Integer> jobIDs = new HashSet<>();
        String sql = "SELECT application_id FROM JobApplications WHERE company = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, companyName);
            
            try (ResultSet rs = ps.executeQuery()) {
            	while(rs.next()) {
            		jobIDs.add(rs.getInt("application_id"));
            	}
            } 
        } catch (SQLException e) {
            e.printStackTrace();
          }
        
	    return jobIDs;
	}
	
	public static void ConnectToDatabase()
	{
		st = null;
		ps = null;

		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CSCI201_Final_Project", SQLUsername, SQLPassword);
	        String query = """
	                SELECT *
	                FROM Users
	            """;

	            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
	                System.out.printf("%-10s | %-18s%n", "Username", "Password");
	                System.out.println("-----------------------------");
	                while (rs.next()) {
	                    String username1 = rs.getString("username");
	                    String password1 = rs.getString("password");
	                    System.out.printf("%-18s | %-18s", username1, password1);
	                }
	            }
	            
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main (String[] args) throws ClassNotFoundException {
		ConnectToDatabase();
		//addUser("Claire", "test123");
		//deleteUserById(1);
		
	    String company = "Netflix";
	    String position = "Data Analyst Intern";
	    String description = "Analyze streaming data to optimize recommendations.";
	    java.sql.Date deadline = java.sql.Date.valueOf("2025-05-15");
	    String requirements = "Resume, Transcript, SQL test";
	    String additionalInfo = "Remote, Summer 2025 internship";
	    String status = "Not Submitted";
	    int userId = 6;

	    //addJobApplication(company, position, description, deadline, requirements, additionalInfo, status, userId);
		
		System.out.println(getCompany(1));
		// May need to move this to a different function
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
