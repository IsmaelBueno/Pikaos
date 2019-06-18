package com.example.ismael.pikaos.UI.chatTeam;

import com.example.ismael.pikaos.data.model.online.Message;

import java.util.List;

public interface ChatTeamContract {
    interface view{
        void setMessages(List<Message> messages);
        void messageError(int message);
    }

    interface presenter {
        void getMessages();
    }
}
