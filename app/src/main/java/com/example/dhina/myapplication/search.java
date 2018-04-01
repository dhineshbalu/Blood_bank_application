package com.example.dhina.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.*;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class search extends AppCompatActivity {
    ListView simpleList;


    EditText name,number;
     String username="";
    CustomAdapter customAdapter;
     String REGISTER_URL="https://dhineshbalu97.000webhostapp.com/bloodbank/select.php";

    String[] bloodgroup = {"A+", "A-", "B+", "B-", "O-", "AB-"};
    String[] location = {"karur", "trichy", "namakkal", "salem", "erode","dhindugal"};
    String blood="";
    String loct="";
    Button click;
    String urlSuffix;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       name = (EditText)findViewById(R.id.name);
        number = (EditText)findViewById(R.id.number);
        Bundle b  =  getIntent().getExtras();
        username =   b.getString("name");
        urlSuffix = "?username=" + username ;
        getJSON(urlSuffix);

        simpleList = (ListView)findViewById(R.id.simpleListView);

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


        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bloodgroup);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin1.setAdapter(aa);
        ArrayAdapter aa1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,location);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin2.setAdapter(aa1);

        click = (Button)findViewById(R.id.save);



        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              filter();
            }
        });

    }

    private void filter() {
       urlSuffix = "?username=" + username + "?bloodgroup" + blood + "?location" + location ;
        count=1;
        getJSON(urlSuffix);
    }

    private void getJSON(String url) {
        /*
        * As fetching the json string is a network operation
        * And we cannot perform a network operation in main thread
        * so we need an AsyncTask
        * The constrains defined here are
        * Void -> We are not passing anything
        * Void -> Nothing at progress update as well
        * String -> After completion it should return a string and it will be the json string
        * */

        class GetJSON extends AsyncTask<String, Void,String> {

            ProgressDialog loading;

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(search.this, "Please Wait", null, true, true);
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(String... params) {

                String s = params[0];

                try {
                    //creating a URL
                    if(count==1)
                    {
                        REGISTER_URL="https://dhineshbalu97.000webhostapp.com/bloodbank/filter.php";
                    }
                    URL url = new URL(REGISTER_URL+ s);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute(url);
    }
    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] names = new String[jsonArray.length()];
        String[] numbers = new String[jsonArray.length()];
        String[]  bloods = new String[jsonArray.length()];
        String[] locations = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            names[i] = obj.getString("username");
            numbers[i] = obj.getString("number");
            bloods[i] = obj.getString("blood");
            locations[i] = obj.getString("location");
        }

        customAdapter = new CustomAdapter(getApplicationContext(), names,numbers,bloods,locations);
        simpleList.setAdapter(customAdapter);

    }
}
