/*
    Cole Howell, Manoj Bompada
    SearchMoviesActivity.java
    ITCS 4180
 */

package example.com.imdbapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class SearchMoviesActivity extends AppCompatActivity {

    String searchValue = "";
    ProgressDialog progressDialog;
    TextView[] tv;
    public static String MOVIELIST = "movielist";
    public static String MOVIETITLE = "title";
    public static String MOVIEOBJ = "movie obj";
    public static String SRCHTXT = "movie txt";

    ArrayList<Movie> movieslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);
        setTitle("Search Movies");

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);


        if(getIntent().getExtras() != null){
            if(getIntent().getExtras().containsKey(MovieDetailsActivity.MOVIEDETAILKEY)){
                searchValue = getIntent().getExtras().getString(MovieDetailsActivity.MOVIEDETAILTXT);
            }

            else if(getIntent().getExtras().containsKey(MainActivity.MAINKEY)){

                searchValue = getIntent().getExtras().getString(MainActivity.SEARCHTXT);
            }


            new GetMoviesAsyncTask().execute("http://www.omdbapi.com/?type=movie&s="+ searchValue);
        }
        else{
            Toast.makeText(SearchMoviesActivity.this,"no text entered",Toast.LENGTH_SHORT).show();
        }

    }

    class GetMoviesAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SearchMoviesActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading Movie List");
            progressDialog.show();
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(final ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            if (movies != null){
                Log.d("demo1", movies.toString());
                progressDialog.dismiss();
                movieslist = movies;

                tv = new TextView[movies.size()];

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lparams.gravity = Gravity.LEFT;
                lparams.setMargins(0, 30, 0, 30);


                for (int i = 0; i < movies.size(); i++) {
                    final String title = movies.get(i).title;

                    final Movie movie = movies.get(i);
                    String year = movies.get(i).year;
                    TextView textView = new TextView(SearchMoviesActivity.this);
                    textView.setLayoutParams(lparams);
                    textView.setText(title+"("+year+")");
                    textView.setId(View.generateViewId());


                    ImageView divider = new ImageView(SearchMoviesActivity.this);
                    divider.setLayoutParams(lparams);
                    divider.setMinimumHeight(2);
                    divider.setBackgroundColor(Color.BLACK);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SearchMoviesActivity.this, MovieDetailsActivity.class);
                            intent.putExtra(MOVIELIST, movieslist);
                            intent.putExtra(MOVIEOBJ, movie);
                            intent.putExtra(SRCHTXT, searchValue);
                            startActivity(intent);
                            finish();
                        }
                    });

                    linearLayout.addView(textView);
                    linearLayout.addView(divider);
                    tv[i] = textView;


                }

            }
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                Log.d("demo","movie url" +url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int statusCode = connection.getResponseCode();
                if(statusCode == HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null){
                        sb.append(line);
                        line = reader.readLine();
                    }
                    return MovieUtil.MovieJSONParser.parseMovie(sb.toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
