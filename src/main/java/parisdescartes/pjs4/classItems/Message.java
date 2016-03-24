package parisdescartes.pjs4.classItems;

/**
 * Created by Vocxtir on 23/03/2016.
 */
public class Message {

    private Integer idMsg ;
    private String msgContent ;
    private Integer idConv ;
    private Integer idUser ;
    private Integer code ;
    private String msg ;

    public Message(Integer idMsg, String msgContent, Integer idConv,
                   Integer idUser, Integer code, String msg) {
        this.idMsg = idMsg;
        this.msgContent = msgContent;
        this.idConv = idConv;
        this.idUser = idUser;
        this.code = code;
        this.msg = msg;
    }

    public Integer getIdMsg() {
        return idMsg;
    }

    public String getMsgContent() {
        return msgContent;
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

    public String getMsg() {
        return msg;
    }
}
