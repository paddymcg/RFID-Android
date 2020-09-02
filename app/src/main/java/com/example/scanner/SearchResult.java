package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {
    private static final String TAG = "RESULTS";
    String value = "";
    TextView tv;
    Bundle extras;
    ListView listView;
    EditText search;

    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        listView = findViewById(R.id.list);
        search = findViewById(R.id.search);
        extras = getIntent().getExtras();

        if (extras != null) {
             value = extras.getString("help");
            getSupportActionBar().setTitle(value);
        }
        
        databaseHelper = new DatabaseHelper(SearchResult.this);
        arrayList = databaseHelper.getAllText(value);
        arrayAdapter = new ArrayAdapter(SearchResult.this,
                android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SearchResult.this.arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
