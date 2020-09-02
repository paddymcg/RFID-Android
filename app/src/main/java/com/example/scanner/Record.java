package com.example.scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Record extends AppCompatActivity {
        EditText groupname, othername;
        Button add;
        DatabaseHelper databaseHelper;

    private static final String TAG = "RECORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        groupname=findViewById(R.id.groupname);
        othername = findViewById(R.id.othername);
        add= findViewById(R.id.add_group);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = groupname.getText().toString();
                String other = othername.getText().toString();

                if(databaseHelper.addTable(name,other)){
                    groupname.setText("");
                    othername.setText("");
                    Toast.makeText(Record.this, " Group " + name + " added!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        databaseHelper = new DatabaseHelper(Record.this);
        /*------------------SETUP BOTTOM NAV-------------------------------*/
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.record:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),Search.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
