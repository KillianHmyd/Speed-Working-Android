package parisdescartes.pjs4.classItems;

/**
 * Created by Killian on 24/03/2016.
 */
public class ResponseService {
    private Integer code;
    private String message;

    public ResponseService(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
