package tr.edu.msku.steprace.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import tr.edu.msku.steprace.MainActivity;
import tr.edu.msku.steprace.R;

public class SettingsActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private TextView profileName,profileSurname,profileEmail,profilePassword;
    private ImageView profileImageView;
    private ImageView backBtn;
    private Button updateBtn;
    private Button delete_account;
    private Button logOut;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent data = getIntent();
        final String name = data.getStringExtra("name");
        String surname = data.getStringExtra("surname");
        String email = data.getStringExtra("email");
        String password = data.getStringExtra("password");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        profileName = findViewById(R.id.name_settings);
        profileSurname = findViewById(R.id.surname_settings);
        profileEmail = findViewById(R.id.email_settings);
        profilePassword = findViewById(R.id.password);
        profileImageView = findViewById(R.id.viewImage);
        updateBtn = findViewById(R.id.update);
        delete_account = findViewById(R.id.delete_account);
        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = profileEmail.getText().toString().trim();
                String name =profileName.getText().toString().trim();
                String surname =profileSurname.getText().toString().trim();
                String password =profilePassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplication(), "Enter your registered name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(surname)) {
                    Toast.makeText(getApplication(), "Enter your registered surname.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplication(), "Enter your registered password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                fAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SettingsActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SettingsActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

            @Override
            public void onClick(View v) {
                String email = profileEmail.getText().toString().trim();
                String password = profilePassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                //startActivity(intent);
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                fAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SettingsActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SettingsActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SettingsActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                                    finish();
                                }

                            }
                        });

            }
        });
        backBtn =findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        StorageReference profileRef = storageReference.child("Users/"+fAuth.getCurrentUser().getUid()+"/.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileName.getText().toString().isEmpty() || profileSurname.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profilePassword.getText().toString().isEmpty()){
                    Toast.makeText(SettingsActivity.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String email = profileEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("Users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email",email);
                        edited.put("name",profileName.getText().toString());
                        edited.put("surname",profileSurname.getText().toString());
                        edited.put("password",profilePassword.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SettingsActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                        });
                        Toast.makeText(SettingsActivity.this, "Email is changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SettingsActivity.this,   e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        profileName.setText(name);
        profileSurname.setText(surname);
        profileEmail.setText(email);
        profilePassword.setText(password);

        Log.d(TAG, "onCreate: " + name + " " +surname + " " + email + " " + password + " ");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                //profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);


            }
        }

    }

    private void uploadImageToFirebase(Uri imageUri) {
        // uplaod image to firebase storage
        final StorageReference fileRef = storageReference.child(fAuth.getUid().toString() );
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

