package parisdescartes.pjs4.fragments;

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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.ErelationService;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.activities.MainActivity;
import parisdescartes.pjs4.classItems.Conversation;
import parisdescartes.pjs4.classItems.Group;
import parisdescartes.pjs4.classItems.Message;
import parisdescartes.pjs4.classItems.Participant;
import parisdescartes.pjs4.classItems.Profil;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


/**
 * Created by Kévin on 24/03/2016.
 */
public class ThreeFragment extends Fragment {

    ERelationDbHelper eRelationDbHelper;
    ErelationService eRelationService ;
    List<Conversation> listConversations;
    ListView mListView;
    ConversationAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        eRelationDbHelper = new ERelationDbHelper(getActivity());

        //instanciation de l'API node
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        eRelationService    = new RestAdapter.Builder().
                setEndpoint(ErelationService.ENDPOINT).
                setConverter(new GsonConverter(gson)).
                build().
                create(ErelationService.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        mListView = (ListView)view.findViewById(R.id.listViewOfConv);
        refreshChats();
        //Mise en place de l'interaction des clicks + groupes
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), listConversations.get(position).getNameConv(), Toast.LENGTH_SHORT).show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshChats();
            }
        });

        return view;
    }

    private void refreshChats(){
        eRelationService.getConversation(AccessToken.getCurrentAccessToken().getToken(), new Callback<ArrayList<Conversation>>() {
            @Override
            public void success(ArrayList<Conversation> conversations, Response response) {
                listConversations = conversations;
                for (Conversation c : conversations) {
                    eRelationDbHelper.deleteAllConv();
                    eRelationDbHelper.deleteAllMessage();
                    eRelationDbHelper.deleteAllUserToConv();
                    eRelationDbHelper.insertConversation(c);
                    if(c.getLastMessage() != null) {
                        eRelationDbHelper.insertMessage(c.getLastMessage());
                        Message m = c.getLastMessage();
                        Profil p = eRelationDbHelper.getProfile(m.getIdUser());
                        if(p == null)
                            eRelationService.getProfil(AccessToken.getCurrentAccessToken().getToken(), m.getIdUser(), new Callback<Profil>() {
                                @Override
                                public void success(Profil profil, Response response) {
                                    if(profil.getEmail() == null)
                                        eRelationDbHelper.insertProfile(profil, false);
                                    else
                                        eRelationDbHelper.insertProfile(profil, true);

                                }

                                @Override
                                public void failure(RetrofitError error) {

                                }
                            });
                    }
                    for(Participant participant : c.getParticipants()){
                        Profil p = eRelationDbHelper.getProfile(participant.getIdUser());
                        if(p == null)
                            eRelationService.getProfil(AccessToken.getCurrentAccessToken().getToken(), participant.getIdUser(), new Callback<Profil>() {
                                @Override
                                public void success(Profil profil, Response response) {
                                    if(profil.getEmail() == null)
                                        eRelationDbHelper.insertProfile(profil, false);
                                    else
                                        eRelationDbHelper.insertProfile(profil, true);

                                }

                                @Override
                                public void failure(RetrofitError error) {

                                }
                            });
                    }

                }
                if(adapter == null) {
                    adapter = new ConversationAdapter(getActivity(), listConversations, eRelationDbHelper);
                    mListView.setAdapter(adapter);
                }
                else {
                    adapter.clear();
                    adapter.addAll(listConversations);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                swipeRefreshLayout.setRefreshing(false);
                ((MainActivity) getActivity()).errorDialog("Connexion au serveur impossible");
            }
        });
    }
}