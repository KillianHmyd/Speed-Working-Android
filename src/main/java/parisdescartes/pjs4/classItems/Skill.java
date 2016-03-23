package parisdescartes.pjs4.classItems;

/**
 * Created by Vocxtir on 21/03/2016.
 */
public class Skill {

    private int idSkill ;
    private String skillName ;

    public Skill(String skillName, int idSkill) {
        this.skillName = skillName;
        this.idSkill = idSkill;
    }

    public int getIdSkill() {
        return idSkill;
    }
    public String getSkillName() {
        return skillName;
    }
}
