package parisdescartes.pjs4.classItems;

/**
 * Created by rahman on 30/03/2016.
 */
public class OwnSkill {
    private Integer idUser ;
    private Integer idSkill ;

    private Integer code;
    private String message;

    public OwnSkill(Integer idSkill, Integer idUser) {
        this.idSkill = idSkill;
        this.idUser = idUser;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(Integer idSkill) {
        this.idSkill = idSkill;
    }
}
