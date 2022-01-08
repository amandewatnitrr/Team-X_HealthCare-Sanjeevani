package com.example.sanjeevanifinal.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sanjeevanifinal.R;
import com.example.sanjeevanifinal.adapters.MessageAdapter;
import com.example.sanjeevanifinal.utils.MessageObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    String chatRoomID = "null";
    EditText composeMsg;
    Button sendBtn;
    private RecyclerView chatList;
    private RecyclerView.Adapter messageListAdapter;
    private RecyclerView.LayoutManager messageListLayoutManager;
    ArrayList<MessageObject> messageList;
    String currUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatRoomID = getIntent().getStringExtra("roomId");
        currUserName = getIntent().getStringExtra("currUserName");
        initialiseWidgets();
        initializeMessage();
        getPrevMessages();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = composeMsg.getText().toString();
                if(message!=null && message.length()>0){
                    initiateSendMsg(message);
                    composeMsg.setText("");
                }
            }
        });
    }

    private void initiateSendMsg(String message) {
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("consulation").child(chatRoomID).child("messages");
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String messageID = myref.push().getKey();
        final Map map = new HashMap<>();
        map.put("creator",currUserName);
        map.put("text",message);
        myref.child(messageID).updateChildren(map);
    }

    private void initialiseWidgets() {
        composeMsg = findViewById(R.id.messageInput);
        sendBtn = findViewById(R.id.send);
        chatList = findViewById(R.id.messageList);
    }

    private void getPrevMessages() {
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("consulation").child(chatRoomID).child("messages");
        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    String text = "", creator = "";
                    if(snapshot.child("text").getValue()!=null){
                        text = snapshot.child("text").getValue().toString();
                    }
                    if(snapshot.child("creator").getValue()!=null){
                        creator = snapshot.child("creator").getValue().toString();
                    }
                    MessageObject mMessage = new MessageObject(snapshot.getKey(), creator, text);
                    messageList.add(mMessage);
                    messageListLayoutManager.scrollToPosition(messageList.size()-1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("WrongConstant")
    private void initializeMessage() {
        messageList = new ArrayList<>();
        chatList= findViewById(R.id.messageList);
        chatList.setNestedScrollingEnabled(false);
        chatList.setHasFixedSize(false);
        messageListLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        chatList.setLayoutManager(messageListLayoutManager);
        messageListAdapter = new MessageAdapter(messageList);
        chatList.setAdapter(messageListAdapter);
    }

}