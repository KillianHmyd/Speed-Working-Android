package parisdescartes.pjs4.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import parisdescartes.pjs4.Application;
import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.classItems.Conversation;
import parisdescartes.pjs4.classItems.Message;
import parisdescartes.pjs4.classItems.Participant;
import parisdescartes.pjs4.classItems.Profil;

/**
 * Created by Vocxtir on 26/03/2016.
 */
public class ConversationAdapter extends ArrayAdapter<Conversation> {

    ERelationDbHelper db;
    private SharedPreferences sharedPreferencesUser;
    private Profil userProfil;

    public ConversationAdapter(Context context, List<Conversation> conversations, ERelationDbHelper db) {
        super(context, 0, conversations);
        this.db = db;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        sharedPreferencesUser = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        userProfil = db.getProfile(sharedPreferencesUser.getLong("idUser", 0));
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_conv,parent, false);
        }

        ConversationViewHolder viewHolder = (ConversationViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ConversationViewHolder();
            viewHolder.convName = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.lastMessage = (TextView) convertView.findViewById(R.id.text);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<message> message
        Conversation conv = getItem(position);
        viewHolder.convName.setText(conv.getNameConv());
        Message m = conv.getLastMessage();
        String s;
        ArrayList<Participant> participants = conv.getParticipants();
        if(participants.size() == 2){
            if(participants.get(0).getIdUser() == userProfil.getIdUser()){
                Picasso.with(getContext()).load(db.getProfile(participants.get(1).getIdUser()).getPicture()).into(viewHolder.avatar);
            }
            else
                Picasso.with(getContext()).load(db.getProfile(participants.get(0).getIdUser()).getPicture()).into(viewHolder.avatar);
        }
        if(m != null) {
            Profil p = db.getProfile(conv.getLastMessage().getIdUser());
            if (p.getEmail() != null)
                s = p.getFirstname() + " " + p.getLastname() + ": " + m.getString();
            else
                s = p.getFirstname() + " " + conv.getNameConv() + ": " + m.getString();
            viewHolder.lastMessage.setText(s);
        }

        return convertView;
    }

    private class ConversationViewHolder{
        public TextView convName;
        public TextView lastMessage;
        public ImageView avatar;

    }
}