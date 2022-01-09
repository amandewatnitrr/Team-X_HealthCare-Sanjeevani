package com.example.sanjeevanifinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanjeevanifinal.R;
import com.example.sanjeevanifinal.utils.BottomNavigationHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        signoutCurrUser();
        return(super.onOptionsItemSelected(item));
    }

    private void signoutCurrUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            getSharedPreferences("role",MODE_PRIVATE).edit().clear().commit();
            getSharedPreferences("kit" , MODE_PRIVATE).edit().clear().commit();
            Intent intent = new Intent(PatientHomeActivity.this,LoginActivity.class);
            auth.signOut();
            startActivity(intent);
            finish();
        }
    }

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
                double currPressure = snapshot.getValue(Double.class);
                valBp.setText(String.valueOf(currPressure));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myref.child("Pulse rate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currPulse = snapshot.getValue(String.class);
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