/*
    Cole Howell, Manoj Bompada
    MovieDetailUtil.java
    ITCS 4180
 */

package example.com.imdbapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manoj on 2/26/2016.
 */
public class MovieDetailUtil {
    static public class MovieJSONParser {
        public static void parseMovie(String in) throws JSONException {

            JSONObject movieobj = new JSONObject(in);

            MovieDetailsActivity.srchmovie.setDirector(movieobj.getString("Director"));
            MovieDetailsActivity.srchmovie.setActors(movieobj.getString("Actors"));
            MovieDetailsActivity.srchmovie.setGenre(movieobj.getString("Genre"));

            String[] date = movieobj.getString("Released").split(" ");
            String dt = date[0];
            String mth = date[1];
            String yr = date[2];

            MovieDetailsActivity.srchmovie.setReleased(mth+" "+dt+" "+yr);
            MovieDetailsActivity.srchmovie.setImdbRating(movieobj.getString("imdbRating"));
            MovieDetailsActivity.srchmovie.setPlot(movieobj.getString("Plot"));
        }
    }
}
