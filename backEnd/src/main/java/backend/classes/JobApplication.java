package backend.classes;
import java.sql.Timestamp; 
import java.util.HashMap;
import java.util.Map;

public class JobApplication {
    int appId;
    String company;
    String jobPosition;
    String jobDescription;
//    Timestamp timeApplied;
    String dueDate;
    String requirements;
    String appStatus;
    String notes;
//    Boolean isPrivate;
//    String userComments;
//    Map<Integer, String> publicComments = new HashMap<>(); // (UserID to Comment)
//    int authorID;

    public JobApplication (
       int appId, String company, String jobPosition, String jobDescription, String dueDate, String requirements, String appStatus, String notes
    ) {
    	this.appId = appId;
        this.company = company;
        this.jobPosition = jobPosition;
        this.jobDescription = jobDescription;
        this.dueDate = dueDate;
        this.requirements = requirements;
        this.appStatus = appStatus;
        this.notes = notes;
    }
    public int getID() {
    	return this.appId;
    }
    
    public String getCompany() {
    	return this.company;
    }
    
    public String getJobPosition() {
    	return this.jobPosition;
    }
    
    public String getJobDescription() {
    	return this.jobDescription;
    }
    
    public String getDueDate() {
    	return this.dueDate;
    }
    
    public String getRequirements() {
    	return this.requirements;
    }
    
    public String getAppStatus() {
    	return this.appStatus;
    }
    
    public String getNotes() {
    	return this.notes;
    }
}
