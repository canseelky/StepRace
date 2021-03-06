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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import tr.edu.msku.steprace.activity.LoginActivity;
import tr.edu.msku.steprace.activity.RegisterActivity;
import tr.edu.msku.steprace.activity.SettingsActivity;
import tr.edu.msku.steprace.adapter.onUserAdded;
import tr.edu.msku.steprace.fragment.Friends;
import tr.edu.msku.steprace.fragment.HomeFragment;
import tr.edu.msku.steprace.fragment.NotificationFragment;
import tr.edu.msku.steprace.fragment.SearchFragment;
import tr.edu.msku.steprace.model.User;
import tr.edu.msku.steprace.service.IntentService;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView navigationView;
    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String user_req_id;





    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //always start with homefragment
        //startIntentService();

        mFirebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Toast.makeText(MainActivity.this,"Enter e-mail address and password ! ",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        }

        changeFragment(new HomeFragment());

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

        startIntentService();


    }
    @Override
    protected void onStart(){
       String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        DocumentReference mUser = db.collection("Users").document(user_id);



        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null){
                    Toast.makeText(MainActivity.this,"Enter e-mail address and password ! ",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    //Toast.makeText(MainActivity.this,"You are already logged in"  ,Toast.LENGTH_SHORT).show();

                }

            }
        };


        startIntentService();
        //TODO update the UI



    Intent uid = getIntent();
    user_req_id = uid.getStringExtra("userid");

    if (user_req_id != null){

        SendRequest(user_req_id);
}
        Intent accept = getIntent();
         String accept_id = accept.getStringExtra("acceptid");

        if (accept_id != null){

            AcceptUser(accept_id);
        }

        Intent reject = getIntent();
        String reject_user_id = reject.getStringExtra("decline");

        if(reject_user_id != null){

            rejectUser(reject_user_id);
        }



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

public void SendRequest(String id){
   DocumentReference Request_ref = db.collection("Notifications").document(id).collection("requests").document(mFirebaseAuth.getUid().toString());
   User user_send = new User();
   user_send.setUser_id(mFirebaseAuth.getUid().toString());
   Request_ref.set(user_send).addOnSuccessListener(new OnSuccessListener<Void>() {
       @Override
       public void onSuccess(Void aVoid) {

           Toast.makeText(MainActivity.this,"send request succesfully!",Toast.LENGTH_LONG).show();
       }
   }).addOnFailureListener(new OnFailureListener() {
       @Override
       public void onFailure(@NonNull Exception e) {
           Log.d("MAIN ACTIVITY",e.toString());

       }
   });



}

public void AcceptUser(String id){
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("Friend").document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).collection("friend").document(id);
    Map<String,Object> edited = new HashMap<>();
    edited.put("id",id);

    docRef.set(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Log.d("Main Activity","friendship accepted!");
            Toast.makeText(MainActivity.this,"User accepted successfully!",Toast.LENGTH_LONG).show();


        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.e("Main Activity",e.toString());

        }
    });

}

    public void rejectUser(String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Notifications").document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).collection("request").document(id);

        Map<String,Object> updates = new HashMap<>();
        updates.put(null, FieldValue.delete());
        Toast.makeText(MainActivity.this,"User  rejected !",Toast.LENGTH_LONG).show();

    }




}