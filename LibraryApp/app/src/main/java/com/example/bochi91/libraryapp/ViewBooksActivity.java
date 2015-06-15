package com.example.bochi91.libraryapp;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.SQLException;


public class ViewBooksActivity extends ListActivity {

    TextView txtheader, txtsubt, txtt,txta;
    SQLiteDatabase db;
    Cursor result;
    ListView list;
    Button add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);


        txtheader = (TextView) findViewById(R.id.texthead);
        txtsubt = (TextView) findViewById(R.id.textsub);
        txtt = (TextView) findViewById(R.id.text1);
        txta = (TextView) findViewById(R.id.text2);

        add = (Button) findViewById(R.id.addbtn);



        add.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent inta = new Intent(ViewBooksActivity.this, BookActivity.class);
                startActivity(inta);
            }
        });




        BookDBManager mydb = new BookDBManager(this);



        try
        {
             mydb.open();
        }
        catch (SQLException e)
        {
            Log.e("Error executing SQL", e.toString());
        }

        //mydb.createEntries();
        result = mydb.getAllBooks();

        final SimpleCursorAdapter mylist = new SimpleCursorAdapter(this, R.layout.row, result, new String[] { mydb.KEY_TITLE , mydb.KEY_AUTHOR }, new int[] {R.id.text1, R.id.text2 });

        setListAdapter(mylist);





        final ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                BookDBManager mydb = new BookDBManager(ViewBooksActivity.this);
                // When clicked, show a toast with the TextView text
                try
                {
                    mydb.open();
                }
                catch (SQLException e)
                {
                    Log.e("Error executing SQL", e.toString());
                }


                int di = (int) id;
                Cursor i = mydb.getRowID(di);
                position = i.getInt(i.getColumnIndex(mydb.KEY_ROWID));
                String pos = i.getString(i.getColumnIndex(mydb.KEY_ROWID));
                Cursor t = mydb.getTitle(position);
                Cursor a = mydb.getAuthor(position);

                String author = a.getString(a.getColumnIndex(mydb.KEY_AUTHOR));
                String title = t.getString(t.getColumnIndex(mydb.KEY_TITLE));



               Toast.makeText(ViewBooksActivity.this, "Book information"  , Toast.LENGTH_LONG).show();

               Intent d = new Intent(ViewBooksActivity.this, BookInfoActivity.class);
                d.putExtra("title", title);
                d.putExtra("author",author);
                d.putExtra("pos", pos);
               startActivity(d);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_books, menu);
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
