package parisdescartes.pjs4.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import java.util.List;

import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.classItems.Group;


/**
 * Created by Kévin on 24/03/2016.
 */
public class TwoFragment extends Fragment {

    ERelationDbHelper eRelationDbHelper;
    List<Group> listGroups;
    ListView mListView;

    String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        eRelationDbHelper = new ERelationDbHelper(getActivity());
        if (eRelationDbHelper.insertGroup(new Group(1,"TestGrp","Ceci est un premier test",1,false,null,null,null))) {
            Toast toast = Toast.makeText(getContext(), "Groupe ajoutée", Toast.LENGTH_SHORT);
            toast.show();
        }

        listGroups = eRelationDbHelper.getAllGroups();
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_group, prenoms);
        //mListView.setAdapter(adapter);

        GroupAdapter adapter = new GroupAdapter(getActivity(), listGroups);
        //mListView.setAdapter(adapter);
        return view;
    }

}