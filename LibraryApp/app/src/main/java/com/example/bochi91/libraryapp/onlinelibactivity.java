package com.example.bochi91.libraryapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/*
This entire activity was taken from the the website http://codetheory.in/working-sqlite-database-crud-operations-android/
 */


public class onlinelibactivity extends ListActivity {

    TextView ht, st;
    Button ab;

    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> booksList;




    private static final String READ_BOOK_URL = "http://teammacro.byethost12.com/getlibbooks.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BOOK = "Book";
    private static final String TAG_BID = "Book_id";
    private static final String TAG_TITLE   = "title";
    private static final String TAG_AUTHOR   = "author";
    private static final String TAG_BORROWED    = "borrowed";

    public static String bid = "";
    public static String title = "";
    public static String author = "";
    public static String borrowed = "";

    // products JSONArray
    JSONArray books = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinelibactivity);

        ht = (TextView) findViewById(R.id.omtxt);
        st = (TextView) findViewById(R.id.olibtxt);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // Hashmap for ListView
        booksList = new ArrayList<HashMap<String, String>>();

        new LoadAllbooks().execute();

        ListView l = getListView();

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int bdid = (int) id;

                position = bdid;
                Intent i = new Intent(onlinelibactivity.this, OnlineBookSelectActivity.class);
                i.putExtra("id", booksList.get(position).get(TAG_BID).toString());
                i.putExtra("title",booksList.get(position).get(TAG_TITLE).toString());
                i.putExtra("author", booksList.get(position).get(TAG_AUTHOR).toString());
                i.putExtra("borrowed", booksList.get(position).get(TAG_BORROWED).toString());
                startActivity(i);

            }
        });


    }

    class LoadAllbooks extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(onlinelibactivity.this);
            pDialog.setMessage("Loading books. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All books from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(READ_BOOK_URL, "GET", params);

            // Check your log cat for JSON response
            Log.d("All Books: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Books
                   books = json.getJSONArray(TAG_BOOK);

                    // looping through All books
                    for (int i = 0; i < books.length(); i++) {
                        JSONObject c = books.getJSONObject(i);

                        // Storing each json item in variable
                        bid = c.getString(TAG_BID);
                        title = c.getString(TAG_TITLE);
                        author = c.getString(TAG_AUTHOR);
                        borrowed = c.getString(TAG_BORROWED);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_BID, bid);
                        map.put(TAG_TITLE, title);
                        map.put(TAG_AUTHOR, author);
                        map.put(TAG_BORROWED, borrowed);

                        // adding HashList to ArrayList
                       booksList.add(map);
                    }
                } else {
                    // no books found
                    Toast.makeText(onlinelibactivity.this, "No Books found", Toast.LENGTH_LONG).show();
                    // Launch Add menu Activity
                    Intent i = new Intent(getApplicationContext(),
                            OnlineLibMenuActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            onlinelibactivity.this, booksList,
                            R.layout.row2, new String[] { TAG_TITLE,
                            TAG_AUTHOR, TAG_BORROWED},
                            new int[] { R.id.text1, R.id.text2, R.id.text3 });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_onlinelibactivity, menu);
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
