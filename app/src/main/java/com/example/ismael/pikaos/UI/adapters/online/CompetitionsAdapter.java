package com.example.ismael.pikaos.UI.adapters.online;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.Utils;
import com.example.ismael.pikaos.data.model.online.Competition;

import java.util.ArrayList;
import java.util.List;

public class CompetitionsAdapter extends RecyclerView.Adapter<CompetitionsAdapter.CompetitionsViewHolder> implements View.OnClickListener{

    private View.OnClickListener listener;//onClick

    private List<Competition> competitionsFilter;
    private List<Competition> competitions;
    private Context context;


    public CompetitionsAdapter(Context context, List<Competition> competitions){
        this.context = context;
        this.competitions = competitions;
        this.competitionsFilter = new ArrayList<>();
        competitionsFilter.addAll(competitions);
    }

    @NonNull
    @Override
    public CompetitionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_list_competitions, parent,false);
        CompetitionsViewHolder competicionViewHolder = new CompetitionsViewHolder(view);

        view.setOnClickListener(this);//onClick

        return competicionViewHolder;
    }

    //onClick
    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }


    //Para colocar los listener desde fuera
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    //onClick/onLongClick para obtener el item actual
    public Competition getItem(int posicion) {
        return competitions.get(posicion);
    }

    @Override
    public void onBindViewHolder(@NonNull CompetitionsViewHolder holder, int position) {
        //Mapeado de datos

        Competition tmpCompetition = competitions.get(position);


        holder.tvName.setText(tmpCompetition.getName());
        holder.tvType.setText(Utils.getTypeCompetitionFromDB(tmpCompetition.getType()));
        holder.tvAdmin.setText("Admin: " + tmpCompetition.getAdmin());
        holder.tvGame.setText(tmpCompetition.getGame());
        if(tmpCompetition.isPrivate()==1)
            holder.imgPublic.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_private_game_red));
        else
            holder.imgPublic.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_public_game_green));
    }

    @Override
    public int getItemCount() {
        return competitions.size();
    }

    public void deleteItem(int idCompetition) {
        for (int i = 0; i < competitions.size(); i++) {
            if(competitions.get(i).getId()==idCompetition)
                competitions.remove(i);
        }

        notifyDataSetChanged();
    }

    public void filter(String text) {
        competitions.clear();
        if(text.isEmpty()){
            competitions.addAll(competitionsFilter);
        } else{
            for(Competition item: competitionsFilter){
                if(item.getName().toLowerCase().contains(text.toLowerCase()))
                    competitions.add(item);
            }
        }
        notifyDataSetChanged();
    }


    public class CompetitionsViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvType;
        TextView tvAdmin;
        LinearLayout lylBar;
        TextView tvGame;
        ImageView imgPublic;

        public CompetitionsViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameItem);
            tvType = itemView.findViewById(R.id.tvTypeCompetitionItem);
            tvAdmin = itemView.findViewById(R.id.tvAdminItem);
            lylBar = itemView.findViewById(R.id.LyLBarItem);
            tvGame = lylBar.findViewById(R.id.tvVideoGameItem);
            imgPublic = lylBar.findViewById(R.id.imgPublicItem);
        }
    }

}
