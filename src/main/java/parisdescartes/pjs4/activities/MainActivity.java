package parisdescartes.pjs4.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import parisdescartes.pjs4.Application;
import parisdescartes.pjs4.CustomViewPager;
import parisdescartes.pjs4.ERelationDbHelper;
import parisdescartes.pjs4.ErelationService;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.classItems.Profil;
import parisdescartes.pjs4.fragments.*;
import parisdescartes.pjs4.swipeCards.view.CardContainer;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    private CustomViewPager viewPager;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Profil profil;
    private SharedPreferences sharedPreferencesUser;
    private ERelationDbHelper db;
    ErelationService erelationService;
    private int[] tabIcons = {
            R.drawable.logoicon,
            R.drawable.ic_group_white_24dp,
            R.drawable.ic_message_white_24dp
    };
    private Target loadtarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferencesUser = getSharedPreferences("USER", Context.MODE_PRIVATE);
        db = ((Application)getApplication()).getDb();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        erelationService = new RestAdapter.Builder().
                setEndpoint(ErelationService.ENDPOINT).
                setConverter(new GsonConverter(gson)).
                build().
                create(ErelationService.class);
        profil = db.getProfile(sharedPreferencesUser.getLong("idUser", 0));
        //NAVIGATION DRAWER PART
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Speed Working");
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.setDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        ((TextView)nvDrawer.getHeaderView(0).findViewById(R.id.nav_header_text)).setText(profil.getFirstname() + " " + profil.getLastname());



        Picasso.with(this).load(profil.getPicture()).into(((CircularImageView) nvDrawer.getHeaderView(0).findViewById(R.id.imageFB)));
        setupDrawerContent(nvDrawer);
        nvDrawer.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("idUser", profil.getIdUser());
                startActivity(intent);
                //errorDialog("ici");
            }
        });
        Picasso.with(this).load(profil.getPicture()).into(((ImageView) nvDrawer.getHeaderView(0).findViewById(R.id.imageFB)));
        makeNavHeaderBackground();
        setupDrawerContent(nvDrawer);


        //END OF NAVIGATION DRAWER PART

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        viewPager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        viewPager.setSwipeable(false);
                        break;
                    case 1:
                        viewPager.setSwipeable(true);
                        break;
                    case 2:
                        viewPager.setSwipeable(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void makeNavHeaderBackground() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.goldengate);
        //Bitmap blurredBitmap = addBlurEffect(bitmap);
        nvDrawer.getHeaderView(0).setBackground(new BitmapDrawable(getResources(), bitmap));
    }

    private Bitmap addBlurEffect(Bitmap bitmap) {
        final float BLUR_RADIUS = 25f;
        Bitmap outputBitmap = Bitmap.createBitmap(bitmap);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, bitmap);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        final OneFragment oneFragment = new OneFragment();
        erelationService.getSuggestion(AccessToken.getCurrentAccessToken().getToken(), 10, new Callback<ArrayList<Profil>>() {
            @Override
            public void success(ArrayList<Profil> profils, Response response) {
                oneFragment.setSuggestionsCards(profils);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        adapter.addFragment(oneFragment, "ONE");
        adapter.addFragment(new TwoFragment(), "TWO");
        adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }



    //NAVIGATION DRAWER PART
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {

        Intent intent;
        switch(menuItem.getItemId()) {
            case R.id.nav_first:
                //TODO Faire la settings activity
                /*
                intent = new Intent(MainActivity.this, Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break; */
            case R.id.nav_second:
                //TODO Faire la about us activity
                intent = new Intent(MainActivity.this, AboutUs.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

        // Highlight the selected item, update the title, and close the drawer
        // Highlight the selected item has been done by NavigationView
         menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
        return actionBarDrawerToggle;
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {

        }

    public void errorDialog(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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

    public void refreshSuggestion(final View view){
        ((ImageButton)view).setEnabled(false);
        RotateAnimation ra =new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setFillAfter(true);
        ra.setDuration(1000);
        ra.setRepeatCount(-1);
        view.startAnimation(ra);
        erelationService.getSuggestion(AccessToken.getCurrentAccessToken().getToken(), 10, new Callback<ArrayList<Profil>>() {
            @Override
            public void success(ArrayList<Profil> profils, Response response) {
                viewPager = (CustomViewPager) findViewById(R.id.viewpager);
                Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());
                if (viewPager.getCurrentItem() == 0 && page != null) {
                    ((OneFragment) page).setSuggestionsCards(profils);
                    view.clearAnimation();
                    ((ImageButton) view).setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Chargement fini", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ((ImageButton) view).setEnabled(true);
                view.clearAnimation();
                errorDialog("Impossible de se connecter au serveur");
            }
        });


    }

    public Context getContext(){
        return this ;
    }

    public void accept(View view){
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());
        CardContainer layoutView =(CardContainer) ((OneFragment) page).getView().findViewById(R.id.layoutview);
        layoutView.leave(1000, 45);
    }

    public void refuse(View view){
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());
        CardContainer layoutView =(CardContainer) ((OneFragment) page).getView().findViewById(R.id.layoutview);
        layoutView.leave(-1000, 45);
    }

    public void openProfile(View view){
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra("idUser", (int)view.getTag());
        startActivity(intent);
    }


}
