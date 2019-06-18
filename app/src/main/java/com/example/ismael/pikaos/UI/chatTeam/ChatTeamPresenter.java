package com.example.ismael.pikaos.UI.chatTeam;

import com.example.ismael.pikaos.UI.chat.ChatInteractorImpl;
import com.example.ismael.pikaos.data.model.online.Message;

import java.util.List;

public class ChatTeamPresenter implements ChatTeamContract.presenter, ChatTeamInteractorImpl.ChatTeamInteractor {

    private ChatTeamActivity view;
    private ChatTeamInteractorImpl interactor;

    public ChatTeamPresenter (ChatTeamActivity view){
        this.view = view;
        this.interactor = new ChatTeamInteractorImpl();
    }

    //CONTRACT
    @Override
    public void getMessages() {
        interactor.getMessages(this);
    }

    //INTERACTOR
    @Override
    public void onSuccess(List<Message> messages) {
        view.setMessages(messages);
    }

    @Override
    public void onError(int error) {
        view.messageError(error);
    }
}
