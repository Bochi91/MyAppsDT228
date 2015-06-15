package com.example.bochi91.libraryapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

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


public class OnlineBookSelectActivity extends ActionBarActivity {

    TextView ht, st, tt, at, bdt;
    Button btb, rtb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_book_select);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ht = (TextView) findViewById(R.id.hdtxt);
        st = (TextView) findViewById(R.id.sttxt);
        tt = (TextView) findViewById(R.id.ttxt);
        at = (TextView) findViewById(R.id.atxt);
        bdt = (TextView) findViewById(R.id.stxt);

        btb = (Button) findViewById(R.id.borbt);
        rtb = (Button) findViewById(R.id.retbt);

        final Intent i  = getIntent();

        final String title = i.getStringExtra("title");
        String author = i.getStringExtra("author");
        final String borrowed = i.getStringExtra("borrowed");

        tt.setText(title);
        at.setText(author);
        bdt.setText(borrowed);

        btb.setOnClickListener(new View.OnClickListener() {

            InputStream is = null;

            @Override
            public void onClick(View v) {

                if(borrowed.matches("N"))
                {
                    String id = i.getStringExtra("id");
                    //This code was taken from the website youtube.com
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);


                    nameValuePairs.add(new BasicNameValuePair("id", id));



                    //setting up the connection inside the try catch block
                    try{
                        //This code has been taken from youtube.com
                        //setting up the default http client
                        HttpClient httpClient = new DefaultHttpClient();

                        //Setting up the http post method and passing the url in case
                        //of online database and the ip address in case of localhost database.
                        //And the php file which serves as the link between the android app
                        //and the database.

                        HttpPost httpPost = new HttpPost("http://teammacro.byethost12.com/borrowed.php");
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
                        String msg = "You successfully borrowed " + title;

                        Toast.makeText(OnlineBookSelectActivity.this, msg, Toast.LENGTH_LONG).show();
                        Intent ia = new Intent(OnlineBookSelectActivity.this, onlinelibactivity.class);
                        startActivity(ia);
                        //This code has been taken from youtube.com


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
                else{

                    String msg = "Book has already been borrowed";
                    Toast.makeText(OnlineBookSelectActivity.this, msg, Toast.LENGTH_LONG).show();
                }

            }
        });

        rtb.setOnClickListener(new View.OnClickListener() {

            InputStream is = null;

            @Override
            public void onClick(View v) {


                if(borrowed.matches("Y"))
                {

                    String id = i.getStringExtra("id");

                    //This code was taken from the website youtube.com
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);


                    nameValuePairs.add(new BasicNameValuePair("id", id));



                    //setting up the connection inside the try catch block
                    try{
                        //This code has been taken from youtube.com
                        //setting up the default http client
                        HttpClient httpClient = new DefaultHttpClient();

                        //Setting up the http post method and passing the url in case
                        //of online database and the ip address in case of localhost database.
                        //And the php file which serves as the link between the android app
                        //and the database.

                        HttpPost httpPost = new HttpPost("http://teammacro.byethost12.com/returned.php");
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
                        String msg = "You successfully returned " + title;

                        Toast.makeText(OnlineBookSelectActivity.this, msg, Toast.LENGTH_LONG).show();
                        Intent ia = new Intent(OnlineBookSelectActivity.this, onlinelibactivity.class);
                        startActivity(ia);
                        //This code has been taken from youtube.com


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
                else{

                    String msg = "Book wasn't borrowed";
                    Toast.makeText(OnlineBookSelectActivity.this, msg, Toast.LENGTH_LONG).show();
                }



            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_online_book_select, menu);
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
