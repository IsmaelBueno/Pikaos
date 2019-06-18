package com.example.ismael.pikaos.UI.myCompetitionsManage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.adapters.online.RvChatGroupAdapter;
import com.example.ismael.pikaos.UI.base.BaseFragment;
import com.example.ismael.pikaos.data.model.online.Message;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.List;

public class MyCompetitionsManageChatView extends BaseFragment implements MyCompetitionManageChatContract.view{

    private static final String TAG = "MyCompetitionsManageChatView";

    public static MyCompetitionsManageChatView newInstance(int idChat, String name) {
        MyCompetitionsManageChatView fragment = new MyCompetitionsManageChatView();

        Bundle args = new Bundle();
        args.putString(TAG,name);
        args.putInt(TAG, idChat);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void setTitleFragment() {
        getActivity().setTitle(getArguments().getString(TAG));
    }

    private Socket socket;
    private View.OnClickListener onclickSendMessage;

    private MyCompetitionManageChatPresenter presenter;

    private ImageButton button;
    private EditText etMessage;

    private RecyclerView rvChat;
    private RvChatGroupAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container,false);
        button = rootView.findViewById(R.id.btSendMessageCompetition);
        etMessage = rootView.findViewById(R.id.etTextChatCompetition);
        rvChat = rootView.findViewById(R.id.rvMessagesCompetition);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new MyCompetitionManageChatPresenter(this);
        presenter.getMessages(getArguments().getInt(TAG));

        button.setOnClickListener(onclickSendMessage);
    }




    //CHAT - SOCKET.IO
    @Override
    public void onDestroy() {
        //Cuando se cierre la activity y se destruya este fragment asguramos que destruimos la conexión del socket
        closeSocket();
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //Si cambiamos de tab cerramos el teclado si estuviera  abierto
        if(!isVisibleToUser && socket!=null)
            hideKeyboard(getActivity());

        //Cuando vuelva a ser visible este tab cargamos los mensajes de nuevo que haya
        if(isVisibleToUser && presenter!=null)
            presenter.getMessages(getArguments().getInt(TAG));

        //Cuando vuelva a ser visible este tag iniciamos el socket de nuevo, en caso de que no sea visible lo destruimos
        if(isVisibleToUser &&socket==null) {
            initSocket();
        }else{
            closeSocket();
        }
    }

    private void closeSocket(){
        if(socket!=null){
            socket.off("serverResponse");
            socket.off("firstConnection");
            socket.close();
            socket = null;
            Log.d("mosquito","destruido los sockets");
        }
    }

    private void initSocket(){

        try {
            socket = IO.socket(PikaosApplication.URL);
            socket.connect();
            socket.on("serverResponse", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String user = (String) args[0];
                            String message = (String) args[1];
                            String hour = (String) args[2];
                            String date = (String) args[3];

                            Message newMessage = new Message(hour, date, message, user);

                            adapter.addMessage(newMessage);

                            setChatLastPosition();
                        }
                    });
                }
            });

            //Para que se una al chat sin que el usuario haya mandado ningún mensaje
            socket.emit("firstConnection",getArguments().getInt(TAG));

            Log.d("mosquito",getArguments().getInt(TAG)+"");
            Log.d("mosquito","creado los sockets");


        } catch (URISyntaxException e) {
            e.printStackTrace();

        }


        onclickSendMessage = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!etMessage.getText().toString().trim().isEmpty()) {
                            socket.emit("clientMessage", getArguments().getInt(TAG), PikaosApplication.userName,etMessage.getText().toString());
                            etMessage.setText(null);
                        }
                    }
                });
            }
        };
    }

    //CONTRACT
    @Override
    public void setMessageError(int error) {

    }

    @Override
    public void setMessages(List<Message> messages) {
        adapter = new RvChatGroupAdapter(getActivity(), messages);
        rvChat.setAdapter(adapter);
        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));

        setChatLastPosition();
    }

    /**
     * Pone el recycler view en la última posición
     */
    private void setChatLastPosition(){

        rvChat.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                if(rvChat.getAdapter().getItemCount()!=0)
                    rvChat.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

}
