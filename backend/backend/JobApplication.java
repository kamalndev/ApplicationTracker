package backend;
import java.util.HashMap;
import java.util.Map;

public class JobApplication {
    //Company company;
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
    
    public void updateStatus() {

    }

    public void editComments() {

    }

    public void editJobDescription() {

    }

    public void changePrivacy() {

    }

    public void addPublicComment(int userID){
        
    }
}
