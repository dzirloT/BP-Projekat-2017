package com.example.seilab.bp007;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class DetaljiObjekta extends AppCompatActivity {

    private static ArrayList<Objekat> unosi = new ArrayList<Objekat>();
    private static String rezultat;
    private static String objekat;
    private static String nazivi;

    private static Connection connection;
    private static ConnectionHelper helper=ConnectionHelper.getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji_objekta);
        ListView lista = (ListView)findViewById(R.id.listac);
        unosi.clear();
        final DetaljiAdapter adapter;
        adapter = new DetaljiAdapter(this,R.layout.listac_elementi, unosi);
        lista.setAdapter(adapter);
        connection=helper.getCurrentConnection();
        rezultat=getIntent().getStringExtra("objekat");
        objekat=getIntent().getStringExtra("naziv");
        nazivi=getIntent().getStringExtra("rezultat");
        TextView textView = (TextView) findViewById(R.id.naziv);
        textView.setText(nazivi);

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+rezultat);
            ResultSetMetaData meta = rs.getMetaData();
            String columnName = meta.getColumnName(1);
            rs = stmt.executeQuery("SELECT * FROM " + rezultat +
                    " WHERE " + columnName + " = " + "'" + objekat + "'");
            meta = rs.getMetaData();
            int i=1;
            int metaColumnCount = meta.getColumnCount();
            while(rs.next())	{
                for(i = 1; i <= metaColumnCount; i++)
                unosi.add(new Objekat(meta.getColumnName(i),rs.getString(i)));
            }
            rs.close();
            stmt.close();
        }

        catch (Exception e) {
            Toast.makeText(DetaljiObjekta.this,
                    "Nema nista", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }
}
