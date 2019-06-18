package com.example.ismael.pikaos.UI.adapters.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.data.model.local.CompetitionLocalView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class CompetitionsListLocalAdapter extends RecyclerView.Adapter<CompetitionsListLocalAdapter.CompetitionLocalViewHolder> implements View.OnClickListener,View.OnLongClickListener{

    private View.OnClickListener listener;//onClick
    private View.OnLongClickListener listenerLongClick;//onLongClick


    private ArrayList<CompetitionLocalView> competitions;
    private ArrayList<CompetitionLocalView> competitionsCopy;
    private Context context;

    public CompetitionsListLocalAdapter(Context context, ArrayList competitions){
        this.context = context;
        this.competitions = competitions;
        this.competitionsCopy = new ArrayList<>();
        competitionsCopy.addAll(competitions);
    }


    /**
     * Ordena el adapter por nombre
     */
    public void orderByName(){
        Collections.sort(competitions);
        notifyDataSetChanged();
    }

    /**
     * Ordena el adapter por fecha de creación
     */
    public void orderByDate(){

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Collections.sort(competitions, new Comparator<CompetitionLocalView>() {
            @Override
            public int compare(CompetitionLocalView o1, CompetitionLocalView o2) {
                try {
                    Date date1 = format.parse(o1.getDate());
                    Date date2 = format.parse(o2.getDate());
                    return date1.compareTo(date2);
                }catch (ParseException e){
                    return 0;
                }
            }
        });

        notifyDataSetChanged();
    }

    /**
     * Filtro para la búsqueda, hace uso del arrayList interno competitionsCopy
     * @param text
     */
    public void filter(String text) {
        competitions.clear();
        if(text.isEmpty()){
            competitions.addAll(competitionsCopy);
        } else{
            for(CompetitionLocalView item: competitionsCopy){
                if(item.getName().toLowerCase().contains(text.toLowerCase()))
                    competitions.add(item);
            }
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CompetitionLocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_list_competitions_local, parent,false);
        CompetitionLocalViewHolder myCompetitionsViewHolder = new CompetitionLocalViewHolder(view);

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

        return myCompetitionsViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CompetitionLocalViewHolder holder, int position) {

        CompetitionLocalView tmpCompetitionLocalView = competitions.get(position);

        holder.tvName.setText(tmpCompetitionLocalView.getName());
        holder.tvType.setText(tmpCompetitionLocalView.getType());
        holder.tvPlayer.setText("Participantes: " +tmpCompetitionLocalView.getnPlayers());

        if(tmpCompetitionLocalView.isFinished()) {
            holder.tvStatusInfo.setTextColor(context.getResources().getColor(R.color.competitionStatusCancelledFinished));
            holder.tvStatusInfo.setText("Finalizada");
        }else{
            holder.tvStatusInfo.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.tvStatusInfo.setText(tmpCompetitionLocalView.getNameRound());
        }
    }


    @Override
    public int getItemCount() {
        return competitions.size();
    }

    public class CompetitionLocalViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvType;
        TextView tvPlayer;
        TextView tvStatusInfo;

        public CompetitionLocalViewHolder(View itemView) {
            super(itemView);

            tvStatusInfo = itemView.findViewById(R.id.tvStatusInfoCompetitionLocal);
            tvName = itemView.findViewById(R.id.tvNameCompetitionLocal);
            tvType = itemView.findViewById(R.id.tvTypeCompetitionLocal);
            tvPlayer = itemView.findViewById(R.id.tvPlayersCompetitionLocal);

        }
    }

    public CompetitionLocalView getItem(int posicion) {
        return competitions.get(posicion);
    }

    //Click
    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }

    //Para colocar los listener desde fuera
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    //LongClick
    @Override
    public boolean onLongClick(View v) {
        listenerLongClick.onLongClick(v);
        return true;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener){this.listenerLongClick = listener;}
}
