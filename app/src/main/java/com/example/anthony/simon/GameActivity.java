package com.example.anthony.simon;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    int score = 0;
    int highscore;
    boolean gameover;
    int count = 0;
    int selectedbutton;

    Button ArrayButtons [] = new Button[4];
    ArrayList<Integer> Moves = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ArrayButtons[0] = (Button) findViewById(R.id.button_red);
        ArrayButtons[1] = (Button) findViewById(R.id.button_green);
        ArrayButtons[2] = (Button) findViewById(R.id.button_blue);
        ArrayButtons[3] = (Button) findViewById(R.id.button_yellow);

        generateMove();
        showMoves();

       ArrayButtons[0].setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {

               AsyncClass AsyncInstance = new AsyncClass();
               AsyncInstance.execute("red");

           }
       });

        ArrayButtons[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            }
        });

        ArrayButtons[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            }
        });

        ArrayButtons[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            }
        });
    }

    private void generateMove() {

        Random rand = new Random();
        Moves.add(rand.nextInt(4));

    }

    private void showMoves() {
        for (int i = 0; i < Moves.size(); i++)
        {
            pressButton(Moves.get(i));
        }
    }

    private void pressButton(int i) {

        try
        {
            Thread.sleep(3000);
            // red highlighted ArrayButtons[i].setBackgroundColor(Color.rgb(176, 23,31));
            // green highlighted ArrayButtons[i].setBackgroundColor(Color.rgb(0, 139, 69));
           // blue highlighted ArrayButtons[i].setBackgroundColor(Color.rgb(0, 0, 128));
            // yellow highlighted ArrayButtons[i].setBackgroundColor(Color.rgb(218, 165, 32));

        } catch (InterruptedException ex) {

        }


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

    public class AsyncClass extends AsyncTask<String, Integer, String[]> {

        @Override
        protected String[] doInBackground(String... params){

            try {
                Thread.sleep(3000);
                publishProgress(0);

                Thread.sleep(3000);
                publishProgress(1);

                Thread.sleep(3000);
                publishProgress(2);

                Thread.sleep(3000);
                publishProgress(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            ArrayButtons[values[0]].setBackgroundColor(Color.rgb(176, 23, 31));

        }
    }

}
