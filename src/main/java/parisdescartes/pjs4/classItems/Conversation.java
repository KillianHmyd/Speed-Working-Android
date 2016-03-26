package parisdescartes.pjs4.classItems;

/**
 * Created by Vocxtir on 26/03/2016.
 */
public class Conversation {
    private Integer idConv ;
    private String nameConv ;

    private Integer code ;
    private String message;

    public Conversation(Integer idConv, String nameConv, Integer code, String message) {
        this.idConv = idConv;
        this.nameConv = nameConv;
        this.code = code;
        this.message = message;
    }

    public Integer getIdConv() {
        return idConv;
    }

    public String getNameConv() {
        return nameConv;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
