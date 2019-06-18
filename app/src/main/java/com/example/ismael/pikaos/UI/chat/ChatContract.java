package com.example.ismael.pikaos.UI.chat;

import com.example.ismael.pikaos.data.model.online.Message;

import java.util.List;

public interface ChatContract {
    interface view{
        void setMessages(List<Message> messages);
        void messageError(int message);
    }

    interface presenter{
        void getMessages(int user);
    }
}
