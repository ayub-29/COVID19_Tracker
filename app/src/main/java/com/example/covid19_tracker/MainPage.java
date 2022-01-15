package com.example.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import model.Engine;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Engine engine = new Engine();
        setContentView(R.layout.activity_main_page);
        Spinner dropdown = findViewById(R.id.dropList);
        Button btn = findViewById(R.id.button);
        String[] items = engine.items;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = dropdown.getSelectedItem().toString();
                if(val.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Select a region", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(MainPage.this, InfoActivity.class);
                    intent.putExtra("cty", val);
                    startActivity(intent);
                }
            }
        });




    }


}