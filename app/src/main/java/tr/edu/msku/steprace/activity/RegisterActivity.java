
package tr.edu.msku.steprace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import tr.edu.msku.steprace.MainActivity;
import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.fragment.HomeFragment;
import tr.edu.msku.steprace.model.User;

public class RegisterActivity extends AppCompatActivity {
    EditText userName,password;
    EditText userSurname;
    TextView AccountExists;
    EditText city;
    EditText email_register;
    EditText dateOfBird;
    Button register;
    private FirebaseAuth mAuth;//Used for firebase authentication
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String user_id;
    private ProgressDialog loadingBar;//Used to show the progress of the registration process
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        userName = (EditText) findViewById(R.id.PersonName);
        userSurname = findViewById(R.id.PersonSurname);
        password = (EditText) findViewById(R.id.Password);
        register = (Button) findViewById(R.id.submit_btn);
        city = findViewById(R.id.city);
        email_register = findViewById(R.id.email);
        dateOfBird = findViewById(R.id.date);

        AccountExists = (TextView) findViewById(R.id.Already_link);
        TextView t2 = (TextView) findViewById(R.id.Already_link);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        loadingBar = new ProgressDialog(this);
        //When user has  an account already he should be sent to login activity.
        AccountExists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToLoginActivity();
            }
        });
        //When user clicks on register create a new account for user
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        final String user_name = userName.getText().toString().trim();
        final String UserSurname = userName.getText().toString().trim();
        final String email = email_register.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        final String city_register = city.getText().toString().trim();
        final String dateOFbird_register = dateOfBird.getText().toString().trim();


        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(RegisterActivity.this,"Please enter email id",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(pwd))
        {
            Toast.makeText(RegisterActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //When both email and password are available create a new account
            //Show the progress on Progress Dialog
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait, we are creating new Account");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())//If account creation successful print message and send user to Login Activity
                            {

                                String user_id = mAuth.getUid();
                                DocumentReference ref = db.collection("Users").document(user_id);
                                User user = new User(user_name,UserSurname,user_id,city_register,email,dateOFbird_register);


                                ref.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {




                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                                sendUserToLoginActivity();
                                Toast.makeText(RegisterActivity.this,"Account created successfully",Toast.LENGTH_SHORT).show();



                                loadingBar.dismiss();
                            }
                            else//Print the error message incase of failure
                            {
                                String msg = task.getException().toString();
                                Toast.makeText(RegisterActivity.this,"Error: "+msg,Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void sendUserToLoginActivity() {
        //This is to send user to Login Activity.
        Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }
}
