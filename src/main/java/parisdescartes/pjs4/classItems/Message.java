package parisdescartes.pjs4.classItems;

import java.util.Date;

/**
 * Created by Vocxtir on 23/03/2016.
 */
public class Message {

    private Integer idMsg ;
    private String string;
    private Integer idConv ;
    private Integer idUser ;
    private Date date;
    private Integer code ;
    private String message;

    public Message(Integer idMsg, String msgContent, Integer idConv,
                   Integer idUser, Date date, Integer code, String msg) {
        this.idMsg = idMsg;
        this.string = msgContent;
        this.idConv = idConv;
        this.idUser = idUser;
        this.date = date;
        this.code = code;
        this.message = msg;
    }

    public Integer getIdMsg() {
        return idMsg;
    }

    public String getString() {
        return string;
    }

    public Date getDate() {
        return date;
    }

    public Integer getIdConv() {
        return idConv;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
