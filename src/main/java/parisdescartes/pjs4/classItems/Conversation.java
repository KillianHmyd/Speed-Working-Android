package parisdescartes.pjs4.classItems;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Vocxtir on 26/03/2016.
 */
public class Conversation {
    private Integer idConv ;
    private String nameConv ;
    private Message lastMessage;
    private ArrayList<Participant> participants;

    private Integer code ;
    private String message;

    public Conversation(Integer idConv, String nameConv, Message lastMessage, ArrayList<Participant> participants,Integer code, String message) {
        this.idConv = idConv;
        this.nameConv = nameConv;
        this.code = code;
        this.participants = participants;
        this.message = message;
        this.lastMessage = lastMessage;
    }

    public Conversation(Integer idConv, String nameConv, Message lastMessage, Integer code, String message) {
        this.idConv = idConv;
        this.nameConv = nameConv;
        this.code = code;
        this.message = message;
        this.lastMessage = lastMessage;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
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

    public Message getLastMessage() {
        return lastMessage;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }
}
