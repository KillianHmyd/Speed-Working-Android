package parisdescartes.pjs4.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.ErelationService;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.classItems.Conversation;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


/**
 * Created by Kévin on 24/03/2016.
 */
public class ThreeFragment extends Fragment {

    ERelationDbHelper eRelationDbHelper;
    ErelationService eRelationService ;
    List<Conversation> listConversations;
    ListView mListView;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        eRelationDbHelper   = new ERelationDbHelper(getActivity());

        //instanciation de l'API node
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        eRelationService    = new RestAdapter.Builder().
                setEndpoint(ErelationService.ENDPOINT).
                setConverter(new GsonConverter(gson)).
                build().
                create(ErelationService.class);

        listConversations = eRelationDbHelper.getAllConv(); //Récupération des Conversations
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        mListView = (ListView)view.findViewById(R.id.listViewOfConv);
        //Test
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_group, prenoms);
        //mListView.setAdapter(adapter);

        ConversationAdapter adapter = new ConversationAdapter(getActivity(), listConversations);
        mListView.setAdapter(adapter);

        //Mise en place de l'interaction des clicks + groupes
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), listConversations.get(position).getNameConv(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}