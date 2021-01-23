package tr.edu.msku.steprace.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.adapter.ResultAdapter;
import tr.edu.msku.steprace.model.UserStore;
import tr.edu.msku.steprace.model.User;


public class SearchResult extends Fragment {


    private RecyclerView recyclerView;
    public List <User> users = new ArrayList<>();



    public SearchResult() {

    }

    public static SearchResult newInstance(String param1, String param2) {
        SearchResult fragment = new SearchResult();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        users.add(new User("John", "WICK","İstanbul","2"));
        users.add(new User("XXXX2222", "YYYY222","İzmir","13"));
        users.add(new User("XXXXX", "YYYY","Ankara","23"));
        users.add(new User("XXXX111", "YYYY1111","İstanbul","1ewds"));
        users.add(new User("XXXX2222", "YYYY222","İzmir","dfdg"));

        UserStore userStore = new UserStore();
        userStore.setUsers(users);
        recyclerView = view.findViewById(R.id.recyclerview_result);
        ResultAdapter resultAdapter = new ResultAdapter(userStore.getUsers());
        recyclerView.setAdapter(resultAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }
}