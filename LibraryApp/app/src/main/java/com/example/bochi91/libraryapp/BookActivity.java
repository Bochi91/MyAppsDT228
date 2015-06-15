package com.example.bochi91.libraryapp;

import android.content.Intent;
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

import java.sql.SQLException;


public class BookActivity extends ActionBarActivity {


    TextView Txth, Txtt, Txta;
    EditText ett, eta;
    Button addb, delb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);



        Txth = (TextView) findViewById(R.id.ht);
        Txtt = (TextView) findViewById(R.id.bt);
        Txta = (TextView) findViewById(R.id.at);

        ett = (EditText) findViewById(R.id.ettitle);
        eta = (EditText) findViewById(R.id.etAuthor);

        addb = (Button) findViewById(R.id.btna);



        addb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                BookDBManager db = new BookDBManager(BookActivity.this);

                String title = ett.getText().toString();
                String author = eta.getText().toString();

                try
                {
                    db.open();
                }
                catch (SQLException e)
                {
                    Log.e("Error executing SQL", e.toString());
                }

                if(title.equals("") || author.equals(""))
                {
                    Toast.makeText(BookActivity.this,"Please fill all details", Toast.LENGTH_LONG).show();
                }
                else
                {

                    db.insertBook(ett.getText().toString(),eta.getText().toString(), "N");

                    Toast.makeText(BookActivity.this,"Book added", Toast.LENGTH_LONG).show();



                    Intent t = new Intent(BookActivity.this, MainActivity.class);
                    startActivity(t);

                }




            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book, menu);
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
