package parisdescartes.pjs4.fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import parisdescartes.pjs4.R;
import parisdescartes.pjs4.classItems.Group;

/**
 * Created by Vocxtir on 25/03/2016.
 */
public class GroupAdapter extends ArrayAdapter<Group> {


    public GroupAdapter(Context context, List<Group> groups) {
        super(context, 0, groups);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_group,parent, false);
        }

        GroupViewHolder viewHolder = (GroupViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new GroupViewHolder();
            viewHolder.grpName = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.description = (TextView) convertView.findViewById(R.id.text);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Group group = getItem(position);
        viewHolder.grpName.setText(group.getNameGroup());
        viewHolder.description.setText(group.getPresentation());
        //viewHolder.avatar.setImageDrawable(new ColorDrawable(group.getColor()));

        return convertView;
    }

    private class GroupViewHolder{
        public TextView grpName;
        public TextView description;
        public ImageView avatar;

    }
}
