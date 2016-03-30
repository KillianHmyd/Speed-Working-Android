package parisdescartes.pjs4.classItems;

/**
 * Created by Killian on 30/03/2016.
 */
public class ResponseAddUser {
    private Integer idUser;
    private Integer idGroup;
    private String message;
    private Integer code;

    public ResponseAddUser(Integer idUser, Integer idGroup, String message, Integer code) {
        this.idUser = idUser;
        this.idGroup = idGroup;
        this.message = message;
        this.code = code;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
