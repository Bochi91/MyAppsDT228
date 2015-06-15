package com.example.bochi91.libraryapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;


public class BookInfoActivity extends ActionBarActivity {

    TextView th, tt, ta, txts;
    Button delb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        Intent i = getIntent();

        String title = i.getStringExtra("title");
        String author = i.getStringExtra("author");
        String is = i.getStringExtra("pos");
        final int pos = Integer.parseInt(is);


        th = (TextView) findViewById(R.id.header);
        tt = (TextView) findViewById(R.id.t);
        ta = (TextView) findViewById(R.id.a);
        txts = (TextView) findViewById(R.id.ts);

        delb = (Button) findViewById(R.id.btndel);

        tt.setText(title);
        ta.setText(author);

        delb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookDBManager mydb = new BookDBManager(BookInfoActivity.this);
                // When clicked, show a toast with the TextView text
                try
                {
                    mydb.open();
                }
                catch (SQLException e)
                {
                    Log.e("Error executing SQL", e.toString());
                }
                Intent i = getIntent();
                String is = i.getStringExtra("pos");
                int pos = Integer.parseInt(is);

                mydb.deleteBook(pos);

                Intent ia = new Intent(BookInfoActivity.this, ViewBooksActivity.class);

                startActivity(ia);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_info, menu);
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
