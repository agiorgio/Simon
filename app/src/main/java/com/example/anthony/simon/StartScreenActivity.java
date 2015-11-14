package com.example.anthony.simon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

public class StartScreenActivity extends AppCompatActivity {

    Button button_start;
    Button button_soundsettings;
    Button button_highscore;
    Button button_deletehighscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        //start button opens game
        button_start = (Button)findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreenActivity.this, GameActivity.class));
            }
        });

        button_highscore = (Button) findViewById(R.id.button_highscore);
        button_highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int highScore = fileRead();
                Toast.makeText(getApplicationContext(), "The high score is " + highScore + ".", Toast.LENGTH_SHORT).show();
            }
        });

        button_deletehighscore = (Button) findViewById(R.id.button_deletehighscore);
        button_deletehighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileDelete();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
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

    private int fileRead() {

        int score = 0;
        File fileDirectory = getFilesDir();
        File highscoreFile = new File(fileDirectory, "HS");

        if (!highscoreFile.exists()|| highscoreFile.length() == 0) {

            return 0;
        }

        try {
            FileInputStream inputStream = new FileInputStream(highscoreFile);
            score = inputStream.read();
            inputStream.close();
        } catch (java.io.IOException e) {
            return 0;
        }
        return score;
    }

    private void fileDelete() {

        File fileDirectory = getFilesDir();
        File highscoreFile = new File(fileDirectory, "HS");
        highscoreFile.delete();
    }
}
