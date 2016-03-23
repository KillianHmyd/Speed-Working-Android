package parisdescartes.pjs4.classItems;

/**
 * Created by Killian on 10/02/2016.
 */
public class RequestConnect {
    private String idFacebook;
    private String tokenFacebook;

    public RequestConnect(String idFacebook, String tokenFacebook) {
        this.idFacebook = idFacebook;
        this.tokenFacebook = tokenFacebook;
    }

    public String getTokenFacebook() {
        return tokenFacebook;
    }

    public void setTokenFacebook(String tokenFacebook) {
        this.tokenFacebook = tokenFacebook;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }
}
