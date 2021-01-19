package tr.edu.msku.steprace.service;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;

import tr.edu.msku.steprace.model.Data;

public class WorkerThread extends AsyncTask<Void,Void,Void> implements SensorEventListener {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String date = sdf.format(new Date()).toString();
    private DocumentReference ref = db.collection("Data").document(user_id).collection("data").document(date);
    int count = 0;

    @Override
    protected Void doInBackground(Void... voids) {


        return null;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
            databaseUpdate();


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void databaseUpdate(){

        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String step = documentSnapshot.getString("num");
                Integer number_of_steps = Integer.valueOf(step);
                Data data = new Data();
                if(documentSnapshot == null){

                        data.setDate(date);
                        data.setNum(1);

                        ref.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }

                    if (documentSnapshot.exists() && documentSnapshot != null && number_of_steps > 0 ){
                        data.setDate(date);
                        data.setNum(number_of_steps++);
                        ref.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }

                }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });




    }


}
