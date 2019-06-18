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
import com.example.ismael.pikaos.data.model.online.Friend;
import com.example.ismael.pikaos.data.model.online.FriendRequest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RvFriendRequestAdapter extends RecyclerView.Adapter<RvFriendRequestAdapter.FriendsRequestsViewHolder> {

    private List<FriendRequest> requests;
    private Context context;

    private View.OnClickListener onClickListener;

    public RvFriendRequestAdapter(Context context, List<FriendRequest> requests){
        this.requests = requests;
        this.context = context;

    }


    public void updateAdapter(int id){
        for (int i=0;i < requests.size();i++){
            if(requests.get(i).getId()==id){
                requests.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.onClickListener = listener;
    }

    public FriendRequest getitem(int position) {
        return requests.get(position);
    }


    @NonNull
    @Override
    public FriendsRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_friend_request, parent,false);
        RvFriendRequestAdapter.FriendsRequestsViewHolder holder = new FriendsRequestsViewHolder(view);
        view.setOnClickListener(onClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsRequestsViewHolder holder, int position) {

        FriendRequest tmpRequest = requests.get(position);
        holder.name.setText(tmpRequest.getName());
        if(tmpRequest.getAvatar()!=null)
            Picasso.get().load(tmpRequest.getAvatar()).into(holder.avatar);
        else
            holder.avatar.setImageResource(R.mipmap.default_avatar);

    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    protected class FriendsRequestsViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView name;

        public FriendsRequestsViewHolder(View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.imgFriendRequestAvatar);
            name = itemView.findViewById(R.id.tvFriendRequestName);
        }
    }
}
