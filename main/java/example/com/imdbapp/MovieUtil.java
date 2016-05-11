/*
    Cole Howell, Manoj Bompada
    MovieUtil.java
    ITCS 4180
 */

package example.com.imdbapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by colehowell on 2/23/16.
 */
public class MovieUtil {

    static public  class MovieJSONParser{
        static ArrayList<Movie> parseMovie(String in) throws JSONException {
            ArrayList<Movie> movieList = new ArrayList<Movie>();

            JSONObject root = new JSONObject(in);
            JSONArray movieJSONArray = root.getJSONArray("Search");

            for (int i = 0; i < movieJSONArray.length(); i++){
                JSONObject movieJSONObject = movieJSONArray.getJSONObject(i);
                Movie movie = Movie.createMovie(movieJSONObject);

                movieList.add(movie);
                Collections.sort(movieList);
            }

            return movieList;
        }
    }
}

