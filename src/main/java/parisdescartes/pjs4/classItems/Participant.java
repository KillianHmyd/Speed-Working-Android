package parisdescartes.pjs4.classItems;

/**
 * Created by Killian on 28/03/2016.
 */
public class Participant {
    private Integer idUser;
    private Integer idConv;

    public Participant(Integer idUser, Integer idConv) {
        this.idUser = idUser;
        this.idConv = idConv;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Integer getIdConv() {
        return idConv;
    }
}
