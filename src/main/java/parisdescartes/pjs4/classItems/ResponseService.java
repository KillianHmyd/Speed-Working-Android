package parisdescartes.pjs4.classItems;

/**
 * Created by Killian on 24/03/2016.
 */
public class ResponseService {
    private int code;
    private String message;

    public ResponseService(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
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
