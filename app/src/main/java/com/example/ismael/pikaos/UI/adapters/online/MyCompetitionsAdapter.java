package com.example.ismael.pikaos.UI.adapters.online;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.Utils;
import com.example.ismael.pikaos.data.model.online.MyCompetition;

import java.util.List;

public class MyCompetitionsAdapter extends RecyclerView.Adapter<MyCompetitionsAdapter.MyCompetitionsViewHolder>{

    private View.OnClickListener listener;

    private List<MyCompetition> myCompetitions;
    private Context context;

    public MyCompetitionsAdapter(Context context,List<MyCompetition> myCompetitions){
        this.context = context;
        this.myCompetitions = myCompetitions;
    }

    @NonNull
    @Override
    public MyCompetitionsAdapter.MyCompetitionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_list_mycompetitions, parent,false);
        MyCompetitionsAdapter.MyCompetitionsViewHolder myCompetitionsViewHolder = new MyCompetitionsAdapter.MyCompetitionsViewHolder(view);

        view.setOnClickListener(listener);

        return myCompetitionsViewHolder;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public MyCompetition getItem(int posicion) {
        return myCompetitions.get(posicion);
    }


    @Override
    public void onBindViewHolder(@NonNull MyCompetitionsAdapter.MyCompetitionsViewHolder holder, int position) {

        MyCompetition tmpCompetition = myCompetitions.get(position);

        if(!tmpCompetition.getState().equals("preparing") && !tmpCompetition.getState().equals("competing")) {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.competitionStatusCancelledFinished));

            String state = tmpCompetition.getState();
            if(state.equals("cancelled"))
                holder.tvStatus.setText("Cancelada");
            if(state.equals("finished"))
                holder.tvStatus.setText("Finalizada");
        }else{
            holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
            holder.tvStatus.setText("Expira: " + tmpCompetition.getExpire().substring(0,10));
        }

        holder.tvName.setText(tmpCompetition.getName());
        holder.tvType.setText(Utils.getTypeCompetitionFromDB(tmpCompetition.getType()));
        holder.tvAdmin.setText("Admin: " + tmpCompetition.getAdmin());
        holder.tvGame.setText(tmpCompetition.getGame());

        holder.tvStatusInfo.setText(getStatusInfoText(tmpCompetition.getState(),tmpCompetition.getType(),
                tmpCompetition.getActualRound(),tmpCompetition.getTotalRounds()));
    }


    private String getStatusInfoText(String stateCompetition, String typeCompetition, int actualRound, int totalRounds){
        if(!stateCompetition.equals("preparing")) {
            if (typeCompetition.equals("ind_cup") || typeCompetition.equals("team_cup")) {
                return Utils.nameOfRoundCup(actualRound, totalRounds);
            } else {
                return "Jornada " + actualRound;
            }
        }else
            return "Preparándose para comenzar";
    }

    @Override
    public int getItemCount() {
        return myCompetitions.size();
    }

    public class MyCompetitionsViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus;
        TextView tvName;
        TextView tvType;
        TextView tvAdmin;
        TextView tvStatusInfo;
        TextView tvGame;

        public MyCompetitionsViewHolder(View itemView) {
            super(itemView);

            tvStatus = itemView.findViewById(R.id.tvStatusCompetitionMyComp);
            tvName = itemView.findViewById(R.id.tvNameCompetitionMyComp);
            tvType = itemView.findViewById(R.id.tvTypeCompetitionMyComp);
            tvAdmin = itemView.findViewById(R.id.tvAdminMyComp);
            tvStatusInfo = itemView.findViewById(R.id.tvStatusInfoMyComp);
            tvGame = itemView.findViewById(R.id.tvVideogameMyComp);

        }
    }
}
