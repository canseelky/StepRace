package tr.edu.msku.steprace.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.model.Friend;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolderFriends> {
    private List<Friend> friends = new ArrayList<>();
    private View view;
    private ViewHolderFriends mViewholder;

    public FriendAdapter(List<Friend> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolderFriends onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item,parent,false);
        mViewholder = new ViewHolderFriends(view);
        return mViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFriends holder, int position) {
        Friend friend = friends.get(position);
        holder.name.setText(friend.getName());
        holder.surname.setText(friend.getSurname());

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public static class ViewHolderFriends extends RecyclerView.ViewHolder{
    TextView name;
    TextView surname;

    public ViewHolderFriends(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.name22);
        surname = (TextView)itemView.findViewById(R.id.surname22);


    }
}

}
