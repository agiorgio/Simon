package com.example.anthony.simon;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    int currentMove = 0;

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
        showMoves();

        for(int i = 0;i<ArrayButtons.length;i++)
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
                            }
                            else
                            {
                                Log.i("LOSE","DIE");
                            }
                            if(currentMove>=Moves.size())
                            {
                                generateMove();
                                showMoves();
                                currentMove=0;
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
    }

    private void unHighlight(final int i)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayButtons[i].setBackgroundColor(unHighlightedColors[i]);
            }
        });
    }
}
