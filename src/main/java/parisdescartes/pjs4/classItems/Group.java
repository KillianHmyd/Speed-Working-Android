package parisdescartes.pjs4.classItems;

import java.util.List;

/**
 * Created by rahman on 22/02/2016.
 */
public class Group {

    private Integer idGroup ;
    private Integer idUserLead ;
    private String GrpName, ProjectName ;
    private String Description ;
    private boolean endOfProject ;
    private List<Integer> UserList ;
    private Integer code ;
    private String msg ;

    public Group(Integer idGroup, Integer idUserLead, String grpName, String projectName, String description,
                 boolean endOfProject, List<Integer> userList, Integer code, String msg) {
        this.idGroup = idGroup;
        this.idUserLead = idUserLead;
        GrpName = grpName;
        ProjectName = projectName;
        Description = description;
        this.endOfProject = endOfProject;
        UserList = userList;
        this.code = code;
        this.msg = msg;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public Integer getIdUserLead() {
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

    public boolean isEndOfProject() {
        return endOfProject;
    }

    public List<Integer> getUserList() {
        return UserList;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
