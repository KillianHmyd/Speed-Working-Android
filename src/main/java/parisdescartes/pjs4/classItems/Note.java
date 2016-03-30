package parisdescartes.pjs4.classItems;

/**
 * Created by harismen on 30/03/2016.
 */
public class Note {
    private Integer idUserFrom;
    private Integer idUserTo;
    private Integer idGroup;
    private Integer note;
    private Integer code;
    private String message;

    public Note(Integer idUserFrom, Integer idUserTo, Integer idGroup, Integer note, Integer code, String message) {
        this.idUserFrom = idUserFrom;
        this.idUserTo = idUserTo;
        this.idGroup = idGroup;
        this.note = note;
        this.code = code;
        this.message = message;
    }

    public Integer getIdUserFrom() {
        return idUserFrom;
    }

    public Integer getIdUserTo() {
        return idUserTo;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public Integer getNote() {
        return note;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
