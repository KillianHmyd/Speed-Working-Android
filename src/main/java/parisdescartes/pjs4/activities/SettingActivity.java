package parisdescartes.pjs4.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.facebook.login.LoginManager;

import parisdescartes.pjs4.Application;
import parisdescartes.pjs4.R;

/**
 * Created by Killian on 31/03/2016.
 */
public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            Preference myPref = (Preference) findPreference("deconnection");
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    LoginManager.getInstance().logOut();
                    ((Application) getApplication()).resetDb();
                    SharedPreferences sharedPreferencesUser = getSharedPreferences("USER", Context.MODE_PRIVATE);
                    sharedPreferencesUser.edit().clear().commit();
                    startActivity(new Intent(getContext(), ConnectActivity.class));
                    return true;
                }
            });
        }
    }

}
