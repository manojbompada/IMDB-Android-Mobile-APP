/*
    Cole Howell, Manoj Bompada
    Movie.java
    ITCS 4180
 */

package example.com.imdbapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by colehowell on 2/23/16.
 */
public class Movie implements Comparable<Movie>,Serializable {

    String title, imdbID,year, poster, released, genre, director, actors, plot, imdbRating;


    static public Movie createMovie(JSONObject js) throws JSONException{
        Movie movie = new Movie();

        movie.setTitle(js.getString("Title"));
        movie.setYear(js.getString("Year"));
        movie.setImdbID(js.getString("imdbID"));
        movie.setPoster(js.getString("Poster"));

        movie.setDirector("");
        movie.setActors("");
        movie.setGenre("");
        movie.setReleased("");
        movie.setImdbRating("");
        movie.setPlot("");

        return movie;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", poster='" + poster + '\'' +
                ", released='" + released + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", plot='" + plot + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    @Override
    public int compareTo(Movie another) {
        return Integer.compare(Integer.parseInt(this.year), Integer.parseInt(another.year));
    }
}
