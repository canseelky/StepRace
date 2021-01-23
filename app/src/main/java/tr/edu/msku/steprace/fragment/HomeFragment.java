package tr.edu.msku.steprace.fragment;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
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
    SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
    String today_date = simpledate.format(new Date());
    private Data data;
    private ArrayList<Integer> datalist = new ArrayList();
    private int numOfStep = 0;
    Calendar c = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    private String oneWeek;
    private String oneMonth;
    private int week_count = 0;
    private int month_count = 0;
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        today = getView().findViewById(R.id.step_num_day);

        //Log.d("current",user_id);
        //get today's step count
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(getContext(), "Some error occured !", Toast.LENGTH_LONG).show();

                }

                if (value.exists() && value != null) {
                    int a = ((Long) value.get("num")).intValue();
                    today.setText(String.valueOf(a));

                }

            }
        });

        //getDates(oneMonth);
        //getDates(oneWeek);
        deneme();

    }

    @Override
    public void onStop() {
        super.onStop();
        //STOP snapshot listener !!!!!
    }


    private List<String> getDates(Date day) {
        List<String> dates = new ArrayList<String>();
        calendar.setTime(day);
        while (calendar.getTime().before(new Date())) {
            Date result = calendar.getTime();
            dates.add(simpledate.format(result));
            calendar.add(Calendar.DATE, 1);
        }
        return dates;

    }
    private void deneme() {
        calendar.add(Calendar.DATE, -7); // 7 day before
        Date oneWeek = calendar.getTime();
        calendar.add(Calendar.DATE, -30); // last 30 day
        Date oneMonth = calendar.getTime();
        getDates(oneWeek);
        final List<String> weekly = getDates(oneWeek);
        final List<String> montly = getDates(oneMonth);

        mcollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    data = snapshot.toObject(Data.class);
                    boolean checkDate = weekly.contains(data.getDate()) || montly.contains(data.getDate());
                    if (weekly.contains(data.getDate())) {
                        week_count = week_count + data.getNum();
                    }
                    if (montly.contains(data.getDate())) {
                        month_count = month_count + data.getNum();

                    }

                    //week.setText(String.valueOf(week_count));
                   // month.setText(String.valueOf((month_count)));
                }


            }
        });
    }





}



