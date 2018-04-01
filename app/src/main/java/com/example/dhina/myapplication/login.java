package com.example.dhina.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class login extends AppCompatActivity {
    EditText name,password;
    Button login;
    private static final String REGISTER_URL="https://dhineshbalu97.000webhostapp.com/bloodbank/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String username = name.getText().toString().toLowerCase();
        String pass = password.getText().toString().toLowerCase();
        loginuser(username,pass);
    }

    private void loginuser(final String username, String password){
        String urlSuffix = "?username=" + username + "&password=" + password;
        class LoginUser extends AsyncTask<String, Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(login.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                // Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
                if (s.equalsIgnoreCase("valid")) {
                    Intent i = new Intent(getApplicationContext(), body.class);
                    i.putExtra("name",username);
                    startActivity(i);
                }



                else
                {
                    Toast.makeText(getApplicationContext(),"invalid ",Toast.LENGTH_LONG).show();
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
        LoginUser ur=new LoginUser();
        ur.execute(urlSuffix);
    }
}
