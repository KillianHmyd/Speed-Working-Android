package parisdescartes.pjs4;


import java.util.Date;

/**
 * Created by Killian on 08/02/2016.
 */
public class User {

    private int idUser;
    private String idFacebook;
    private String tokenFacebook;
    private Date lastActivity;

    public User(int idUser, String idFacebook, String tokenFacebook, Date lastActivity) {
        this.idUser = idUser;
        this.idFacebook = idFacebook;
        this.tokenFacebook = tokenFacebook;
        this.lastActivity = lastActivity;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getTokenFacebook() {
        return tokenFacebook;
    }

    public void setTokenFacebook(String tokenFacebook) {
        this.tokenFacebook = tokenFacebook;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }
}
