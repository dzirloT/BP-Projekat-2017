package com.example.seilab.bp007;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

   // private static final String DEFAULT_URL = "jdbc:oracle:thin:@80.65.65.66:1521:ETFLAB";
    //private static final String DEFAULT_USERNAME = "BP07";
    //private static final String DEFAULT_PASSWORD = "eyEeo2es";
    private static final String TAG = "MyActivity";
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static Connection connection;
    private static ConnectionHelper helper=ConnectionHelper.getApplication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final EditText host = (EditText) findViewById(R.id.host);
        final EditText port = (EditText) findViewById(R.id.port);
        final EditText sid = (EditText) findViewById(R.id.SID);
        final EditText user = (EditText) findViewById(R.id.Username);
        final EditText pass = (EditText) findViewById(R.id.pass);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Connecting", Toast.LENGTH_LONG).show();
                URL = "jdbc:oracle:thin:@"+host.getText().toString()+":"+port.getText().toString() +
                        ":"+sid.getText().toString();
                USERNAME = user.getText().toString();
                PASSWORD = pass.getText().toString();
                helper.Initial(URL,USERNAME,PASSWORD);

                try {
                    helper.createConnection();
                     Toast.makeText(MainActivity.this,
                            "Success", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(MainActivity.this, Lista.class);
                    MainActivity.this.startActivity(myIntent);
                }
                catch (Exception e) {
                    Log.v(TAG,""+e);
                    Toast.makeText(MainActivity.this,
                            "OOPSIE", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

}


