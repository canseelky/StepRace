package tr.edu.msku.steprace.adapter;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import tr.edu.msku.steprace.MainActivity;
import tr.edu.msku.steprace.R;
import tr.edu.msku.steprace.model.User;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private static final int LIST = 1;
    private static final int HEADER = 0;
    private List<User> users = new ArrayList<>();
    private String TAG = getClass().getSimpleName();
    private Button button_send;
    private View view;
    private onUserAdded museradd;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();


    public ResultAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_header,parent,false);
            ViewHolder mViewholder = new ViewHolder(view,viewType);
            return mViewholder;

        }
        else if (viewType == LIST){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row,parent,false);
            ViewHolder mViewholder = new ViewHolder(view,viewType);
            return mViewholder;


        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        if (holder.view_type == LIST){
            final User user = users.get(position -1);
            holder.name.setText(user.getName());
            holder.surname.setText(user.getSurname());
            holder.city.setText(user.getCity());
            holder.imageView.setImageResource(R.drawable.user);
            String id = user.getUser_id();
            DocumentReference ref = db.collection("Image").document(user_id);
            ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        String image = documentSnapshot.getString("image");
                        Picasso.get().load(image).transform(new CropCircleTransformation()).into(holder.imageView);
                    }
                    else {
                        holder.imageView.setImageResource(R.drawable.user);
                        //Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/steprace-c2005.appspot.com/o/135688?alt=media&token=d7c9d113-8a05-4357-a591-96b02da22bbf").into(holder.imageView);
                        Log.d("resultadapter11",user_id);


                    }
                };

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("resultadapter11",e.toString());

                }
            });
            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //museradd.onUserAdd(user.getUser_id());
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("userid",user.getUser_id());
                    String id = user.getUser_id();
                    Log.d("resultadapterid",id);
                    v.getContext().startActivity(intent);

                }
            });

        }
        else if(holder.view_type == HEADER) {

            holder.headername.setText("Results");
            holder.backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //TODO back to search fragment
                }
            });

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
        return users.size()+1;
    }


    public static class  ViewHolder extends RecyclerView.ViewHolder{
        private int view_type;
        private ImageView imageView;
        private TextView name;
        private TextView surname;
        private TextView city;
        private TextView headername;
        private ImageButton backButton;
        private ImageButton addButton;
        public ViewHolder(@NonNull View itemView, int ViewType) {
            super(itemView);
            if (ViewType == LIST){
                name = itemView.findViewById(R.id.name);
                surname = itemView.findViewById(R.id.surname);
                city = itemView.findViewById(R.id.city);
                imageView = itemView.findViewById(R.id.image_search);
                addButton = itemView.findViewById(R.id.send_friendship);
                view_type = 1;
            }
            else if(ViewType== HEADER){
                headername = itemView.findViewById(R.id.result_header);
                backButton= itemView.findViewById(R.id.back_button);
                view_type = 0;
            }

        }

    }


}