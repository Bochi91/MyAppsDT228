package com.example.bochi91.multicalendar;

//import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
//import android.widget.Toast;


public class RegActivity extends ActionBarActivity {

    public EditText firstname, surname, address1, password, department, email;
    public Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        firstname = (EditText) findViewById(R.id.firstname);
        surname = (EditText) findViewById(R.id.surname);
        address1 = (EditText) findViewById(R.id.address1);
        password = (EditText) findViewById(R.id.password);
        department = (EditText) findViewById(R.id.department);
        email = (EditText) findViewById(R.id.email);
        register = (Button) findViewById(R.id.button);


        register.setOnClickListener(new View.OnClickListener() {

            InputStream is = null;

            @Override
            public void onClick(View v) {

                // This onClick will call the function to add the datat to the DB
                // Displays a toast message now to make sure it all works.
                String efirstname = firstname.getText().toString();
                String eSurname = surname.getText().toString();
                String eAddress1 = address1.getText().toString();

                String eDepartment = department.getText().toString();
                String eEmail = email.getText().toString();
                String ePassword = password.getText().toString();

                if(efirstname.equals("") || eSurname.equals("") || eAddress1.equals("")
                        || eDepartment.equals("") || eEmail.equals("") || ePassword.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please fill all of the fields.",
                            Toast.LENGTH_LONG).show();

                }
                else
                {

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);

                    nameValuePairs.add(new BasicNameValuePair("firstname", efirstname));
                    nameValuePairs.add(new BasicNameValuePair("surname", eSurname));
                    nameValuePairs.add(new BasicNameValuePair("address", eAddress1));
                    nameValuePairs.add(new BasicNameValuePair("department", eDepartment));
                    nameValuePairs.add(new BasicNameValuePair("email", eEmail));
                    nameValuePairs.add(new BasicNameValuePair("password", ePassword));


                    //setting up the connection inside the try catch block
                    try{
                        //setting up the default http client
                        HttpClient httpClient = new DefaultHttpClient();

                        //Setting up the http post method and passing the url in case
                        //of online database and the ip address in case of localhost database.
                        //And the php file which serves as the link between the android app
                        //and the database.

                        HttpPost httpPost = new HttpPost("http://teammacro.byethost12.com/connect.php");
                        //Passing the nameValuePairs inside the httpPost
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        //Getting the response
                        HttpResponse response = httpClient.execute(httpPost);

                        //setting up the entity
                        HttpEntity entity = response.getEntity();

                        //setting up the content in an input stream reader
                        //lets define the input stream reader


                        is = entity.getContent();

                        //Displaying a toast message if the data is entered successfully
                        String msg = "Registration complete";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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
                }

        });
    }


}
