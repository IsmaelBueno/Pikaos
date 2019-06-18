package com.example.ismael.pikaos.UI.chat;

import com.example.ismael.pikaos.data.model.online.Message;

import java.util.List;

public class ChatPresenter implements ChatContract.presenter,ChatInteractorImpl.ChatInteractor {

    private ChatContract.view view;
    private ChatInteractorImpl interactor;


    public ChatPresenter(ChatContract.view view){
        this.view = view;
        interactor = new ChatInteractorImpl();
    }

    //CONTRACT
    @Override
    public void getMessages(int user) {
        interactor.getMessages(this,user);
    }

    //INTERACTOR
    @Override
    public void onMessages(List<Message> messages) {
        view.setMessages(messages);
    }

    @Override
    public void onError(int error) {
        view.messageError(error);
    }
}
