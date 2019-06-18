package com.example.ismael.pikaos.UI.adapters.online;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.data.model.local.CupConfrontationsLocal;
import com.example.ismael.pikaos.data.model.online.Friend;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RvFriendListAdapter extends RecyclerView.Adapter<RvFriendListAdapter.FriendsListViewHolder> {

    private List<Friend> friends;
    private Context context;

    private View.OnLongClickListener onLongClickListener;
    private View.OnClickListener onClickListener;

    public RvFriendListAdapter(Context context, List<Friend> friends){
        this.friends = friends;
        this.context = context;

    }

    public void setOnClickListener(View.OnClickListener listener){
        this.onClickListener = listener;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener){
        this.onLongClickListener = listener;
    }

    public Friend getitem(int position) {
        return friends.get(position);
    }


    public void updateAdapter(int id){

        for (int i=0;i < friends.size();i++){
            if(friends.get(i).getId()==id){
                friends.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_friend, parent,false);
        RvFriendListAdapter.FriendsListViewHolder holder = new FriendsListViewHolder(view);

        view.setOnClickListener(onClickListener);
        view.setOnLongClickListener(onLongClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsListViewHolder holder, int position) {

        Friend tmpFriend = friends.get(position);
        holder.name.setText(tmpFriend.getName());

        //STATUS
        if(tmpFriend.getStatus()!=null)
            holder.status.setText(tmpFriend.getStatus());
        else
            holder.status.setText("Sin estado");

        //AVATAR
        if(tmpFriend.getAvatar()!=null)
            Picasso.get().load(tmpFriend.getAvatar()).into(holder.avatar);
        else
            holder.avatar.setImageResource(R.mipmap.default_avatar);

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    protected class FriendsListViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView name;
        TextView status;

        public FriendsListViewHolder(View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.imgFriendAvatar);
            name = itemView.findViewById(R.id.tvFriendName);
            status = itemView.findViewById(R.id.tvFriendStatus);
        }
    }
}
