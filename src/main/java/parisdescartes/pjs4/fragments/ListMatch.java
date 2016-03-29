package parisdescartes.pjs4.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import parisdescartes.pjs4.R;

/**
 * Created by Kévin on 29/03/2016.
 */
public class ListMatch extends Fragment {
    String[] city= {
            "Bangalore",
            "Chennai",
            "Mumbai",
            "Pune",
            "Delhi",
            "Jabalpur",
            "Indore",
            "Ranchi",
            "Hyderabad",
            "Ahmedabad",
            "Kolkata",
            "Bhopal"
    };
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                city );
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                //CheckedTextView item = (CheckedTextView) view;
                Toast.makeText(getActivity(), city[i] + " checked", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
