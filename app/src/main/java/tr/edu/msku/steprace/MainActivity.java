package tr.edu.msku.steprace;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import tr.edu.msku.steprace.activity.LoginActivity;
import tr.edu.msku.steprace.activity.SettingsActivity;
import tr.edu.msku.steprace.adapter.onUserAdded;
import tr.edu.msku.steprace.fragment.Friends;
import tr.edu.msku.steprace.fragment.HomeFragment;
import tr.edu.msku.steprace.fragment.NotificationFragment;
import tr.edu.msku.steprace.fragment.SearchFragment;
import tr.edu.msku.steprace.service.IntentService;

public class MainActivity extends AppCompatActivity implements onUserAdded {


    BottomNavigationView navigationView;
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //always start with homefragment
        //startIntentService();
        changeFragment(new HomeFragment());
        //startIntentService();

            navigationView = findViewById(R.id.bottom_navigation);

            navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.HomeMenu:
                            changeFragment(new HomeFragment());
                            break;
                        case R.id.FriendsMenu:
                            changeFragment(new Friends());
                            break;

                        case R.id.SearchMenu:
                            changeFragment(new SearchFragment());
                            break;

                        case R.id.NotificationsMenu:
                            changeFragment(new NotificationFragment());
                            break;
                        case R.id.SettingsMenu:
                            Intent intent3 = new Intent(MainActivity.this, SettingsActivity.class);
                            MainActivity.this.startActivity(intent3);
                            break;
                    }
                    return true;

                }
            });

            mFirebaseAuth = FirebaseAuth.getInstance();
            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null){
                        Toast.makeText(MainActivity.this,"Enter e-mail address and password ! ",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this,"You are already logged in"  ,Toast.LENGTH_SHORT).show();

                    }

                }
            };

    }
    @Override
    protected void onStart(){

        //TODO update the UI
        Calendar c = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();

        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    protected void startIntentService () {
        Intent intent = new Intent(this, IntentService.class);
        startService(intent);
    }

    protected  void changeFragment(Fragment fragment){
        if (fragment != null){
            FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
            fts.replace(R.id.container, fragment);
            fts.commit();

        }
    }

    @Override
    public void onUserAdd(String id) {
        Log.d("main66",id);
    }
}