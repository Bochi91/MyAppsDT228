package com.example.bochi91.libraryapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class LogActivity extends ActionBarActivity {

    TextView txh, txe, txp;
    EditText emailt, passt;
    Button lbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txh = (TextView) findViewById(R.id.headtitle);
        txe = (TextView) findViewById(R.id.emailtxt);
        txp = (TextView) findViewById(R.id.passtxt);

        emailt = (EditText) findViewById(R.id.Emailet);
        passt = (EditText) findViewById(R.id.passet);

        lbt = (Button) findViewById(R.id.logbtn);



        lbt.setOnClickListener(new View.OnClickListener() {

            InputStream is = null;
            @Override
            public void onClick(View v) {
                String email = emailt.getText().toString();
                String password = passt.getText().toString();

                //This code was taken from the website youtube.com
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);


                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("password", password));


                //setting up the connection inside the try catch block
                try{
                    //setting up the default http client
                    HttpClient httpClient = new DefaultHttpClient();

                    //Setting up the http post method and passing the url in case
                    //of online database and the ip address in case of localhost database.
                    //And the php file which serves as the link between the android app
                    //and the database.

                    HttpPost httpPost = new HttpPost("http://teammacro.byethost12.com/liblogin.php");
                    //Passing the nameValuePairs inside the httpPost
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    //Getting the response
                    HttpResponse response = httpClient.execute(httpPost);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    //setting up the entity
                    HttpEntity entity = response.getEntity();

                    //setting up the content in an input stream reader
                    //lets define the input stream reader
                    String responseBody = httpClient.execute(httpPost, responseHandler);

                    is = entity.getContent();

                    //Displaying a toast message if the data is entered successfully
                    String msg = responseBody;

                    if(msg.matches("match")) {

                        msg = "Login successful";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LogActivity.this, OnlineLibMenuActivity.class);
                        i.putExtra("User", email);
                        startActivity(i);
                    }
                    else
                    {
                        msg = "Error: incorrect credentials";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }

                    //This code was taken from the website youtube.com

                }//write the catch blocks to handle the exceptions
                catch(ClientProtocolException e){
                    Log.e("ClientProtocol", "Log_tag");
                    e.printStackTrace();
                } catch (IOException e)
                {
                    Log.e("Log_tag", "IOException");
                    e.printStackTrace();
                }

            }


        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
