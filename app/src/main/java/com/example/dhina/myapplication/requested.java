package com.example.dhina.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class requested extends AppCompatActivity {

    String[] bloodgroup = {"A+", "A-", "B+", "B-", "O-", "AB-"};
    String[] location = {"karur", "trichy", "namakkal", "salem", "erode","dhindugal"};

    String username = "";
    String blood="";
    String loct="";

    String ph ="";
    EditText num,name;
    Button click;
    String REGISTER_URL="https://dhineshbalu97.000webhostapp.com/bloodbank/request.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested);

        num = (EditText) findViewById(R.id.number);
        name = (EditText) findViewById(R.id.name);
        click = (Button) findViewById(R.id.save);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph = num.getText().toString().toLowerCase();
                username = name.getText().toString().toLowerCase();
                // Toast.makeText(getApplication(), "gdfg", Toast.LENGTH_LONG).show();
                fillform(username, ph, blood, loct);
            }
        });

        Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(),bloodgroup[position] ,Toast.LENGTH_LONG).show();
                blood = bloodgroup[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),location[position] ,Toast.LENGTH_LONG).show();
                loct = location[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodgroup);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin1.setAdapter(aa);
        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, location);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin2.setAdapter(aa1);
    }


    private void fillform( final String username,String number,String blood,String location ) {
        String urlSuffix = "?username=" + username + "&number=" + number + "&blood=" + blood  + "&location=" + location  ;
        Toast.makeText(getApplicationContext(),username + " " + number + " " + blood ,Toast.LENGTH_LONG).show();
        class FillForm extends AsyncTask<String, Void,String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(requested.this, "Please Wait", null, true, true);
            }

            @Override




            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                // Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
                if (s.equalsIgnoreCase("successfully registered")) {
                    Intent i = new Intent(getApplicationContext(), body.class);
                    startActivity(i);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferReader=null;
                try {
                    URL url=new URL(REGISTER_URL+s);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    bufferReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result=bufferReader.readLine();
                    // Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                    return  result;
                } catch (Exception e) {
                    return null;
                }
            }
        }
        FillForm ur=new FillForm();
        ur.execute(urlSuffix);

    }


}
