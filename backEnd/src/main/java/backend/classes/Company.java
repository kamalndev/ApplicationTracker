package backend.classes;
import java.util.ArrayList;  
import java.util.List;
import java.util.Set;

import backend.Database;

import java.util.HashSet;

public class Company {
    String name;
    List<String> jobPositions;
    List<JobApplication> jobApps;
    int numApps = 0;

    public Company(String name) {
    	this.name = name;
    	this.jobPositions = new ArrayList<>();
    	this.jobApps = new ArrayList<>();
    }
    
	public String getName() {
		return this.name;
	}
	
	public List<String> getJobPositions() {
		return this.jobPositions;
	}

	public List<JobApplication> getJobApps() {
		return this.jobApps;
	}
	
	public int getNumApps() {
		return this.numApps;
	}
	
	public void addApplication(JobApplication app) {
	    if (app != null) {
	        jobApps.add(app);

	        String position = app.getJobPosition();
	        if (position != null && !position.isEmpty() && !jobPositions.contains(position)) {
	            jobPositions.add(position);
	        }

	        numApps = jobApps.size();
	    }
	}
}