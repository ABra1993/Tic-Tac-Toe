package example.promo.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    // integer array containing id's of buttons
    int[] id = new int[]{R.id.imageButton1, R.id.imageButton2, R.id.imageButton3, R.id.imageButton4,
            R.id.imageButton5, R.id.imageButton6, R.id.imageButton7, R.id.imageButton8,
            R.id.imageButton9};

    Game game;
    Boolean gameOver = false;

    /**
     * This function saves the board's tiles and reloads them when the screen rotates ( by using
     * Serializable).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game();

        if (savedInstanceState != null) {

            game = (Game) savedInstanceState.getSerializable("Game");
            ImageButton button;
            int idIB = 0;

            for (int i = 0; i < game.BOARD_SIZE; i++){
                for (int j = 0; j < game.BOARD_SIZE; j++) {
                    if (game.board[j][i] == Tile.CROSS) {
                        button = findViewById(id[idIB]);
                        button.setBackgroundResource(R.drawable.cross);
                    } else if (game.board[j][i] == Tile.CIRCLE) {
                        button = findViewById(id[idIB]);
                        button.setBackgroundResource(R.drawable.circle);
                    } else {
                        button = findViewById(id[idIB]);
                        button.setBackgroundResource(R.drawable.blank);
                    }
                    idIB++;
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); // always call super
        outState.putSerializable("Game", game);
    }

    /**
     * This function changes UI when a user clicks on one of the buttons. If one of the players
     * has won or all the boxes are filled the game is stopped.
     */
    public void tileClicked(View view) {

        if (gameOver == false) {
            TextView textView = findViewById(R.id.textView2);
            textView.setText("");


            // determines row and column of checked box
            int id = view.getId();
            int row;
            int column;

            if (id == R.id.imageButton1) {
                row = 0;
                column = 0;
            } else if (id == R.id.imageButton2) {
                row = 1;
                column = 0;
            } else if (id == R.id.imageButton3) {
                row = 2;
                column = 0;
            } else if (id == R.id.imageButton4) {
                row = 0;
                column = 1;
            } else if (id == R.id.imageButton5) {
                row = 1;
                column = 1;
            } else if (id == R.id.imageButton6) {
                row = 2;
                column = 1;
            } else if (id == R.id.imageButton7) {
                row = 0;
                column = 2;
            } else if (id == R.id.imageButton8) {
                row = 1;
                column = 2;
            } else {
                row = 2;
                column = 2;
            }

            // changes box to user's tile (i.e. cross or circle)
            Tile tile = game.draw(row, column);

            // checks whether board has three tiles in a row
            GameState gamestate = game.check(row, column, tile);

            // changes UI to user's tile (i.e. cross or circle)
            ImageButton button = findViewById(id);
            switch (tile) {
                case CROSS:
                    button.setBackgroundResource(R.drawable.cross);
                    break;
                case CIRCLE:
                    button.setBackgroundResource(R.drawable.circle);
                    break;
                case INVALID:
                    textView.setText("This box is already filled!");
                    break;
            }

            // returns statement if one of the player's has won the game
            if (gamestate == GameState.PLAYER_ONE) {
                textView.setText("Player one, you won!");
                gameOver = true;
            } else if (gamestate == GameState.PLAYER_TWO) {
                textView.setText("Player two, you won!");
                gameOver = true;
            }
        }
    }

    // resets UI (i.e. empty board)
    public void resetClicked (View view){

        game = new Game();
        gameOver = false;

        for (int i = 0; i < 9; i++) {
            ImageButton button = findViewById(id[i]);
            button.setBackgroundResource(R.drawable.border);
        }

        TextView textView = findViewById(R.id.textView2);
        textView.setText("Welcome!");
    }
    }
