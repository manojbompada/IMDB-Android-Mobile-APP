/*
    Cole Howell, Manoj Bompada
    MovieDetailsActivty.java
    ITCS 4180
 */

package example.com.imdbapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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

public class MovieDetailsActivity extends AppCompatActivity {
    TextView title,release,genre,director,actors,plot;
    ImageView poster;
    Button finishbtn;

    private int index=0;

    public static Movie srchmovie;
    ArrayList<Movie> movielist = new ArrayList<Movie>();
    ProgressDialog progressDialog;
    RatingBar rb;
    public static String MOVIEDETAILKEY="detail";
    public static String DETMOVIELST="detaillst";
    String srchtxt="";
    public static String MOVIEDETAILTXT="det";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        setTitle("Movie Details");

        title = (TextView) findViewById(R.id.titletxt);
        release = (TextView) findViewById(R.id.releasetxt);
        genre = (TextView) findViewById(R.id.genretxt);
        director = (TextView) findViewById(R.id.directortxt);
        actors = (TextView) findViewById(R.id.actorstxt);
        plot = (TextView) findViewById(R.id.plottxt);
        poster = (ImageView) findViewById(R.id.imagePoster);
        finishbtn = (Button) findViewById(R.id.finishbtn);
        rb = (RatingBar) findViewById(R.id.movieratingBar);
        rb.setNumStars(5);

        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailsActivity.this, SearchMoviesActivity.class);
                intent.putExtra(DETMOVIELST, movielist);
                intent.putExtra(MOVIEDETAILKEY, "details");
                intent.putExtra(MOVIEDETAILTXT, srchtxt);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.imgprevbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (index > 0) {
                    index = index - 1;
                    Log.d("demo", "Moviedetaillist index of prev movie clicked :" + index);
                    srchmovie = movielist.get(index);
                    new GetMoviesDetailAsyncTask().execute("http://www.omdbapi.com/?i=" + movielist.get(index).getImdbID());
                    new GetMoviePosterAsyncTask().execute(movielist.get(index).getPoster());
                } else {
                    Toast.makeText(MovieDetailsActivity.this, "This is first in list", Toast.LENGTH_SHORT).show();
                }

            }
        });


        findViewById(R.id.imgbtnnxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(index<(movielist.size()-1)){
                    index = index+1;
                    Log.d("demo","Moviedetaillist index of next movie clicked :"+ index);
                    srchmovie= movielist.get(index);
                    new GetMoviesDetailAsyncTask().execute("http://www.omdbapi.com/?i="+ movielist.get(index).getImdbID());
                    new GetMoviePosterAsyncTask().execute(movielist.get(index).getPoster());
                }
                else{
                    Toast.makeText(MovieDetailsActivity.this,"This is last in list",Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.imagePoster).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailsActivity.this, MovieWebViewActivity.class);
                intent.putExtra("imdbID", srchmovie.getImdbID());
                startActivity(intent);
            }
        });


        if(getIntent().getExtras() != null){
            srchmovie = (Movie) getIntent().getExtras().getSerializable(SearchMoviesActivity.MOVIEOBJ);
            Log.d("demo","moviedetail search movie: "+ srchmovie.getTitle());
            movielist = (ArrayList<Movie>) getIntent().getExtras().getSerializable(SearchMoviesActivity.MOVIELIST);
            srchtxt = getIntent().getExtras().getString(SearchMoviesActivity.SRCHTXT);

            if(movielist.size()>0){
                    Log.d("demo", "moviedetail movielist: "+movielist );
                    Log.d("demo", "moviedetail srch movie object: "+srchmovie );
            }
            else{
                Log.d("demo","moviedetail movielist is empty------------------!!!!!!!!!!");
            }



            for(int i=0; i<movielist.size();i++){

                Log.d("demo", "moviedetail movielist for loop titles of list : " + movielist.get(i).getTitle() );
                Log.d("demo", "moviedetail movielist for loop title of srcmovie : " + srchmovie.getTitle() );
                if( movielist.get(i).getTitle().toString().equals(srchmovie.getTitle().toString())){
                    index = i;

                    Log.d("demo","Moviedetaillist index of movie clicked :"+ index);
                }
                else{
                    Log.d("demo","Moviedetaillist no match :----------------!!!!!!!!!!!!!!!!!!!!!!!!");
                }
                Log.d("demo", "---------------------------------------------------------------------------------------------------------------- : " );

            }


            new GetMoviesDetailAsyncTask().execute("http://www.omdbapi.com/?i="+ srchmovie.getImdbID());
            new GetMoviePosterAsyncTask().execute(srchmovie.getPoster());


        }
        else{
            Toast.makeText(MovieDetailsActivity.this,"Empty list no movie", Toast.LENGTH_SHORT).show();
        }


        
    }

    private class GetMoviesDetailAsyncTask extends AsyncTask<String, Void, Movie> {
        @Override
        protected Movie doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
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
                    MovieDetailUtil.MovieJSONParser.parseMovie(sb.toString());
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MovieDetailsActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading Movie");
            progressDialog.show();
            progressDialog.setCancelable(false);

        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);

        }
    }

    private class GetMoviePosterAsyncTask extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {

            URL url;

            try {
                url = new URL(params[0]);
                Log.d("demo", "URL IN DEtAIL :" + url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                return image;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressDialog.dismiss();
            title.setText(srchmovie.getTitle() + "(" + srchmovie.getYear() + ")");
            release.setText(srchmovie.getReleased());
            genre.setText(srchmovie.getGenre());
            director.setText(srchmovie.getDirector());
            actors.setText(srchmovie.getActors());
            plot.setText(srchmovie.getPlot());
            poster.setImageBitmap(bitmap);
            rb.setRating(Float.parseFloat(srchmovie.getImdbRating()) / 2);

        }
    }
}
