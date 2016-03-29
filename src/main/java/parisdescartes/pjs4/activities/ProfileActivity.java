package parisdescartes.pjs4.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.ErelationService;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.classItems.Profil;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class ProfileActivity extends AppCompatActivity {

    private ERelationDbHelper eRelationDbHelper ;
    private ErelationService erelationService;
    private Profil profile ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eRelationDbHelper = new ERelationDbHelper(this);
        int idUser = getIntent().getExtras().getInt("idUser");
        profile = eRelationDbHelper.getProfile(idUser);
        setContentView(R.layout.activity_profile);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        erelationService = new RestAdapter.Builder().
                setEndpoint(ErelationService.ENDPOINT).
                setConverter(new GsonConverter(gson)).
                build().
                create(ErelationService.class);
        if(profile == null){
            erelationService.getProfil(AccessToken.getCurrentAccessToken().getToken(), idUser, new Callback<Profil>() {
                @Override
                public void success(Profil profil, Response response) {
                    eRelationDbHelper.insertProfile(profil, profil.getEmail() == null? false : true);
                    profile = profil;
                }

                @Override
                public void failure(RetrofitError error) {
                    errorDialog("Echec de la récupération de données via le serveur distant");
                    finish();
                }
            });
        }

        //Mise en place des informations sur le Layout
        TextView tVNames=   (TextView) findViewById(R.id.textViewNames);
        TextView tMail  =   (TextView) findViewById(R.id.textViewMail);
        Picasso.with(this).load(profile.getPicture()).into(((ImageView) findViewById(R.id.imageViewProfile)));

        tVNames.setText(profile.getFirstname() + " " + profile.getLastname());
        tMail.setText(profile.getEmail() != null ? profile.getEmail() : "Vous n'êtes pas autorisé à voir l'email \n de cette personne");






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

    public Context getContext(){
        return this ;
    }
}
