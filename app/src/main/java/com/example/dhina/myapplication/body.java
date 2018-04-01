package com.example.dhina.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.*;

public class body extends AppCompatActivity {

    String username="";
    ImageButton profile,search,request,donor;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
        Bundle b  =  getIntent().getExtras();
         username =   b.getString("name");
        logout = (Button)findViewById(R.id.log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(body.this,MainActivity.class);

                startActivity(i);
            }
        });
        profile = (ImageButton)findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"profile",Toast.LENGTH_LONG).show();

                Intent i = new Intent(body.this,profile.class);
                i.putExtra("name",username);
                startActivity(i);
            }
        });

        search = (ImageButton)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(body.this,search.class);
                i.putExtra("name",username);
                startActivity(i);
            }
        });

        donor = (ImageButton)findViewById(R.id.donor);
        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(body.this,donor.class);
                i.putExtra("name",username);
                startActivity(i);
            }
        });

        request = (ImageButton)findViewById(R.id.request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(body.this,requested.class);
                startActivity(i);
            }
        });
    }
}
