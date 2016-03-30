package parisdescartes.pjs4.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;


import parisdescartes.pjs4.Application;
import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.ErelationService;
import parisdescartes.pjs4.R;

import parisdescartes.pjs4.activities.CreateGroup;
import parisdescartes.pjs4.activities.MainActivity;
import parisdescartes.pjs4.activities.NoteUsers;
import parisdescartes.pjs4.classItems.Contributor;
import parisdescartes.pjs4.classItems.Group;
import parisdescartes.pjs4.classItems.Note;
import parisdescartes.pjs4.classItems.Profil;
import parisdescartes.pjs4.classItems.RemainingNote;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Kévin on 29/03/2016.
 */
public class ListNoteRemaining extends Fragment {

    ERelationDbHelper eRelationDbHelper;
    ArrayList<Profil> profils;
    private int idGroup;
    private long idUser;
    private SharedPreferences sharedPreferencesUser;
    private ErelationService erelationService;

    private ListView listView;
    public ListNoteRemaining() {
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
                R.layout.fragment_listnoteremaining, container, false);
        listView = (ListView) view.findViewById(R.id.listMatch);
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        eRelationDbHelper = ((Application)getActivity().getApplication()).getDb();
        final NoteUsers noteUsers = (NoteUsers) getActivity();
        sharedPreferencesUser = noteUsers.getSharedPreferences("USER", Context.MODE_PRIVATE);
        idUser = sharedPreferencesUser.getLong("idUser", 0);

        Intent intent = noteUsers.getIntent();
        idGroup = intent.getIntExtra("idGroup", 0);
        getProfilsNonRating();
        MatchAdapter arrayAdapter = new MatchAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                profils );
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                //CheckedTextView item = (CheckedTextView) view;
                //Toast.makeText(getActivity(), profils.get(i).getFirstname() + profils.get(i).getIdUser() + " checked", Toast.LENGTH_SHORT).show();
                final Dialog rankDialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                rankDialog.setContentView(R.layout.rank_dialog);
                rankDialog.setCancelable(true);
                final RatingBar ratingBar = (RatingBar) rankDialog.findViewById(R.id.dialog_ratingbar);

                TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);

                Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ratingBar.getNumStars();
                        rankDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                rankDialog.show();
            }
        });


        return view;
    }

    public void getProfilsNonRating() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        erelationService = new RestAdapter.Builder().
                setEndpoint(ErelationService.ENDPOINT).
                setConverter(new GsonConverter(gson)).
                build().
                create(ErelationService.class);
        erelationService.getRemainingNote(AccessToken.getCurrentAccessToken().getToken(), idGroup, new Callback<RemainingNote>() {
            @Override
            public void success(RemainingNote remainingNote, Response response) {
                if(remainingNote.isRemaining()) {
                    Group group = eRelationDbHelper.getGroup(idGroup);
                    for (Contributor c : group.getContributors()) {
                        erelationService.getNote(AccessToken.getCurrentAccessToken().getToken(), idGroup, c.getIdUser(), new Callback<Note>() {
                            @Override
                            public void success(Note note, Response response) {
                                profils.add(eRelationDbHelper.getProfile(note.getIdUserTo()));
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                errorDialog("Echec de la récupération de données via le serveur distant");
                getActivity().finish();
            }
        });
    }

    public void errorDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Erreur");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
