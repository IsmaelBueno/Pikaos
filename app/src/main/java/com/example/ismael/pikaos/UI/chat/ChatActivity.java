package com.example.ismael.pikaos.UI.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ismael.pikaos.R;
import com.example.ismael.pikaos.UI.PikaosApplication;
import com.example.ismael.pikaos.UI.adapters.online.RvChatAdapter;
import com.example.ismael.pikaos.data.model.online.Friend;
import com.example.ismael.pikaos.data.model.online.Message;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.net.URISyntaxException;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements ChatContract.view{

    public final static String TAG = "ChatActivityTag";

    private ChatPresenter presenter;

    private Friend friend;
    private Socket socket;

    private RvChatAdapter adapter;

    private ImageButton button;
    private EditText etMessage;
    private RecyclerView rvChat;
    private Toolbar toolbar;

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        socket.off("serverResponse");
        socket.off("firstConnection");
        socket.close();

        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = findViewById(R.id.toolbar);
        button = findViewById(R.id.btSendMessage);
        rvChat = findViewById(R.id.rvMessages);
        etMessage = findViewById(R.id.etTextChat);

        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        friend = (Friend) bundle.getSerializable(TAG);

        //Toolbar
        getSupportActionBar().setTitle(friend.getName());
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        /*
        Picasso.get().load(friend.getAvatar()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                toolbar.setLogo(new BitmapDrawable(bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });*/

        //Chat, cargar mensajes con el apirestclient, nuevos mensajes a traves de sockets
        presenter = new ChatPresenter(this);
        presenter.getMessages(friend.getId());

        initSocket();

    }

    private void initSocket(){


        try {
            socket = IO.socket(PikaosApplication.URL);
            socket.connect();
            socket.on("serverResponse", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    runOnUiThread(new Runnable() {
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
            socket.emit("firstConnection",friend.getChat());


        } catch (URISyntaxException e) {
            e.printStackTrace();

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!etMessage.getText().toString().trim().isEmpty()) {
                            socket.emit("clientMessage", friend.getChat(), PikaosApplication.userName,etMessage.getText().toString());
                            etMessage.setText(null);
                        }
                    }
                });
            }
        });
    }

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


    //CONTRACT
    @Override
    public void setMessages(List<Message> messages) {
        adapter = new RvChatAdapter(this, messages);
        rvChat.setAdapter(adapter);
        rvChat.setLayoutManager(new LinearLayoutManager(this));

        //Si hubiera muchos mensajes lo pone en la ultima posición
        setChatLastPosition();
    }

    @Override
    public void messageError(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
