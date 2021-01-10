package tr.edu.msku.steprace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tr.edu.msku.steprace.activity.LoginActivity;
import tr.edu.msku.steprace.fragment.Friends;
import tr.edu.msku.steprace.fragment.HomeFragment;
import tr.edu.msku.steprace.fragment.SearchResult;
import tr.edu.msku.steprace.fragment.Settings;
import tr.edu.msku.steprace.service.BackgroundService;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView navigationView;
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //always start with homefragment
        changeFragment(new HomeFragment());

            navigationView = findViewById(R.id.bottom_navigation);

            navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                          changeFragment(new SearchResult());
                            break;
                        case R.id.SettingsMenu:
                            changeFragment(new Settings());
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
                        Toast.makeText(MainActivity.this,"You are already logged in",Toast.LENGTH_SHORT).show();

                    }



                }
            };




    }
    @Override
    protected void onStart(){

        //TODO update the UI

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
        Intent intent = new Intent(this, BackgroundService.class);
        //intent.putExtra("numOfSteps", numOfSteps);
        //intent.putExtra("userid", Users.userId);
        startService(intent);
    }

    protected  void changeFragment(Fragment fragment){
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        fts.replace(R.id.container, fragment);
        fts.commit();


    }
}