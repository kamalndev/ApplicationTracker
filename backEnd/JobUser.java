package classes;
import java.util.ArrayList;
import java.util.List;

public class JobUser {
    int userID;
    String username;
    String password;
    List<JobApplication> jobApplications = new ArrayList<>();

    public JobUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void login(){
        //To-do: Fill jobApplications with data;
        jobApplications = getJobApplicationsByUserId();
    }
    public void signUp(){
        addUser(username, password);
    }
    public void logOut(){

    }

    public void addJob(){
        
    }
}