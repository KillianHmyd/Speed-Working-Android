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

import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.classItems.Group;
import parisdescartes.pjs4.classItems.OwnSkill;
import parisdescartes.pjs4.classItems.Skill;

/**
 * Created by Vocxtir on 25/03/2016.
 */
public class SkillAdapter extends ArrayAdapter<OwnSkill> {
    ERelationDbHelper eRelationDbHelper ;

    public SkillAdapter(Context context, List<OwnSkill> skills) {
        super(context, 0, skills);
        eRelationDbHelper = new ERelationDbHelper(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_skill,parent, false);
        }

        SkillViewHolder viewHolder = (SkillViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new SkillViewHolder();
            viewHolder.skillName    = (TextView) convertView.findViewById(R.id.skillName);
            convertView.setTag(viewHolder);
        }
        System.out.println("position: "+position);
        OwnSkill skill = getItem(position);
        System.out.println("idSkill : "+skill.getIdSkill());
        viewHolder.skillName.setText(eRelationDbHelper.getSkill(skill.getIdSkill()).getSkillName());

        return convertView;
    }

    private class SkillViewHolder{
        public TextView skillName;
        public TextView description;
        public ImageView skillIcon;

    }
}
