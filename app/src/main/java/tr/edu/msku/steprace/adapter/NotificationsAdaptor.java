package tr.edu.msku.steprace.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.util.ArrayList;
import java.util.List;
import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.model.User;

public class NotificationsAdaptor extends RecyclerView.Adapter<NotificationsAdaptor.ViewHolderNotification>{

    private List<User> users =new ArrayList<>();

    public NotificationsAdaptor(List<User> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public ViewHolderNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_row, parent, false);
        return new NotificationsAdaptor.ViewHolderNotification(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNotification holder, int position) {
        holder.name.setText ( users.get(position).getName());
        holder.surname.setText ( users.get(position).getSurname());
        holder.city.setText ( users.get(position).getCity());
        holder.profile_photo.setImageResource(R.drawable.user);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/steprace-c2005.appspot.com/o/2V3SuNGoRWO5Spz2CmYxhYUvvSp2?alt=media&token=ec33faf6-b3a3-482d-a1ce-1631edc9a46a").into(holder.profile_photo);


    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolderNotification extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView profile_photo;
        public TextView surname;
        public TextView city;
        public ImageButton imgBtn_accept;
        public ImageButton imgBtn_reject;


        public ViewHolderNotification(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            surname= (TextView) itemView.findViewById(R.id.surname);
            city= (TextView) itemView.findViewById(R.id.city);
            profile_photo =(ImageView)itemView.findViewById(R.id.image_search);

        }
    }
}
