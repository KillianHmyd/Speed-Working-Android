package parisdescartes.pjs4.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.ErelationService;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.classItems.Group;
import parisdescartes.pjs4.classItems.IdGroup;
import parisdescartes.pjs4.classItems.ResponseService;
import parisdescartes.pjs4.fragments.TwoFragment;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by harismen on 30/03/2016.
 */
public class GroupActivity extends AppCompatActivity {
    private ERelationDbHelper eRelationDbHelper ;
    private ErelationService erelationService;
    private Group groupe;
    private int idGroup;
    private long idLeader;
    private SharedPreferences sharedPreferencesUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgroup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGroup);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreferencesUser = getSharedPreferences("USER", Context.MODE_PRIVATE);
        idLeader = sharedPreferencesUser.getLong("idUser", 0);

        Intent intent = getIntent();
        idGroup = intent.getIntExtra("idGroup", 0);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        erelationService = new RestAdapter.Builder().
                setEndpoint(ErelationService.ENDPOINT).
                setConverter(new GsonConverter(gson)).
                build().
                create(ErelationService.class);
        erelationService.getGroup(AccessToken.getCurrentAccessToken().getToken(), idGroup, new Callback<Group>() {
            @Override
            public void success(Group group, Response response) {
                groupe = group;
                init();
                checkLeader();
            }

            @Override
            public void failure(RetrofitError error) {
                errorDialog("Echec de la récupération de données via le serveur distant");
                finish();
            }
        });


    }

    public void init(){
        //Mise en place des informations sur le Layout
        TextView nameGroup = (TextView) findViewById(R.id.nameGroup);
        TextView descriptionGroup = (TextView) findViewById(R.id.descriptionGroup);

        nameGroup.setText(groupe.getNameGroup());
        descriptionGroup.setText(groupe.getPresentation());

    }

    public void errorDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
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

    private void checkLeader() {
        Integer idLead = groupe.getIdLeader();
        if (idLead == idLeader) {
            Button addUser = (Button) findViewById(R.id.buttonAddUserToGroup);
            Button finishGroup = (Button) findViewById(R.id.buttonFinishGroup);
            addUser.setVisibility(View.VISIBLE);
            finishGroup.setVisibility(View.VISIBLE);
        }
    }

    public void addUser(View view) {
        Toast.makeText(GroupActivity.this, "Utilisateur ajouté", Toast.LENGTH_SHORT).show();
    }

    public void finishGroup(View view) {
        erelationService.endGroup(AccessToken.getCurrentAccessToken().getToken(), new IdGroup(groupe.getIdGroup()), new Callback<ResponseService>() {
            @Override
            public void success(ResponseService responseService, Response response) {
                Toast.makeText(GroupActivity.this, "Groupe fini c'est bon negro", Toast.LENGTH_SHORT);
                Intent intent = new Intent(GroupActivity.this, NoteUsers.class);
                int idUser = groupe.getIdGroup();
                intent.putExtra("idGroup", idUser);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
