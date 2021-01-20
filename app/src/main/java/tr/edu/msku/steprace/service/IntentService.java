package tr.edu.msku.steprace.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

import tr.edu.msku.steprace.model.Data;

public class IntentService extends android.app.IntentService implements SensorEventListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date = sdf.format(new Date()).toString();
    private DocumentReference ref = db.collection("Data").document(user_id).collection("data").document(date);
    private SensorManager manager;
    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor;



    public IntentService() {
        super("Intent-Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
            return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Log.d("ıntent","sensorchanged");
        databaseUpdate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void databaseUpdate(){

        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                documentSnapshot.toObject(Data.class);
                Data data = new Data();

                //Integer step = documentSnapshot.getString("num");
                int number_of_steps = Integer.valueOf(String.valueOf(data.getNum()));
                Log.d("ıntent1" ,String.valueOf(data.getNum()));
                if(number_of_steps == 0){
                    data.setDate(date);
                    Log.d("ıntent1" ,String.valueOf(data.getNum()));
                    data.setNum(1);

                    ref.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("ıntent","database yazıldı");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("ıntent","database yazılamadı" + e.toString());
                        }
                    });

                }

                if (documentSnapshot.exists() && documentSnapshot != null && number_of_steps > 0 ){
                    data.setDate(date);
                    int numb = data.getNum();

                    Log.d("ıntent1" ,String.valueOf(data.getNum()));
                    data.setNum(numb++);
                    Log.d("ıntent1" ,String.valueOf(data.getNum()));

                    ref.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("ıntent","database yazıldı");

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
