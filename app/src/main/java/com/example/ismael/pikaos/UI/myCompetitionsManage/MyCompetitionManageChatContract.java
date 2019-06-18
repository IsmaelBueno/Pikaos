package com.example.ismael.pikaos.UI.myCompetitionsManage;

import com.example.ismael.pikaos.data.model.online.Message;

import java.util.List;

public interface MyCompetitionManageChatContract {
    interface view{
        void setMessageError(int error);
        void setMessages(List<Message> messages);
    }

    interface presenter{
        void getMessages(int chatId);
    }
}
