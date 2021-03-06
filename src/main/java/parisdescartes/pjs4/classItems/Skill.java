package parisdescartes.pjs4.classItems;

/**
 * Created by Vocxtir on 21/03/2016.
 */
public class Skill {

    private Integer idSkill ;
    private String nameSkill ;
    private Integer code ;
    private String message;

    public Skill(Integer idSkill, String skillName, Integer code, String message) {
        this.idSkill = idSkill;
        this.nameSkill = skillName;
        this.code = code;
        this.message = message;
    }

    public Integer getIdSkill() {
        return idSkill;
    }

    public String getSkillName() {
        return nameSkill;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
