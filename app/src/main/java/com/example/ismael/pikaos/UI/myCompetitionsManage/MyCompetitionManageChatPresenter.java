package com.example.ismael.pikaos.UI.myCompetitionsManage;

import com.example.ismael.pikaos.data.model.online.Message;

import java.util.List;

public class MyCompetitionManageChatPresenter implements MyCompetitionManageChatContract.presenter, MyCompetitionManageInteractorImpl.manageChat {

    private MyCompetitionsManageChatView view;
    private MyCompetitionManageInteractorImpl interactor;

    public MyCompetitionManageChatPresenter(MyCompetitionsManageChatView view){
        this.view = view;
        interactor = new MyCompetitionManageInteractorImpl();
    }

    //CONTRACT
    @Override
    public void getMessages(int chatId) {
        view.titleLoading();
        interactor.getAllMessages(this,chatId);
    }


    //INTERACTOR
    @Override
    public void onSuccess(List<Message> messages) {
        view.setMessages(messages);
        view.setTitleFragment();
    }

    @Override
    public void onError(int error) {
        view.setMessageError(error);
        view.setTitleFragment();
    }
}
