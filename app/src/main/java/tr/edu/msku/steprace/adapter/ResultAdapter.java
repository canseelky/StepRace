package tr.edu.msku.steprace.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.activity.SendFriendRequest;
import tr.edu.msku.steprace.model.User;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private List<User> users = new ArrayList<>();
    private String TAG = getClass().getSimpleName();
    private Button button_send;

    public ResultAdapter(List<User> users) {

        this.users = users;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row,parent,false);
       ViewHolder viewHolder = new ViewHolder(view);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"in onBindViewHolder");
        holder.imageView.setImageResource(R.drawable.friends);
        holder.name.setText(users.get(position).getName());
        holder.surname.setText(users.get(position).getSurname());
       holder.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendFriendRequest.SendRequest(v.getContext(),position);
                Log.d("User :",users.get(position).getName());

            }
        });

    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView name;
        private TextView surname;
        private ImageButton sendButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView3);
            name = (TextView)itemView.findViewById(R.id.name);
            surname = itemView.findViewById(R.id.surname);
            sendButton = itemView.findViewById(R.id.imageButton);
        }

}

}