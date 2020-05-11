package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MainActivity handles the logic of tictactoe game
 */
public class MainActivity extends AppCompatActivity {

    /** Keep track which player's turn it is. captain america is player 1. iron man is player 2.*/
    int player = 1;
    /** Keep track of the empty and occupied spaces.
     * 0 means empty. 1 means occupied by player 1. 2 means occupied by player 2.
     */
    int[] gameState = {0,0,0,0,0,0,0,0,0};
    /** Keep track of whether the game can continue. */
    boolean gameActive = true;
    /** Remains true when there's a tie. */
    boolean tie = false;

    /** Stores all the winning position and checks if any player has fulfilled them. */
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, // horizontal winning positions
            {0,3,6}, {1,4,7}, {2,5,8}, // vertical winning positions
            {0,4,8}, {2,4,6}}; // diagonal winning positions

    /** Simulates a position being occupied by a counter (token). */
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int gridPosition = Integer.parseInt(counter.getTag().toString());

        if (gameState[gridPosition] == 0 && gameActive) { // if position is not occupied & game is active
            gameState[gridPosition] = player;
            counter.setTranslationY(-2000); // original starting point of token is above screen

            if(player == 1) {
                counter.setImageResource(R.drawable.capt);
            } else {
                counter.setImageResource(R.drawable.ironman);
            }

            for (int[] position : winningPositions) {
                if (gameState[position[0]] != 0 &&
                        gameState[position[0]] == gameState[position[1]]
                        && gameState[position[1]] == gameState[position[2]]) {

                    String playerName = "";
                    playerName = player == 1 ? "Captain America" : "Iron Man";
                    Toast.makeText(this, playerName + " has won", Toast.LENGTH_SHORT).show();
                    gameActive = false;

                    Button resetButton = (Button) findViewById(R.id.resetButton);
                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                    winnerTextView.setText(playerName + " has won");

                    resetButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);

                }
            }

            // swap player if game haven't ends
            if (player == 1) {
                player = 2;
            } else {
                player = 1;
            }
            counter.animate().translationYBy(2000).rotationBy(1080).setDuration(400); // animate dropping of counter

            tie = true;
            for (int i = 0; i < gameState.length; i++) {
                if(gameState[i] == 0) {
                    tie = false;
                }
            }
            if (tie) {
                Toast.makeText(this, "It's a Tie!", Toast.LENGTH_LONG).show();
                Button resetButton = (Button) findViewById(R.id.resetButton);
                resetButton.setVisibility(View.VISIBLE);
            }

        } else {
            // do nothing
        }
    }

    /** Resets the board to play again */
    public void playAgain(View view) {
        player = 1;
        gameState = new int[] {0,0,0,0,0,0,0,0,0};
        gameActive = true;

        Button resetButton = (Button) findViewById(R.id.resetButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        resetButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        androidx.gridlayout.widget.GridLayout grid = findViewById(R.id.gridLayout);
        for (int i = 0; i < grid.getChildCount(); i++) {
            ImageView counter = (ImageView) grid.getChildAt(i);
            counter.setImageDrawable(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
