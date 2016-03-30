package parisdescartes.pjs4.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
                    init();
                }

                @Override
                public void failure(RetrofitError error) {
                    errorDialog("Echec de la récupération de données via le serveur distant");
                    finish();
                }
            });
        }
        else{
            init();
        }

    }

    public void init(){
        //Mise en place des informations sur le Layout
        TextView tVFirstname=   (TextView) findViewById(R.id.textViewFirstname);
        TextView tVLastname=   (TextView) findViewById(R.id.textViewLastname);
        TextView tMail  =   (TextView) findViewById(R.id.textViewMail);
        TextView tGender = (TextView) findViewById(R.id.textViewGender);
        TextView tAge = (TextView) findViewById(R.id.textViewAge);
        Picasso.with(getContext()).load(profile.getPicture()).into(((ImageView) findViewById(R.id.imageViewProfile)));

        tVFirstname.setText(profile.getFirstname());
        tVLastname.setText(profile.getLastname() != null ? " "+profile.getLastname() : "");
        tMail.setText(profile.getEmail() != null ? profile.getEmail() : "");
        tGender.setText(profile.getGender().equals("male") ? "Homme" : "Femme");
        if(profile.getBirthday() != null){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
            try {
                Calendar dob = Calendar.getInstance();
                dob.setTime(df.parse(profile.getBirthday()));
                Calendar today = Calendar.getInstance();
                int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
                if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
                    age--;
                } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                        && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
                    age--;
                }
                tAge.setText(age+" ans");
            } catch (ParseException e) {
                tAge.setText("");
            }
        }
        else
            tAge.setText("");
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public Context getContext(){
        return this ;
    }
}
