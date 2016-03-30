package parisdescartes.pjs4.classItems;

/**
 * Created by Killian on 30/03/2016.
 */
public class ResponseCreateGroup {
    Integer idGroup;
    String message;
    Integer code;

    public ResponseCreateGroup(Integer idGroup, String message, Integer code) {
        this.idGroup = idGroup;
        this.message = message;
        this.code = code;
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
