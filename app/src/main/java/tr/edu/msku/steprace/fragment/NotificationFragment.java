package tr.edu.msku.steprace.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.adapter.NotificationsAdapter;
import tr.edu.msku.steprace.model.User;
import tr.edu.msku.steprace.model.UserStore;

/**
 * A fragment representing a list of Items.
 */
public class NotificationFragment extends Fragment {

    private Button getBtn;
    private ImageButton acceptBtn;
    private ImageButton cancelBtn;
    private TextView name,surname,city;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  FirebaseUser user;
    private CollectionReference mUser = db.collection("Users");
    private List<User> users = new ArrayList<>();
    private  LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<User> requests = new ArrayList<User>();
    private ArrayList<String> requests_id = new ArrayList<>();
    private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private CollectionReference mCollectionRef = db.collection("Notifications").document(user_id)
            .collection("requests");
    private User request;


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;


    public NotificationFragment() {
    }



    public static NotificationFragment newInstance(int columnCount) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        getRequestId(new mCallback() {
            //get list
            @Override
            public void onCallback(final List<String> list) {
                mUser.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    User user1;
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot user : queryDocumentSnapshots){
                            user1 = user.toObject(User.class);
                            if (user.getId() != null){
                                String id = user.getId().trim();
                                Log.d("notid1", id);
                                for(int i =0; i<list.size();i++){
                                    if ((list.get(i).trim().equals(id))){
                                        requests.add(new User(user1.getName(),user1.getSurname()));
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                        }

                        UserStore userStore = new UserStore();
                        userStore.setUsers(requests);
                        recyclerView = getView().findViewById(R.id.recyclerview_notification);
                        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        NotificationsAdapter adapter = new NotificationsAdapter(userStore.getUsers());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);


                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });


        return view;
    }



    private void getRequestId(final mCallback mCallback){

        mCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    //request = snapshot.toObject(User.class);
                    Log.d("notid", snapshot.getId());
                    requests_id.add(snapshot.getId());

                }
                mCallback.onCallback(requests_id);
            }});

    }



    private interface mCallback{
        void onCallback(List<String> list);
    }



}
