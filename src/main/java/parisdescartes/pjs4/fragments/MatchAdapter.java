package parisdescartes.pjs4.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import parisdescartes.pjs4.R;
import parisdescartes.pjs4.classItems.Profil;

/**
 * Created by Killian on 29/03/2016.
 */
public class MatchAdapter extends ArrayAdapter<Profil> {

    public MatchAdapter(Context context, int textViewResourceId, ArrayList<Profil> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_conv,parent, false);
        }
        MatchViewHolder viewHolder = (MatchViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new MatchViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MatchViewHolder) convertView.getTag();
        }

        Profil item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.pseudo.setText(String.format("%s %s", item.getFirstname(), item.getLastname()));
            Picasso.with(getContext()).load(item.getPicture()).into(viewHolder.avatar);
        }

        return convertView;
    }

    private class MatchViewHolder{
        public TextView pseudo;
        public ImageView avatar;

    }
}
