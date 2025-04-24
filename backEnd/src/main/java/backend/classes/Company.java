package backend.classes;
import java.util.ArrayList; 
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Company {
    String name;
    Set<String> jobPositions = new HashSet<>();
    Set<Integer> jobApps = new HashSet<Integer>();
    int numRecordedApps;
    int numSuccessApps;
}
