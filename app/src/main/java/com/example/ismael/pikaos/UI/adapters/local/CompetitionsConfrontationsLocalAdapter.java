package com.example.ismael.pikaos.UI.adapters.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.data.model.local.CupConfrontationsLocal;

import java.util.ArrayList;

public class CompetitionsConfrontationsLocalAdapter extends RecyclerView.Adapter<CompetitionsConfrontationsLocalAdapter.CompetitionConfrontationsLocalHolder> {

    private Context context;
    private ArrayList<CupConfrontationsLocal> confrontations;
    private View.OnClickListener listener;



    public CompetitionsConfrontationsLocalAdapter(Context context, ArrayList<CupConfrontationsLocal> confrontations) {
        this.context = context;
        this.confrontations = confrontations;
    }

    /**
     * Establece un listener onClick para cada item
     * @param listener
     */
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    /**
     * Actualiza el adapter
     * @param newConfrontations
     */
    public void updateAdapter(ArrayList<CupConfrontationsLocal> newConfrontations){
        confrontations.clear();
        confrontations.addAll(newConfrontations);
        notifyDataSetChanged();
    }

    /**
     * Devuelve el item del arrayList interno del adapter dada una posición
     * @param position
     * @return
     */
    public CupConfrontationsLocal getitem(int position) {
        return confrontations.get(position);
    }

    @NonNull
    @Override
    public CompetitionConfrontationsLocalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_confrontation,parent,false);
        CompetitionConfrontationsLocalHolder holder = new CompetitionConfrontationsLocalHolder(view);
        view.setOnClickListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompetitionConfrontationsLocalHolder holder, int position) {
        CupConfrontationsLocal tmpConfrontation = confrontations.get(position);

        holder.playerOne.setText(tmpConfrontation.getPlayerOne());

        String playerTwo = tmpConfrontation.getPlayerTwo();
        if(playerTwo.isEmpty())
            holder.playerTwo.setText("Sin enfrentamiento");
        else
            holder.playerTwo.setText(playerTwo);

        if(tmpConfrontation.getWinner().equals(tmpConfrontation.getPlayerOne())) {
            holder.playerOne.setTextColor(context.getResources().getColor(R.color.competitionStatusCompeting));
            holder.playerTwo.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
        }

        if(tmpConfrontation.getWinner().equals(tmpConfrontation.getPlayerTwo())) {
            holder.playerOne.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
            holder.playerTwo.setTextColor(context.getResources().getColor(R.color.competitionStatusCompeting));
        }

        if(tmpConfrontation.getWinner().isEmpty()){
            holder.playerOne.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
            holder.playerTwo.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
        }


    }

    @Override
    public int getItemCount() {
        return confrontations.size();
    }


    class CompetitionConfrontationsLocalHolder extends RecyclerView.ViewHolder{
        TextView playerOne;
        TextView playerTwo;


        public CompetitionConfrontationsLocalHolder(View itemView) {
            super(itemView);
            playerOne = itemView.findViewById(R.id.tvPlayerOneLocal);
            playerTwo = itemView.findViewById(R.id.tvPlayerTwoLocal);
        }
    }
}
