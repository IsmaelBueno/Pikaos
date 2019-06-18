package com.example.ismael.pikaos.UI.adapters.online;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.data.model.online.PlayerPoint;

import java.util.List;

public class RvCompetitionsTableAdapter extends RecyclerView.Adapter<RvCompetitionsTableAdapter.RvCompetitionsTableHolder> {

    private Context context;
    private List<PlayerPoint> listPoints;

    public RvCompetitionsTableAdapter(Context context, List<PlayerPoint> listPoints){
        this.context = context;
        this.listPoints = listPoints;
    }


    @NonNull
    @Override
    public RvCompetitionsTableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_list_point_league, parent,false);
        RvCompetitionsTableHolder holder = new RvCompetitionsTableHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvCompetitionsTableHolder holder, int position) {
        PlayerPoint tmp = listPoints.get(position);

        holder.player.setText(tmp.getName());
        holder.points.setText(String.valueOf(tmp.getPoints()));
        holder.position.setText(String.valueOf(position+1));

    }

    @Override
    public int getItemCount() {
        return listPoints.size();
    }

    protected class RvCompetitionsTableHolder extends RecyclerView.ViewHolder{

        TextView position;
        TextView player;
        TextView points;

        public RvCompetitionsTableHolder(View itemView) {
            super(itemView);

            position = itemView.findViewById(R.id.tvPointsPosition);
            player = itemView.findViewById(R.id.tvPointsPlayer);
            points = itemView.findViewById(R.id.tvPointsPoints);
        }
    }
}
