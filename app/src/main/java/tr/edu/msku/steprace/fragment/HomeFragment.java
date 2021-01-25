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
import tr.edu.msku.steprace.model.User;


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
    Calendar calendar = Calendar.getInstance();
    DocumentReference mUser = db.collection("Users").document(user_id);
    private User currentUser;
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

        };
        getSteps();
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
    private void getSteps() {
        calendar.add(Calendar.DATE, -7); // 7 day before
        Date oneWeek = calendar.getTime();
        calendar.add(Calendar.DATE, -30); // last 30 day
        Date oneMonth = calendar.getTime();
        getDates(oneWeek);
        final List<String> weekly = getDates(oneWeek);
        final List<String> montly = getDates(oneMonth);
Log.d("sss",weekly.toString());
        mcollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    data = snapshot.toObject(Data.class);
                    if (weekly.contains(data.getDate())) {
                        week_count = week_count + data.getNum();
                        Log.d("sss",String.valueOf(week_count));
                    }
                    if (montly.contains(data.getDate())) {
                        month_count = month_count + data.getNum();

                    }
                    Log.d("sss",String.valueOf(week_count));
                    month_count = month_count + data.getNum();
                    week = getView().findViewById(R.id.step_num_week);
                    month = getView().findViewById(R.id.step_num_month);
                    week.setText(String.valueOf(week_count));
                     month.setText(String.valueOf((month_count)));


                    mUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        User current_user = new User();
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                User user = documentSnapshot.toObject(User.class);
                                Log.d("homee",documentSnapshot.getString("city").toString());
                                current_user.setCity(user.getCity());
                                current_user.setName(user.getName());
                                current_user.setUser_id(user.getUser_id());
                                current_user.setSurname(user.getSurname());
                                current_user.setDateOfBirth(user.getDateOfBirth());
                                current_user.setEmail(user.getEmail());
                                current_user.setMonth_data(0);

                                if (month_count != 0){
                                    current_user.setMonth_data(month_count);
                                    mUser.set(current_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("HomeFragment",String.valueOf(month_count));


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("HomeFragment","error" + e.toString()) ;

                                        }
                                    });


                                }

                            }

                        }
                    });

                }

            }
        });
    }

}



