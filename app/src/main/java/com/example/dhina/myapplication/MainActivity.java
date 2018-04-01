package com.example.dhina.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.widget.ImageButton;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    EditText username, password, email;
    Button signup, login;
    private static final String REGISTER_URL = "https://dhineshbalu97.000webhostapp.com/bloodbank/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        signup = (Button) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), login.class);
                i.putExtra("name","dhinesh");
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
         @Override
           public void onClick(View v) {

                           registeruser();
         }
      });


           }

    private void registeruser() {
               String name = username.getText().toString().toLowerCase();
               String pass = password.getText().toString().toLowerCase();
               String em = email.getText().toString().toLowerCase();
               register(name,pass,em);

                   }

    private void register(final String username, String password, String email){
        String urlSuffix = "?username=" + username + "&password=" + password + "&email=" + email;
                class RegisterUser extends AsyncTask<String, Void,String>{
       ProgressDialog loading;

                   @Override
                      protected void onPreExecute()
                   {
                                super.onPreExecute();
                                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
                   }


                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        // Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
                        if (s.equalsIgnoreCase("successfully registered")) {
                            Intent i = new Intent(getApplicationContext(), form.class);
                            i.putExtra("name",username);
                            startActivity(i);
                        }    else

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
        RegisterUser ur=new RegisterUser();
        ur.execute(urlSuffix);
    }



    }