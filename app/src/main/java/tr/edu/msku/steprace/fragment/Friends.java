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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
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




        friends.add(new Friend("Jake", "22000"));
        friends.add(new Friend("Emily", "19500"));



        recyclerView = view.findViewById(R.id.recyclerView_friends);
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        friendsAdapter = new FriendsAdapter(friends);




        FriendsAdapter adapter = new FriendsAdapter(friends);

        //recyclerView.setAdapter(friendAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getFriends();


    }

    private void getFriendsId(final mCallback mCallback){
        mCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    friend = snapshot.toObject(Friend.class);
                    friendsid.add(snapshot.getId());
                    Log.d("Friend id", snapshot.getId());
                }
              mCallback.onCallback(friendsid);
            }});

    }




    private void getFriends(){


     getFriendsId(new mCallback() {
         @Override
         public void onCallback(final ArrayList<String> list) {

             CollectionReference mUserReference= db.collection("Users");

             mUserReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                 User user1;
                 @Override
                 public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                     for (QueryDocumentSnapshot user : queryDocumentSnapshots){

                         user1 = user.toObject(User.class);
                         for (int i = 0; i< list.size(); i++){
                             //Log.d("friends listid ,user id",list.get(i) +list.get(i) );
                             //Log.d("friends user id ",user.getId());
                             if (user.getId().equals(list.get(i))){
                                 Log.d("friends list",friends.toString());
                                 friends.add(new Friend(user1.getName(),user1.getSurname()));
                                 Log.d("friends",user.getString(user1.getName()) + " " +user.getString(user1.getSurname()));

                             }
                         }
                     }
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {

                 }
             });



         }
     });



    }

    private interface mCallback{
        void onCallback(ArrayList<String> list);
}







}