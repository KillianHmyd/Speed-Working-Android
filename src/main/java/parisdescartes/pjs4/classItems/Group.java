package parisdescartes.pjs4.classItems;

import java.util.ArrayList;

/**
 * Created by rahman on 22/02/2016.
 */
public class Group {

    private Integer idGroup ;
    private Integer idLeader;
    private String nameGroup;
    private String presentation;
    private boolean finish;
    private ArrayList<Contributor> contributors;
    private Integer idConv;
    private Integer code ;
    private String message;

    public Group(Integer idGroup, String grpName, String description, Integer idUserLead,
                 boolean endOfProject, ArrayList<Contributor> contributors, Integer idConv, Integer code, String msg) {
        this.idGroup = idGroup;
        this.idLeader = idUserLead;
        nameGroup = grpName;
        presentation = description;
        this.finish = endOfProject;
        this.contributors = contributors;
        this.code = code;
        this.message = msg;
        this.idConv = idConv;
    }

    public Group(String nameGroup, String presentation) {
        this.nameGroup = nameGroup;
        this.presentation = presentation;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public Integer getIdLeader() {
        return idLeader;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public String getPresentation() {
        return presentation;
    }

    public boolean isFinish() {
        return finish;
    }

    public ArrayList<Contributor> getContributors() {
        return contributors;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setContributors(ArrayList<Contributor> contributors) {
        this.contributors = contributors;
    }

    public Integer getIdConv() {
        return idConv;
    }
}
