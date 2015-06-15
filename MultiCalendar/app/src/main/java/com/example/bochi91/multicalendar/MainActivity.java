package com.example.bochi91.multicalendar;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class MainActivity extends ActionBarActivity {

    TextView txthead;
    TextView txtView;
    TextView txtCh;
    Spinner spnr;

    Button btnl;
    Button btnReg;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txthead = (TextView) findViewById(R.id.txth);
        txtView = (TextView) findViewById(R.id.txtv);
        txtCh = (TextView) findViewById(R.id.txtc);


        btnl = (Button) findViewById(R.id.btnlog);
        btnReg =(Button) findViewById(R.id.btnreg);

        spnr = (Spinner) findViewById(R.id.spinner);
        img = (ImageView) findViewById(R.id.imageView);

        btnReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, RegActivity.class);
                startActivity(i);


            }
        });

        btnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent l = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(l);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
