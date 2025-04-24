package backend.classes;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class JobApplication {
    String company;
    int appId;
    String jobPosition;
    String jobDescription;
    Timestamp timeApplied;
    Long dueDate;
    String requirements;
    Boolean isPrivate;
    String userComments;
    Map<Integer, String> publicComments = new HashMap<>(); // (UserID to Comment)
    int authorID;
    String appStatus;
    int jobID;

    public JobApplication (
        String company, String jobPosition, int appId, Timestamp timeApplied, String appStatus
    ) {
        this.company = company;
        this.jobPosition = jobPosition;
        this.timeApplied = timeApplied;
        this.appStatus = appStatus;
        this.appId = appId;        
    }
}
