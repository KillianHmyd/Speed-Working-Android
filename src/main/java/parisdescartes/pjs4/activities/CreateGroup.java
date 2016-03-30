package parisdescartes.pjs4.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.FragmentTransaction;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import parisdescartes.pjs4.Application;
import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.ErelationService;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.classItems.Group;
import parisdescartes.pjs4.classItems.ResponseAddUser;
import parisdescartes.pjs4.classItems.ResponseCreateGroup;
import parisdescartes.pjs4.classItems.ResponseService;
import parisdescartes.pjs4.classItems.ServiceAddUserToGroup;
import parisdescartes.pjs4.fragments.ListMatch;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Kévin on 28/03/2016.
 */
public class CreateGroup extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ArrayList<Integer> idUsersGroup;
    private EditText name;
    private  EditText presentation;
    private ERelationDbHelper db;

    ErelationService erelationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
        idUsersGroup = new ArrayList<>();
        name = (EditText)findViewById(R.id.nameGroup);
        presentation = (EditText) findViewById(R.id.descriptionGroup);
        db = ((Application)getApplication()).getDb();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        erelationService = new RestAdapter.Builder().
                setEndpoint(ErelationService.ENDPOINT).
                setConverter(new GsonConverter(gson)).
                build().
                create(ErelationService.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGroup);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            if(name.getText().toString() != null && presentation.getText().toString() != null)
            erelationService.createGroup(AccessToken.getCurrentAccessToken().getToken(), new Group(name.getText().toString(), presentation.getText().toString()),
                    new Callback<ResponseCreateGroup>() {
                @Override
                public void success(final ResponseCreateGroup responseCreateGroup, Response response) {
                    for(Integer i : idUsersGroup){
                        erelationService.addUserToGroup(AccessToken.getCurrentAccessToken().getToken(), new ServiceAddUserToGroup(i, responseCreateGroup.getIdGroup()),
                                new Callback<ResponseAddUser>() {
                            @Override
                            public void success(ResponseAddUser responseAddUser, Response response) {
                                if(idUsersGroup.indexOf(responseAddUser.getIdUser()) == idUsersGroup.size() - 1){
                                    Toast.makeText(CreateGroup.this, "Groupe crée", Toast.LENGTH_SHORT).show();
                                    erelationService.getGroup(AccessToken.getCurrentAccessToken().getToken(), responseCreateGroup.getIdGroup(), new Callback<Group>() {
                                        @Override
                                        public void success(Group group, Response response) {
                                            db.insertGroup(group);
                                            CreateGroup.this.finish();
                                            return;
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            CreateGroup.this.finish();
                                            return;
                                        }
                                    });
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void searchBetter(View view) {
        Toast.makeText(CreateGroup.this, "Fonctionnalité pas encore implémentée", Toast.LENGTH_SHORT).show();
    }
    public void searchMatch(View view) {
        fragment = new ListMatch();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();
    }

    public void addToArray(int idUser) {
        Integer integer = idUser;
        idUsersGroup.add(integer);
        Toast.makeText(CreateGroup.this, String.valueOf(integer), Toast.LENGTH_SHORT);
    }

}
