package tr.edu.msku.steprace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.model.User;


public class FriendsAdapter extends RecyclerView.Adapter <FriendsAdapter.ViewHolder2> {
    FirebaseStorage storage = FirebaseStorage.getInstance();

    private static final int LIST = 1;
    private static final int HEADER = 0;
    List<User> friends = new ArrayList<>();
    private View view;
    private ViewHolder2 mViewholder;
    private Context mcontext;
    StorageReference storageRef = storage.getReference();

    public FriendsAdapter(List<User> friends) {
        this.friends = friends;
    }



    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendfragment_header,parent,false);
            mViewholder = new ViewHolder2(view,viewType);
            return mViewholder;

        }
        else if (viewType == LIST){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item,parent,false);
            mViewholder = new ViewHolder2(view,viewType);
            return mViewholder;


        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder, int position) {
        User friend;
        if ( holder.view_type == LIST){
            friend = friends.get(position-1);
            holder.ad.setText(friend.getName());
            holder.soyad.setText(friend.getSurname());


            //holder.profile_photo.setImageResource(R.drawable.user);

            if(friend.getImage() == null){
                holder.profilpp.setImageResource(R.drawable.user);


            }
            else if (friend.getImage() != null ){
                //Picasso.get().load(friend.getImage().toString().into(holder.profilpp);

            }
        }

        else if (holder.view_type == HEADER){
            holder.headername.setText("Name");
            holder.headerstep.setText("Number of Steps");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if( position == 0){
            return HEADER;

        }
        return LIST;

    }

    @Override
    public int getItemCount() {
        return friends.size() + 1;
    }

    public  class ViewHolder2 extends RecyclerView.ViewHolder{
        private int view_type;
        private TextView ad;
        private TextView soyad;
        private TextView headername;
        private TextView headerstep;
        private ImageView profilpp;


        public ViewHolder2(@NonNull View itemView,int viewType) {
            super(itemView);
            if (viewType == LIST){

                ad = itemView.findViewById(R.id.name22);
                soyad = itemView.findViewById(R.id.surname22);
                profilpp = itemView.findViewById(R.id.profil_picture);
                view_type = 1;
            }
          else if(viewType == HEADER){
                headername = itemView.findViewById(R.id.name_header);
                headerstep = itemView.findViewById(R.id.steps_header);
                view_type = 0;
            }
        }
    }
}
