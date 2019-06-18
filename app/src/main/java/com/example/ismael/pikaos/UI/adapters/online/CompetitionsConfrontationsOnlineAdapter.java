package com.example.ismael.pikaos.UI.adapters.online;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.data.model.local.CupConfrontationsLocal;
import com.example.ismael.pikaos.data.model.online.Confrontation;
import com.example.ismael.pikaos.data.model.online.MyCompetition;

import java.util.ArrayList;
import java.util.List;

public class CompetitionsConfrontationsOnlineAdapter extends RecyclerView.Adapter<CompetitionsConfrontationsOnlineAdapter.CompetitionConfrontationsHolder> {

    private Context context;
    private Confrontation[] confrontations;
    private View.OnClickListener listener;



    public CompetitionsConfrontationsOnlineAdapter(Context context, Confrontation[] confrontationList) {
        this.context = context;
        this.confrontations = confrontationList;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }



    public void updateAdapter(String winner,String playerone,String playertwo){

        for (int i = 0; i < confrontations.length; i++) {
            if(confrontations[i].getPlayer_one().equals(playerone) && confrontations[i].getPlayer_two().equals(playertwo))
                confrontations[i].setWinner(winner);
        }

        notifyDataSetChanged();
    }


    public Confrontation getitem(int position) {
        return confrontations[position];
    }

    @NonNull
    @Override
    public CompetitionConfrontationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_confrontation,parent,false);
        CompetitionConfrontationsHolder holder = new CompetitionConfrontationsHolder(view);
        view.setOnClickListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompetitionConfrontationsHolder holder, int position) {
        Confrontation tmpConfrontation = confrontations[position];

        String playerTwo = tmpConfrontation.getPlayer_two();
        if(playerTwo.equals("0"))
            holder.playerTwo.setText("Sin enfrentamiento");
        else
            holder.playerTwo.setText(playerTwo);

        if(tmpConfrontation.getPlayer_one().equals("0"))
            holder.playerOne.setText("Sin enfrentamiento");
        else
            holder.playerOne.setText(tmpConfrontation.getPlayer_one());

        holder.playerOne.setTypeface(null, Typeface.NORMAL);
        holder.playerTwo.setTypeface(null, Typeface.NORMAL);

        if(tmpConfrontation.getWinner()!=null) {

            //Empate
            if(tmpConfrontation.getWinner().equals("0")){
                holder.playerOne.setTypeface(null, Typeface.BOLD);
                holder.playerTwo.setTypeface(null, Typeface.BOLD);
                holder.playerOne.setTextColor(context.getResources().getColor(R.color.winnerConfrontation));
                holder.playerTwo.setTextColor(context.getResources().getColor(R.color.winnerConfrontation));

            }else {
                //Jugador uno gana
                if (tmpConfrontation.getWinner().equals(tmpConfrontation.getPlayer_one())) {
                    holder.playerOne.setTypeface(null, Typeface.BOLD);
                    holder.playerOne.setTextColor(context.getResources().getColor(R.color.winnerConfrontation));
                    holder.playerTwo.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
                }

                //Jugador dos gana
                if (tmpConfrontation.getWinner().equals(tmpConfrontation.getPlayer_two())) {
                    holder.playerTwo.setTypeface(null, Typeface.BOLD);
                    holder.playerOne.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
                    holder.playerTwo.setTextColor(context.getResources().getColor(R.color.winnerConfrontation));
                }
            }
        }else{
            //Ganador sin establecer
            holder.playerOne.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
            holder.playerTwo.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));
        }

    }

    @Override
    public int getItemCount() {
        return confrontations.length;
    }


    class CompetitionConfrontationsHolder extends RecyclerView.ViewHolder{
        TextView playerOne;
        TextView playerTwo;


        public CompetitionConfrontationsHolder(View itemView) {
            super(itemView);
            playerOne = itemView.findViewById(R.id.tvPlayerOneLocal);
            playerTwo = itemView.findViewById(R.id.tvPlayerTwoLocal);
        }
    }
}
