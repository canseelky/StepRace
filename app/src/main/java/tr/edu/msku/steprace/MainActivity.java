package tr.edu.msku.steprace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import tr.edu.msku.steprace.activities.LoginActivity;
import tr.edu.msku.steprace.activities.RegisterActivity;
import tr.edu.msku.steprace.fragments.HomeFragment;
import tr.edu.msku.steprace.service.BackgroundService;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView navigationView;
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();


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
                        case R.id.FriendsMenu:
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            MainActivity.this.startActivity(intent);
                            break;

                        case R.id.SearchMenu:
                            Intent intent2 = new Intent(MainActivity.this, RegisterActivity.class);
                            MainActivity.this.startActivity(intent2);
                            break;

                    }
                    return true;

                }
            });




    }
    @Override
    protected void onStart(){

        //TODO update the UI

        super.onStart();
    }
    protected void startIntentService () {
        Intent intent = new Intent(this, BackgroundService.class);
        //intent.putExtra("numOfSteps", numOfSteps);
        //intent.putExtra("userid", Users.userId);
        startService(intent);
    }

    protected  void changeFragment(Fragment fragment){
        mFragmentTransaction.replace(R.id.container, fragment);
        //mFragmentTransaction.addToBackStack("BackStack");
        mFragmentTransaction.commit();
    }
}