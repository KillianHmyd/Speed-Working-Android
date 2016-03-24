
package parisdescartes.pjs4.classItems;

import java.util.Date;

/**
 * Created by Killian on 08/02/2016.
 */
public class User {

    private Integer idUser;
    private String idFacebook;
    private String tokenFacebook;
    private Date lastActivity;
    private Integer code ;
    private String msg ;

    public User(Integer idUser, String idFacebook, String tokenFacebook, Date lastActivity, Integer code, String msg) {
        this.idUser = idUser;
        this.idFacebook = idFacebook;
        this.tokenFacebook = tokenFacebook;
        this.lastActivity = lastActivity;
        this.code = code;
        this.msg = msg;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public String getTokenFacebook() {
        return tokenFacebook;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
