package tr.edu.msku.steprace.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.adapter.NotificationsAdaptor;
import tr.edu.msku.steprace.model.User;
import tr.edu.msku.steprace.model.UserStore;

/**
 * A fragment representing a list of Items.
 */
public class NotificationFragment extends Fragment {

    private Button getBtn;
    private ImageButton acceptBtn;
    private ImageButton cancelBtn;
    private EditText name,surname,city;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  FirebaseUser user;
    private CollectionReference mUser = db.collection("Notifications");
    private List<User> users = new ArrayList<>();
    private  LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;


    public NotificationFragment() {
    }


    @SuppressWarnings("unused")
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
        getBtn = view.findViewById(R.id.getBtn);
        acceptBtn =view.findViewById(R.id.imgBtn_accept);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Accept Request");
                alertDialog.setMessage("Are you sure you want to accept the Request?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        DocumentReference docRef = db.collection("Notifications").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("name",name.getText().toString());
                        edited.put("surname",surname.getText().toString());
                        edited.put("city",city.getText().toString());
            }
        });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });
        cancelBtn = view.findViewById(R.id.imgBtn_reject);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Decline Request");
                alertDialog.setMessage("Are you sure you want to decline the Request?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        Map declineMap = new HashMap();

                declineMap.put("Notifications/" + "/" + name + "/" + user.getUid(),null);
                declineMap.put("Notifications/" + "/" + surname + "/" + user.getUid(),null);
                declineMap.put("Notifications/" + "/" + city + "/" + user.getUid(),null);


            }
        });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });

        name=view.findViewById(R.id.name);
        surname=view.findViewById(R.id.surname);
        city=view.findViewById(R.id.city);
        String text_name = name.getText().toString().trim();
        String text_surname = surname.getText().toString().trim();
        String text_city = city.getText().toString().trim();
      /*  users.add(new User("Jake", "Peralta", "NewYork"));
        users.add(new User("Mary", "Couper", "Paris"));
        users.add(new User("Kate", "Darvis", "London"));
*/
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_name = name.getText().toString().trim();
                String text_surname = surname.getText().toString().trim();
                String text_city = city.getText().toString().trim();
                mUser.whereEqualTo("name",text_name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot user: queryDocumentSnapshots){
                        User user1 = user.toObject(User.class);
                        String name = user.getString(user1.getName());
                        String surname = user.getString(user1.getSurname());
                        String city = user.getString(user1.getCity());
                        users.add(new User(name,surname,city));
                    }}
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        // Set the adapter
        UserStore userStore = new UserStore();
        userStore.setUsers(users);
        recyclerView = getView().findViewById(R.id.recyclerview_notification);
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        NotificationsAdaptor adapter = new NotificationsAdaptor(userStore.getUsers());
        //recyclerView.setAdapter(friendAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

