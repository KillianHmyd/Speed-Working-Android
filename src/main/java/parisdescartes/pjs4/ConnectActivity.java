package parisdescartes.pjs4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class ConnectActivity extends Activity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProgressDialog progress;
    private SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        sharedpreferences  = getSharedPreferences("USER", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_connect);
        startVideo();
        if (AccessToken.getCurrentAccessToken() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
        }
        fbConnect();

    }

    @Override
    public void onResume(){
        super.onResume();
        startVideo();
    }

    @Override
    public void onPause(){
        super.onPause();
       /*if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
        }*/
    }

    public Context getContext(){
        return this;
    }

    public void fbConnect() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startVideo();
                progress = ProgressDialog.show(getContext(), "Connexion en cours",
                        "Veuillez patienter....", true);
                progress.setCancelable(true);
                progress.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        // TODO Auto-generated method stub
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            LoginManager.getInstance().logOut();
                            progress.dismiss();
                            finish();
                            startActivity(getIntent());
                            return true;
                        }
                        return true;
                    }
                });
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(final JSONObject jsonObject, GraphResponse graphResponse) {

                        Gson gson = new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                .create();
                        EraltionService erelationConnect = new RestAdapter.Builder().
                                setEndpoint(EraltionService.ENDPOINT).
                                setConverter(new GsonConverter(gson)).
                                build().
                                create(EraltionService.class);

                        try {
                            erelationConnect.connect(new RequestConnect(jsonObject.getString("id"), AccessToken.getCurrentAccessToken().getToken()), new Callback<Profil>() {
                                @Override
                                public void success(Profil profil, Response response) {
                                    sharedpreferences.edit().putInt("idUser", profil.getIdUser()).commit();
                                    sharedpreferences.edit().putString("firstname", profil.getFirstname()).commit();
                                    sharedpreferences.edit().putString("lastname", profil.getLastname()).commit();
                                    sharedpreferences.edit().putString("email", profil.getEmail()).commit();
                                    sharedpreferences.edit().putString("gender", profil.getGender()).commit();
                                    sharedpreferences.edit().putString("birthday", profil.getBirthday().toString()).commit();
                                    sharedpreferences.edit().putString("picture", profil.getPicture()).commit();
                                    progress.dismiss();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    System.out.println(error);
                                    LoginManager.getInstance().logOut();
                                    progress.dismiss();
                                    errorDialog("Connexion au serveur impossible.");
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                        } catch (JSONException e) {
                            System.out.println(e);
                            LoginManager.getInstance().logOut();
                            progress.dismiss();
                            errorDialog("Connexion au serveur impossible.");
                        }

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,last_name,email,gender, birthday,first_name,picture.height(500).width(500).type(large)");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                errorDialog("Connexion annulée.");
            }

            @Override
            public void onError(FacebookException e) {
                errorDialog("Connexionimpossible. Veuillez ressayer.");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startVideo() {
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.workingspacetest);
        videoView.setVideoURI(uri);
        boolean music = false;
        if(((AudioManager) getSystemService(Context.AUDIO_SERVICE)).isMusicActive()){
            music = true;
        }
        videoView.start();
        final boolean finalMusic = music;
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if(finalMusic){
                    Intent i = new Intent("com.android.music.musicservicecommand");
                    i.putExtra("command", "play");
                    sendBroadcast(i);
                }
                mp.setLooping(true);
            }
        });
    }

    public void errorDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(ConnectActivity.this).create();
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
