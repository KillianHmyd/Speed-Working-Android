package parisdescartes.pjs4.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.ErelationService;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.activities.CreateGroup;
import parisdescartes.pjs4.activities.MainActivity;
import parisdescartes.pjs4.classItems.Group;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


/**
 * Created by Kévin on 24/03/2016.
 */
public class TwoFragment extends Fragment {

    ERelationDbHelper eRelationDbHelper;
    ErelationService eRelationService ;
    ArrayList<Group> listGroups;
    ListView mListView;
    GroupAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    //Données de test
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

        listGroups = eRelationDbHelper.getAllGroups(); //Récupération des groupes
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_two, container, false);
        mListView = (ListView)view.findViewById(R.id.listViewOfGroups);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        if(listGroups == null){
            listGroups = new ArrayList<>();
        }
        adapter = new GroupAdapter(getActivity(), listGroups);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(view.findViewById(R.id.emptyElementGroup));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshGroups();
            }
        });


        ListView listView = (ListView) view.findViewById(R.id.listViewOfGroups);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToListView(listView);
        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateGroup.class);
                startActivity(intent);
            }
        });

        //Mise en place de l'interaction des clicks + groupes
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), listGroups.get(position).getNameGroup(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void refreshGroups(){
        eRelationService.getGroups(AccessToken.getCurrentAccessToken().getToken(), new Callback<ArrayList<Group>>() {
            @Override
            public void success(ArrayList<Group> groups, Response response) {
                eRelationDbHelper.deleteAllGroup();
                eRelationDbHelper.deleteAllUserToConv();
                for(Group g : groups) {
                    eRelationDbHelper.insertGroup(g);
                }
                adapter.clear();
                adapter.addAll(groups);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                swipeRefreshLayout.setRefreshing(false);
                ((MainActivity)getActivity()).errorDialog("Connexion au serveur impossible");
            }
        });
    }

}