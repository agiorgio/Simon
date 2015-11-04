package com.example.anthony.simon;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
        generateMove();
        generateColor(1);
    }

    private void generateMove() {

        Random rand = new Random();
        Moves.add(rand.nextInt(4));

    }

    private void generateColor(int i) {

        ArrayButtons[i].setBackgroundColor(Color.WHITE);

        //int color = Color.argb(ArrayButtons[0].setBackgroundColor(Color.DF0101);

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
}