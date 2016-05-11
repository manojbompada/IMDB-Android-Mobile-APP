/*
    Cole Howell, Manoj Bompada
    MainActivity.java
    ITCS 4180
 */

package example.com.imdbapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button searchButton;
    EditText search;

    public static String SEARCHTXT="Search";
   public static String MAINKEY="main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("IMDb Movie App");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        searchButton = (Button) findViewById(R.id.searchButton);
        search = (EditText) findViewById(R.id.searchMovie);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!search.getText().toString().equals("")){
                    String searchtxt = search.getText().toString();
                    Intent intent = new Intent(MainActivity.this, SearchMoviesActivity.class);
                    intent.putExtra(SEARCHTXT,searchtxt);
                    intent.putExtra(MAINKEY,"mainkey");
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"Please enter some movie title text",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}
