package com.example.anthony.simon;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    int currentMove = 0;
    int userScore = 0;
    int highScore = 0;

    Button ArrayButtons [] = new Button[4];
    ArrayList<Integer> Moves = new ArrayList<Integer>();

    MediaPlayer redsoundMP;
    MediaPlayer greensoundMP;
    MediaPlayer bluesoundMP;
    MediaPlayer yellowsoundMP;

    MediaPlayer [] sounds = new MediaPlayer[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ArrayButtons[0] = (Button) findViewById(R.id.button_red);
        ArrayButtons[1] = (Button) findViewById(R.id.button_green);
        ArrayButtons[2] = (Button) findViewById(R.id.button_blue);
        ArrayButtons[3] = (Button) findViewById(R.id.button_yellow);

        redsoundMP = MediaPlayer.create(this, R.raw.red_button_sound);
        greensoundMP = MediaPlayer.create(this, R.raw.green_button_sound);
        bluesoundMP = MediaPlayer.create(this, R.raw.blue_button_sound);
        yellowsoundMP = MediaPlayer.create(this, R.raw.yellow_button_sound);

        sounds [0] = redsoundMP;
        sounds [1] = greensoundMP;
        sounds [2] = bluesoundMP;
        sounds [3] = yellowsoundMP;

        generateMove();
        showMoves();

        for(int     i = 0;i<ArrayButtons.length;i++)
        {
            final int q = i;
            ArrayButtons[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            highlight(q);

                            return true;
                        case MotionEvent.ACTION_UP:
                            unHighlight(q);
                            if(q==Moves.get(currentMove))
                            {
                                currentMove++;
                                userScore++;
                                ScoreInProgress();
                            }
                            else
                            {
                                highScore = fileRead();
                                if (userScore > highScore) {
                                    highScore = userScore;
                                    fileWrite(userScore);
                                }

                                Toast.makeText(getApplicationContext(), "Game over, your score is " + userScore +
                                        ". The high score is " + highScore + ".", Toast.LENGTH_SHORT).show();
                                resetGame();
                            }
                            if(currentMove>=Moves.size())
                            {
                                generateMove();
                                showMoves();
                                currentMove = 0;
                            }
                            return true;
                    }
                    return true;
                }
            });
        }
    }

    private void generateMove() {

        Random rand = new Random();
        Moves.add(rand.nextInt(4));

    }

    private void showMoves() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setButtonsEnabled(false);
                for (int i = 0; i < Moves.size(); i++)
                {
                    highlight(Moves.get(i));
                    pause(1000);
                    unHighlight(Moves.get(i));
                    pause(250);
                }
                setButtonsEnabled(true);
            }
        }).start();
    }

    private void setButtonsEnabled(final boolean enabled)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(Button b: ArrayButtons)
                {
                    b.setEnabled(enabled);
                }
            }
        });

    }

    private void pause(long millis)
    {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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

    int R1 = Color.parseColor("#FF0000");
    int G1 = Color.parseColor("#00FF00");
    int B1 = Color.parseColor("#0000FF");
    int Y1 = Color.parseColor("#FFFF00");
    int R2 = Color.parseColor("#7F0000");
    int G2 = Color.parseColor("#007F00");
    int B2 = Color.parseColor("#00007F");
    int Y2 = Color.parseColor("#7F7F00");

    int[] highlightedColors = {R2,G2,B2,Y2};
    int[] unHighlightedColors = {R1,G1,B1,Y1};

    private void highlight(final int i)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayButtons[i].setBackgroundColor(highlightedColors[i]);
            }
        });

        sounds[i].start();
    }

    private void unHighlight(final int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayButtons[i].setBackgroundColor(unHighlightedColors[i]);
            }
        });

        sounds[i].pause();
        sounds[i].seekTo(0);
    }

    private int fileRead () {

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

    private void fileWrite(int score) {

        File fileDirectory = getFilesDir();
        File highscoreFile = new File(fileDirectory, "HS");

        try {
            if (!highscoreFile.exists()) {
                highscoreFile.createNewFile();
            }
                //FileOutputStream outputStream = new FileOutputStream(highscoreFile, false);
                BufferedWriter outputStream = new BufferedWriter(new FileWriter(highscoreFile, false));
                outputStream.write(score);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private void ScoreInProgress () {
        final TextView scoreView = (TextView) findViewById(R.id.textView_userScore);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreView.setText(userScore + "");
            }
        });
    }

    private void resetGame() {
        userScore = 0;
        ScoreInProgress();
        Moves.clear();
    }
}
