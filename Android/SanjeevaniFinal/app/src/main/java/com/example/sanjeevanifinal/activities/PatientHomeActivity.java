package com.example.sanjeevanifinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sanjeevanifinal.R;
import com.example.sanjeevanifinal.utils.BottomNavigationHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientHomeActivity extends AppCompatActivity {

    public int ActivityNum = 1;
    private EditText editKitID;
    private Button go;
    private View linStats;
    private TextView valTemp, valBp, valPulse;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        setUpBottomNavigationView();
        initialiseWidgets();
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kitID = editKitID.getText().toString();
                fetchHealthParamsFromDB(kitID);
                saveKitID(kitID);
            }
        });
    }

    private void saveKitID(String kitID) {
        SharedPreferences.Editor editor = getSharedPreferences("kit" , MODE_PRIVATE).edit();
        editor.putString("savedKitID", kitID);
        editor.commit();
    }

    private void fetchHealthParamsFromDB(String kitID) {
        linStats.setVisibility(View.VISIBLE);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myref = db.getReference("iot").child(kitID);
        myref.child("Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double currTemp = (double) snapshot.getValue();
                valTemp.setText(String.valueOf(currTemp));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myref.child("Blood Pressure").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double currPressure = (double) snapshot.getValue();
                valTemp.setText(String.valueOf(currPressure));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myref.child("Pulse rate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currPulse = String.valueOf(snapshot.getValue(Long.class));
                valPulse.setText(currPulse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialiseWidgets() {
        editKitID = findViewById(R.id.kitID);
        go = findViewById(R.id.btnGo);
        linStats = findViewById(R.id.layStats);
        valTemp = findViewById(R.id.valTemp);
        valBp = findViewById(R.id.valBp);
        valPulse = findViewById(R.id.valPulse);
        linStats.setVisibility(View.GONE);
        prefs = getSharedPreferences("kit", MODE_PRIVATE);
        editKitID.setText(prefs.getString("savedKitID", "Sanjeevani Kit ID"));

        String s = prefs.getString("savedKitID", "Sanjeevani Kit ID");
        if (!s.equals("Sanjeevani Kit ID")){
            fetchHealthParamsFromDB(s);
        }
    }

    private void setUpBottomNavigationView(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavViewBar);
        bottomNavigationView.setItemIconTintList(null);
        BottomNavigationHelper.enableNavigation(PatientHomeActivity.this,bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ActivityNum);
        menuItem.setChecked(true);
    }

}
