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
import com.example.ismael.pikaos.data.model.online.FriendRequest;
import com.example.ismael.pikaos.data.model.online.TeamRequest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RvTeamRequestAdapter extends RecyclerView.Adapter<RvTeamRequestAdapter.TeamRequestHolder>{

    private List<TeamRequest> requests;
    private Context context;

    private View.OnClickListener onClickListener;

    public RvTeamRequestAdapter(Context context, List<TeamRequest> requests){
        this.requests = requests;
        this.context = context;
    }


    public void deleteRequest(int id){
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

    public TeamRequest getitem(int position) {
        return requests.get(position);
    }

    @NonNull
    @Override
    public TeamRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_friend_request, parent,false);
        TeamRequestHolder holder = new TeamRequestHolder(view);
        view.setOnClickListener(onClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamRequestHolder holder, int position) {

        TeamRequest tmpRequest = requests.get(position);

        holder.name.setText(tmpRequest.getName());
        Picasso.get().load("http://157.230.114.223/logos/"+tmpRequest.getLogo()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    protected class TeamRequestHolder extends RecyclerView.ViewHolder{

        ImageView logo;
        TextView name;

        public TeamRequestHolder(View itemView) {
            super(itemView);

            logo = itemView.findViewById(R.id.imgFriendRequestAvatar);
            name = itemView.findViewById(R.id.tvFriendRequestName);
        }
    }
}
