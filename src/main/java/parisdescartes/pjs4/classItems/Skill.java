package parisdescartes.pjs4.classItems;

/**
 * Created by Vocxtir on 21/03/2016.
 */
public class Skill {

    private Integer idSkill ;
    private String skillName ;
    private Integer code ;
    private String msg ;

    public Skill(Integer idSkill, String skillName) {
        this.idSkill = idSkill;
        this.skillName = skillName;
    }

    public Integer getIdSkill() {
        return idSkill;
    }

    public String getSkillName() {
        return skillName;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
