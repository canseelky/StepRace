package tr.edu.msku.steprace.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.adapter.NotificationsAdaptor;
import tr.edu.msku.steprace.model.User;

/**
 * A fragment representing a list of Items.
 */
public class NotificationFragment extends Fragment {

    private List<User> users = new ArrayList<>();
    private  LinearLayoutManager layoutManager;


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotificationFragment() {
    }

    // TODO: Customize parameter initialization
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
        users.add(new User("Jake", "Peralta", "NewYork","erfg"));
        users.add(new User("Mary", "Couper", "Paris","sdf"));
        users.add(new User("Kate", "Darvis", "London","qwedfr"));


        // Set the adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_notification);
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);


        NotificationsAdaptor adapter = new NotificationsAdaptor(users);
        NotificationsAdaptor adapter1 = new NotificationsAdaptor(users);

        //recyclerView.setAdapter(friendAdapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        return view;
    }
}

