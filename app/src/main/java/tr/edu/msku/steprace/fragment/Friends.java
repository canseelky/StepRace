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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

import tr.edu.msku.steprace.MainActivity;
import tr.edu.msku.steprace.adapter.FriendsAdapter;
import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.model.Friend;
import tr.edu.msku.steprace.model.User;


public class Friends extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private String user_id = mFirebaseAuth.getCurrentUser().getUid();
    private ArrayList<String> friendsid = new ArrayList<>();
    private List<Friend> friends = new ArrayList<>();
    private  LinearLayoutManager layoutManager;
    private FriendsAdapter friendsAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Friend friend;
    private CollectionReference mUserReference= db.collection("Users").document(
            user_id).collection("friend"); private CollectionReference mCollectionRef = db.collection("Friends").document(
            user_id).collection("friend");
   private CollectionReference mUser = db.collection("Users");
    private CollectionReference mUserdata = db.collection("Users");

    public Friends() {
        // Required empty public constructor
    }

    public static Friends newInstance(String param1, String param2) {
        Friends fragment = new Friends();
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
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        Query query = mUser.orderBy("name").limit(3);
        getFriendsId(new mCallback() {
            //get list
            @Override
            public void onCallback(final ArrayList<String> list) {
                mUser.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    User user1;
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot user : queryDocumentSnapshots){
                            user1 = user.toObject(User.class);
                            if (user.getId() != null){
                                String id = user.getId().trim();
                                for(int i =0; i<list.size();i++){
                                    //Log.d("friends", String.valueOf((list.get(i).trim().equals(id))));
                                    if ((list.get(i).trim().equals(id))){
                                        //Log.d("friends",friends.toString());
                                        //Log.d("friends","same");
                                        friends.add(new Friend(user1.getName(),user1.getSurname()));
                                        //Log.d("friends",friends.get(0).getName());
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                        }
                        recyclerView = getView().findViewById(R.id.recyclerView_friends);
                        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        friendsAdapter = new FriendsAdapter(friends);
                        FriendsAdapter adapter = new FriendsAdapter(friends);
                        recyclerView.setHasFixedSize(true);
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

    @Override
    public  void onStart() {
        super.onStart();


    }

    private void getFriendsId(final mCallback mCallback){
        mCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    friend = snapshot.toObject(Friend.class);
                    //Log.d("Friend id", snapshot.getId());
                    friendsid.add(snapshot.getId());

                }
              mCallback.onCallback(friendsid);
            }});

    }



private interface mCallback{
        void onCallback(ArrayList<String> list);
}



}