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
import com.squareup.picasso.Picasso;

public class RvTeamMembersAdapter extends RecyclerView.Adapter<RvTeamMembersAdapter.RvTeamHolder> {

    private String[]members;
    private Context context;
    private String captain;

    public RvTeamMembersAdapter(Context context,String[] members,String captain){
        this.context = context;
        this.members = members;
        this.captain = captain;
    }


    @NonNull
    @Override
    public RvTeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_list_members_team, parent,false);
        RvTeamHolder holder = new RvTeamHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvTeamHolder holder, int position) {
        String tmpMember = members[position];

        holder.tvMember.setText(tmpMember);

        if(captain.equals(tmpMember))
            Picasso.get().load(R.mipmap.ic_admin_red).into(holder.imgAdmin);
    }

    @Override
    public int getItemCount() {
        return members.length;
    }

    protected class RvTeamHolder extends RecyclerView.ViewHolder{

        ImageView imgAdmin;
        TextView tvMember;


        public RvTeamHolder(View itemView) {
            super(itemView);

            imgAdmin = itemView.findViewById(R.id.imgMembersTeamAdminItem);
            tvMember = itemView.findViewById(R.id.tvMemberTeamNameItem);
        }
    }
}
