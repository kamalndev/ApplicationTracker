package classes;
import java.util.ArrayList;
import java.util.List;

public class Company {
    String name;
    List<String> jobPositions = new ArrayList<>();
    List<JobApplication> jobApps = new ArrayList<>();
    int numRecordedApps;
    int numSuccessApps;

    public Company(String n){
        name = n;
    }

    public void addJobApplication(){

    }

    public void removeJobApplication(){

    }
}
