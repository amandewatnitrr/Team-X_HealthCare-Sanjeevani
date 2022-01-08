package com.example.sanjeevanifinal.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sanjeevanifinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ChatListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> data;
    private ArrayList<String> listOfNames;
    private LayoutInflater inflater=null;
    Context ctx;
    private TextView name,id;

    public ChatListAdapter(Activity activity, ArrayList<String> room, ArrayList<String> namesOfChat){
        this.ctx = activity.getBaseContext();
        this.activity = activity;
        this.data=room;
        this.listOfNames=namesOfChat;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi=convertView;

        if(convertView==null)
            vi = inflater.inflate(R.layout.item_chat_list, null);

        name = (TextView)vi.findViewById(R.id.roomName);
        id = (TextView) vi.findViewById(R.id.roomID);

        id.setText(data.get(position));
        name.setText(listOfNames.get(position));

        return vi;
    }
}
