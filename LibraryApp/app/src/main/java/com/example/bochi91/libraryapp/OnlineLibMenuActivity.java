package com.example.bochi91.libraryapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class OnlineLibMenuActivity extends ActionBarActivity {

    TextView h, m, u, n;
    Button o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_lib_menu);

        h = (TextView) findViewById(R.id.welcome);
        m = (TextView) findViewById(R.id.message);
        u = (TextView) findViewById(R.id.user);
        n = (TextView) findViewById(R.id.name);
        o = (Button) findViewById(R.id.btno);

        Intent i = getIntent();
        String user = i.getStringExtra("User");

        n.setText(user);

        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent io = new Intent(OnlineLibMenuActivity.this, onlinelibactivity.class);
                startActivity(io);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_online_lib_menu, menu);
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
