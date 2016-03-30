package parisdescartes.pjs4.classItems;

/**
 * Created by Killian on 30/03/2016.
 */
public class ServiceAddUserToGroup {
    private int idUser;
    private int idGroup;

    public ServiceAddUserToGroup(int idUser, int idGroup) {
        this.idUser = idUser;
        this.idGroup = idGroup;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdGroup() {
        return idGroup;
    }
}
