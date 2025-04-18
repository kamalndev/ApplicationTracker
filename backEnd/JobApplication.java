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
    Map<Integer, String> publicComments = new HashMap<>(); // (UserID to Comment)
    int authorID;
    String appStatus;
    int jobID;

    public JobApplication (
        Company company, String jobPosition, int appId, Long timeApplied, String appStatus
    ) {
        this.company = company;
        this.jobPosition = jobPosition;
        this.timeApplied = timeApplied;
        this.appStatus = appStatus;
        this.appID = appID;

        setCompany(appId, company);
        setJobPosition(appId, jobPosition);
        setJobDescription(appId, jobDescription);
        setApplicationDeadline(appId, dueDate);
        setApplicationRequirements(appId, requirements);
        
    }
    
    public void deleteApplication(){
        deleteApplicationById(appId);
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

    public void changePrivacy(boolean priv) {
    	isPrivate = priv;
    }

    public void addPublicComment(int userID){
        
    }
    
    public String getJobPosition() {
    	return jobPosition;
    }
}
