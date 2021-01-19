package tr.edu.msku.steprace.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

import tr.edu.msku.steprace.adapter.FriendsAdapter;
import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.model.Friend;


public class Friends extends Fragment {


    private RecyclerView recyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private List<Friend> friends = new ArrayList<>();
    private  LinearLayoutManager layoutManager;
    FriendsAdapter friendsAdapter;



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


         /* for(Friend model : friends) {
            System.out.println(model.getName());
        } */

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
}