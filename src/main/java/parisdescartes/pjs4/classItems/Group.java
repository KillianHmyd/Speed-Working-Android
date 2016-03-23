package parisdescartes.pjs4.classItems;

import java.util.List;

/**
 * Created by rahman on 22/02/2016.
 */
public class Group {

    private int idGroup ;
    private int idUserLead ;
    private String GrpName, ProjectName ;
    private String Description ;
    private boolean endOfProject ;
    private List<Integer> UserList ;

    public Group(int idGroup, int idUserLead, String grpName, String projectName, String description) {
        this.idGroup = idGroup;
        this.idUserLead = idUserLead;
        GrpName = grpName;
        ProjectName = projectName;
        Description = description;
        //UserList = new ArrayList<Integer>() ;
        //TODO Map<String, Boolean>
        //requiredSkills = new Hashtable<String,Boolean>() ;
    }


    public int getIdGroup() {
        return idGroup;
    }
    public int getIdUserLead() {
        return idUserLead;
    }
    public String getGrpName() {
        return GrpName;
    }
    public String getProjectName() {
        return ProjectName;
    }
    public String getDescription() {
        return Description;
    }
    public List<Integer> getUserList() {
        return UserList;
    }
    public boolean isEndOfProject() {
        return endOfProject;
    }
}
