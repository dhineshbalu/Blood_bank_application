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
public class donor extends AppCompatActivity {


    String username="";
    CustomAdapter1 customAdapter;
    String REGISTER_URL="https://dhineshbalu97.000webhostapp.com/bloodbank/donor.php";


    ListView simpleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);

        Bundle b  =  getIntent().getExtras();
        username =   b.getString("name");

        simpleList = (ListView)findViewById(R.id.simpleListView);

        getJSON();

    }
    private void getJSON() {
        /*

        * As fetching the json string is a network operation
        * And we cannot perform a network operation in main thread
        * so we need an AsyncTask
        * The constrains defined here are
        * Void -> We are not passing anything
        * Void -> Nothing at progress update as well
        * String -> After completion it should return a string and it will be the json string
        * */
        String urlSuffix = "?username=" + username ;

        class GetJSON extends AsyncTask<String, Void,String> {

            ProgressDialog loading;

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(donor.this, "Please Wait", null, true, true);
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
        getJSON.execute(urlSuffix);
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

        customAdapter = new CustomAdapter1(getApplicationContext(), names,numbers,bloods,locations);
        simpleList.setAdapter(customAdapter);

    }
}