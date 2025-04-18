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
        jobApplications = getJobApplicationsByUserId();
    }

    public void deleteUser(){
        deleteUserById(userID);
    }

    public void signUp(){
        addUser(username, password);
    }
    public void logOut(){

    }

    public void addJob(){
        
    }
}