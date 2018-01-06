package com.example.seilab.bp007;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Lista extends AppCompatActivity {


    private static ArrayList<String> unosi = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        unosi.clear();
        unosi.add("Tables");
        unosi.add("Views");
        unosi.add("Triggers");
        unosi.add("Procedures");
        unosi.add("Indexes");
        ListView lista = (ListView)findViewById(R.id.listica);
        final MyArrayAdapter adapter;
        adapter = new MyArrayAdapter(this,R.layout.element_liste, unosi);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(Lista.this, Detalji.class);
                myIntent.putExtra("objekat", unosi.get(position));
                Lista.this.startActivity(myIntent);
            }
        });




    }
}
