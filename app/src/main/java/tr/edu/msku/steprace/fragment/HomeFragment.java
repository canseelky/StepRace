package tr.edu.msku.steprace.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;
import tr.edu.msku.steprace.MainActivity;
import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.model.Data;


@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    private TextView today;
    private TextView month;
    private TextView week;
    SimpleDateFormat simpledate = new SimpleDateFormat("dd-MM-yyyy");
    String today_date= simpledate.format(new Date());
    private Data data;
    private ArrayList<Integer> datalist = new ArrayList();
    private int numOfStep = 0;
    Calendar c = Calendar.getInstance();
   Calendar calendar = Calendar.getInstance();
    private String oneWeek;
    private String oneMonth;
    private DocumentReference ref = db.collection("Data").document(user_id).collection("data").document(today_date);
    private CollectionReference mcollectionReference = db.collection("Data").document(user_id).collection("data");


    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_home, container, false);

       week = view.findViewById(R.id.step_num_week);
       month = view.findViewById(R.id.step_num_month);
       Data data1 = new Data("1/12/1232",12);
       ref.set(data1);
        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        today = getView().findViewById(R.id.step_num_day);

        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    Toast.makeText(getContext(),"Some error occured !", Toast.LENGTH_LONG).show();

                }

                if( value.exists() && value != null){
                    int a = ((Long) value.get("num")).intValue();
                    today.setText(String.valueOf(a));

                }

            }
        });
        update();



        //getSteps();
    }

    @Override
    public void onStop() {
        super.onStop();
        //STOP snapshot listener !!!!!
    }


private void update(){
        week = getView().findViewById(R.id.step_num_week);
    month= getView().findViewById(R.id.step_num_month);
    today = getView().findViewById(R.id.step_num_day);
    calendar.add(Calendar.DATE,-7); // 7 day before
    oneWeek = calendar.getTime().toString();
    calendar.add(Calendar.DATE,-30); // last 30 day
    oneMonth = calendar.getTime().toString();
    week.setText(String.valueOf(getData(oneWeek)));
    month.setText(String.valueOf(getData(oneMonth)));


}


private int getData(final String date){
        mcollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    data = snapshot.toObject(Data.class);

                    Log.d("home",data.getDate() + "  " +data.getNum());

                    while (data.getDate() == date){
                       numOfStep = numOfStep + data.getNum();
                        //Log.d("home",data.getDate() + "  " +data.getNum());

                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("Home Fragment",e.toString());

            }
        });


        return numOfStep;

}


}