package com.example.ismael.pikaos.UI.adapters.online;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.ismael.pikaos.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RvAvatarsAdapter extends RecyclerView.Adapter<RvAvatarsAdapter.AvatarHolder> {

    private List<String> avatars;
    private Context context;

    private View.OnClickListener onClickListener;

    public RvAvatarsAdapter(Context context, List<String> avatars){
        this.avatars = avatars;
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.onClickListener = listener;
    }

    public String getitem(int position) {
        return avatars.get(position);
    }

    @NonNull
    @Override
    public AvatarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_avatar, parent,false);
        RvAvatarsAdapter.AvatarHolder holder = new AvatarHolder(view);
        view.setOnClickListener(onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarHolder holder, int position) {

        String urlAvatar = avatars.get(position);
        Picasso.get().load(urlAvatar).into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return avatars.size();
    }

    protected class AvatarHolder extends RecyclerView.ViewHolder{

        ImageView imgAvatar;


        public AvatarHolder(View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.imageItemAvatar);
        }
    }
}
