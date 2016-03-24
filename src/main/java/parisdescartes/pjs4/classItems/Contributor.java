package parisdescartes.pjs4.classItems;

/**
 * Created by Killian on 24/03/2016.
 */
public class Contributor {
    private Integer idUser;
    private Integer idGroup;

    public Contributor(Integer idUser, Integer idGroup) {
        this.idUser = idUser;
        this.idGroup = idGroup;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Integer getIdGroup() {
        return idGroup;
    }
}
