package classes;
import java.util.HashMap;
import java.util.Map;

public class JobApplication {
    Company company;
    int appId;
    String jobPosition;
    String jobDescription;
    Long timeApplied;
    Long dueDate;
    String requirements;
    Boolean isPrivate;
    String userComments;
    Map<int, String> publicComments = new HashMap<>(); // (UserID to Comment)
    int authorID;
    String appStatus;
    int jobID;

    public JobApplication (
        Company company, String jobPosition, String jobDescription, Long timeApplied, Long dueDate,
        String requirements, Boolean isPrivate, String appStatus, int jobID
    ) {
        this.company = company;
        this.jobPosition = jobPosition;
        this.jobDescription = jobDescription;
        this.timeApplied = timeApplied;
        this.dueDate = dueDate;
        this.requirements = requirements;
        this.isPrivate = isPrivate;
        this.appStatus = appStatus;
        this.jobID = jobID;

        setJobPosition(appId, jobPosition);
        setJobDescription(appId, jobDescription);
        setApplicationDeadline(appId, dueDate);
        setApplicationRequirements(appId, requirements);
        
    }
    
    public void updateStatus(String status) {
        appStatus = status;
        updateApplicationStatus(appId, status);
    }

    public void editComments() {
    }

    public void editJobDescription(String newDescription) {
    	
    	jobDescription = newDescription;
    	setJobDescription(appId, newDescription);
    }

    public void changePrivacy(boolean private) {
    	isPrivate = private;
    }

    public void addPublicComment(int userID){
        
    }
    
    public String getJobPosition() {
    	return jobPosition;
    }
}
