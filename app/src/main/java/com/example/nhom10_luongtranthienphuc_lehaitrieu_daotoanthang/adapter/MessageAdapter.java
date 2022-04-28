package com.example.nhom10_luongtranthienphuc_lehaitrieu_daotoanthang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10_luongtranthienphuc_lehaitrieu_daotoanthang.ChatDetailActivity;
import com.example.nhom10_luongtranthienphuc_lehaitrieu_daotoanthang.R;
import com.example.nhom10_luongtranthienphuc_lehaitrieu_daotoanthang.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<User> mUsers;

    public MessageAdapter(ArrayList<User> users) {
        this.mUsers = users;
    }
    public class ViewHolderUser extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView tvUser, tvLastMess;
        public ViewHolderUser(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profile_image);
            tvUser = itemView.findViewById(R.id.tvName);
            tvLastMess = itemView.findViewById(R.id.tvLastMess);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_user,parent,false);
        return new ViewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = mUsers.get(position);
        ViewHolderUser viewHolderUser =(ViewHolderUser) holder;
        viewHolderUser.tvUser.setText(user.getUsername());
        Picasso.get().load(user.getImage()).placeholder(R.drawable.profile).into(viewHolderUser.circleImageView);


//        Xem tin nhắn gần nhất
        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid() + user.getUserID())
                .orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()){
                            for (DataSnapshot snapshot1: snapshot.getChildren()){
                                viewHolderUser.tvLastMess.setText(snapshot1.child("message").getValue().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        viewHolderUser.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Truyền dữ liệu qua chatdetail
                Intent intent = new Intent(viewHolderUser.itemView.getContext(), ChatDetailActivity.class);
                intent.putExtra("userID", user.getUserID());
                intent.putExtra("username", user.getUsername());

                viewHolderUser.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
