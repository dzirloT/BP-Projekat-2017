package com.example.seilab.bp007;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Detalji extends AppCompatActivity {

    private static ArrayList<String> unosi = new ArrayList<String>();
    private static String rezultat;
    private static String nesto;

    private static Connection connection;
    private static ConnectionHelper helper=ConnectionHelper.getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji);
        ListView lista = (ListView)findViewById(R.id.listic);
        unosi.clear();
        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter <String>(this,android.R.layout.simple_list_item_1, unosi);
        lista.setAdapter(adapter);
        connection=helper.getCurrentConnection();
        rezultat=getIntent().getStringExtra("objekat");

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs;
            switch (rezultat) {
                case "Tables":
                    rs=stmt.executeQuery("select * from user_tables");
                    while(rs.next()) {
                        unosi.add(rs.getString(1));

                    }
                    nesto="USER_TABLES";
                    break;
                case "Views":
                   rs=stmt.executeQuery("select * from user_views");
                    while(rs.next()) {
                        unosi.add(rs.getString(1));
                    }
                    nesto="USER_VIEWS";
                    break;
                case "Triggers":
                    rs=stmt.executeQuery("select * from user_triggers");
                    while(rs.next()) {
                        unosi.add(rs.getString(1));
                    }
                    nesto="USER_TRIGGERS";
                    break;
                case "Procedures":
                    rs=stmt.executeQuery("select * from USER_PROCEDURES");
                    while(rs.next()) {
                        unosi.add(rs.getString(1));
                    }
                    nesto="USER_PROCEDURES";
                    break;
                case "Indexes":
                    rs=stmt.executeQuery("select * from USER_INDEXES");
                    while(rs.next()) {
                        unosi.add(rs.getString(1));
                    }
                    nesto="USER_INDEXES";
                    break;
                default:
                    break;
            }

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(Detalji.this, DetaljiObjekta.class);
                    myIntent.putExtra("objekat", nesto);
                    myIntent.putExtra("rezultat", rezultat);
                    myIntent.putExtra("naziv",unosi.get(position));
                    startActivity(myIntent);
                }
            });

        }

        catch (Exception e) {
            Toast.makeText(Detalji.this,
                    "Nema nista", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }



    }
}
