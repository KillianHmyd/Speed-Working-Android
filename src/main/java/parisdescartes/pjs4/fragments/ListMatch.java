package parisdescartes.pjs4.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import parisdescartes.pjs4.Application;
import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.ErelationService;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.activities.CreateGroup;
import parisdescartes.pjs4.classItems.Profil;

/**
 * Created by Kévin on 29/03/2016.
 */
public class ListMatch extends Fragment {

    ERelationDbHelper eRelationDbHelper;
    ArrayList<Profil> profils;
    ArrayList<String> city;


    private ListView listView;
    public ListMatch() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_list_match, container, false);
        listView = (ListView) view.findViewById(R.id.listMatch);
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        final CreateGroup createGroup = (CreateGroup) getActivity();

        eRelationDbHelper = ((Application)getActivity().getApplication()).getDb();

        profils = eRelationDbHelper.getMatchedProfile();
        city = new ArrayList<>();

        MatchAdapter arrayAdapter = new MatchAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                profils );
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                //CheckedTextView item = (CheckedTextView) view;
                //Toast.makeText(getActivity(), profils.get(i).getFirstname() + profils.get(i).getIdUser() + " checked", Toast.LENGTH_SHORT).show();
                createGroup.addToArray(profils.get(i).getIdUser());
            }
        });


        return view;
    }

}
