package tr.edu.msku.steprace.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.adapter.ResultAdapter;
import tr.edu.msku.steprace.model.User;
import tr.edu.msku.steprace.model.UserStore;


public class SearchFragment extends Fragment  {

    private Button search;
    private EditText search_text;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference mUser = db.collection("Users");

    private RecyclerView recyclerView;
    public List<User> users = new ArrayList<>();


    public SearchFragment() {
        // Required empty public constructor

    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    protected  void changeFragment(Fragment fragment) {
        FragmentManager mFragmentManager = getFragmentManager();

        FragmentTransaction fts = mFragmentManager.beginTransaction();
        fts.replace(R.id.container, fragment);
        fts.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        search =view.findViewById(R.id.search_b);
        search_text = (EditText)view.findViewById(R.id.search_text);
        String text = search_text.getText().toString().trim();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = search_text.getText().toString().trim();
                Log.d("search1",text);
                mUser.whereEqualTo("email",text).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot user: queryDocumentSnapshots){

                            User user1 = user.toObject(User.class);
                            String name = user.getString(user1.getName());
                            String surname = user.getString(user1.getSurname());
                            String city = "ankara";
                            users.add(new User(name,surname,city));
                            Log.d("search1",users.get(0).toString());
                        }

                        //Log.d("search1",users.get(0).toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                //Log.d("search1",users.get(0).toString());


//                users.add(new User("John", "WICK","İstanbul","2"));
//                users.add(new User("XXXX2222", "YYYY222","İzmir","13"));
//                users.add(new User("XXXXX", "YYYY","Ankara","23"));
//                users.add(new User("XXXX111", "YYYY1111","İstanbul","1ewds"));
//                users.add(new User("XXXX2222", "YYYY222","İzmir","dfdg"));



                UserStore userStore = new UserStore();
                userStore.setUsers(users);
                recyclerView = getView().findViewById(R.id.recyclerview_result);
                ResultAdapter resultAdapter = new ResultAdapter(userStore.getUsers());
                recyclerView.setAdapter(resultAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);



            }
        });

        return view;
    }


}