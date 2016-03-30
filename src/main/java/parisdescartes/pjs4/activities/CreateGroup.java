package parisdescartes.pjs4.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.ArrayList;

import parisdescartes.pjs4.R;
import parisdescartes.pjs4.fragments.ListMatch;

/**
 * Created by Kévin on 28/03/2016.
 */
public class CreateGroup extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ArrayList<Integer> IdUsersGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGroup);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
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
        Integer integer = new Integer(idUser);
        IdUsersGroup.add(integer);
        Toast.makeText(CreateGroup.this, integer, Toast.LENGTH_SHORT);
    }
}
