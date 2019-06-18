package com.example.ismael.pikaos.UI.adapters.online;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.data.model.online.Message;

import java.util.List;

public class RvChatAdapter extends RecyclerView.Adapter<RvChatAdapter.ChatViewHolder> {

    private List<Message> messages;
    private Context context;

    public RvChatAdapter(Context context,List<Message> messages){
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_message, parent,false);
        RvChatAdapter.ChatViewHolder holder = new ChatViewHolder(view);

        return holder;
    }

    public void addMessage(Message message){
        messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message tmpMessage = messages.get(position);

        holder.tvMessage.setText(tmpMessage.getText());
        holder.tvHour.setText(tmpMessage.getChour().substring(0,5));

        if(tmpMessage.getCfrom().equals(PikaosApplication.userName)) {
            holder.rlChat.setGravity(Gravity.END);
            holder.tvMessage.setBackground(context.getResources().getDrawable(R.drawable.chat2));
        }
        else {
            holder.rlChat.setGravity(Gravity.START);
            holder.tvMessage.setBackground(context.getResources().getDrawable(R.drawable.chat));
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    protected class ChatViewHolder extends RecyclerView.ViewHolder{

        TextView tvHour;
        TextView tvMessage;
        RelativeLayout rlChat;


        public ChatViewHolder(View itemView) {
            super(itemView);

            tvHour = itemView.findViewById(R.id.tvHourChat);
            tvMessage = itemView.findViewById(R.id.tvMessageChat);
            rlChat = itemView.findViewById(R.id.ll_message);
        }
    }
}
