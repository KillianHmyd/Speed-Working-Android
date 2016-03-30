package parisdescartes.pjs4.fragments;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.classItems.OwnSkill;
import parisdescartes.pjs4.classItems.Profil;
import parisdescartes.pjs4.classItems.Skill;

public class SkillListFragment extends Fragment {

    private ERelationDbHelper eRelationDbHelper ;
    private ListView mListView ;
    private SkillAdapter adapter ;
    private ArrayList<OwnSkill> listSkills;
    private Integer idUser ;

    public SkillListFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        eRelationDbHelper = new ERelationDbHelper(getActivity());
        idUser = getArguments().getInt("idUser");
        listSkills = eRelationDbHelper.getAllOwnSkill(idUser);
        if(listSkills == null)
            listSkills = new ArrayList<>();
        final View view = inflater.inflate(R.layout.fragment_skill_list, container, false);
        mListView = (ListView)view.findViewById(R.id.listViewOfSkills);


        adapter = new SkillAdapter(getActivity(), listSkills);
        mListView.setAdapter(adapter);

        //Mise en place de l'interaction d'une checkbox pour les skills
/*          mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), listSkills.get(position).getSkillName(), Toast.LENGTH_SHORT).show();

                CheckBox checkSkill = (CheckBox) view.findViewById(R.id.checkBoxOfSkill);
                if (checkSkill.isChecked())
                    checkSkill.setChecked(false);
                else
                    checkSkill.setChecked(true);
            }
        });
*/

        return view ;
    }
}
