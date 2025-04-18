package classes;
import java.util.ArrayList; 
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Company {
    String name;
    Set<String> jobPositions = new HashSet<>();
    Set<Int> jobApps = new HashSet<>();
    int numRecordedApps;
    int numSuccessApps;

    public Company(String n){
        name = n;
        jobApps = getJobIDsForCompany(n);
        for (Integer jobID : jobApps) {
        	jobPositions.add(getJobPosition(jobID));
        }
    }

    public void addJobApplication(Int newApp){
    	jobApps.add(newApp);
    	jobPositions.add(getJobPosition(newApp));
    }

    public void removeJobApplication(Int removeApp){
    	jobApps.remove(removeApp);
    }
}
