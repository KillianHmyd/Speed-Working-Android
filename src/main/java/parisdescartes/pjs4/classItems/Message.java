package parisdescartes.pjs4.classItems;

/**
 * Created by Vocxtir on 23/03/2016.
 */
public class Message {

    private int idMsg ;
    private String msgContent ;
    private int idConv ;
    private int idUser ;

    public Message(int idUser, int idMsg, String msgContent, int idConv) {
        this.idUser = idUser;
        this.idMsg = idMsg;
        this.msgContent = msgContent;
        this.idConv = idConv;
    }

    public int getIdMsg() {
        return idMsg;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public int getIdConv() {
        return idConv;
    }

    public int getIdUser() {
        return idUser;
    }
}
