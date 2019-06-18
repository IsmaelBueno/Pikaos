package com.example.ismael.pikaos.UI.adapters.online;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class RvChatGroupAdapter extends RecyclerView.Adapter<RvChatGroupAdapter.ChatGroupViewHolder> {

    private List<Message> messages;
    private Context context;

    public RvChatGroupAdapter(Context context,List<Message> messages){
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_message_group, parent,false);
        ChatGroupViewHolder holder = new ChatGroupViewHolder(view);

        return holder;
    }

    public void addMessage(Message message){
        messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ChatGroupViewHolder holder, int position) {
        Message tmpMessage = messages.get(position);

          holder.tvMessage.setText(tmpMessage.getText());
        holder.tvHour.setText(tmpMessage.getChour().substring(0,5));

        if(tmpMessage.getCfrom().equals(PikaosApplication.userName)) {
            holder.rlChat.setGravity(Gravity.END);
            holder.tvMessage.setBackground(context.getResources().getDrawable(R.drawable.chat2));
            holder.tvName.setVisibility(View.GONE);
        }
        else {
            holder.rlChat.setGravity(Gravity.START);
            holder.tvMessage.setBackground(context.getResources().getDrawable(R.drawable.chat));
            holder.tvName.setText(tmpMessage.getCfrom());
            holder.tvName.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    protected class ChatGroupViewHolder extends RecyclerView.ViewHolder{

        TextView tvHour;
        TextView tvMessage;
        TextView tvName;
        RelativeLayout rlChat;


        public ChatGroupViewHolder(View itemView) {
            super(itemView);

            tvHour = itemView.findViewById(R.id.tvHourChatGroup);
            tvMessage = itemView.findViewById(R.id.tvMessageChatGroup);
            rlChat = itemView.findViewById(R.id.ll_message);
            tvName = itemView.findViewById(R.id.tvNameChatGroup);
        }
    }
}