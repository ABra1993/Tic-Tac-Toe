package example.promo.tictactoe;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // integer array containing id's of buttons
    int[] id = new int[]{R.id.imageButton1, R.id.imageButton2, R.id.imageButton3, R.id.imageButton4,
            R.id.imageButton5, R.id.imageButton6, R.id.imageButton7, R.id.imageButton8,
            R.id.imageButton9};


    // property initialization
    Game game;
    Boolean gameOver = false;
    String player1 = "";
    String player2 = "";
    int playersCount = 0;

    /**
     * This function saves the board's tiles and reloads them when the screen rotates (by using
     * Serializable).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();

        // checks for screen rotation
        if (savedInstanceState != null) {

            // retrieves and shows player's names
            playersCount = savedInstanceState.getInt("PlayersCount");
            String message = savedInstanceState.getString("Message");
            TextView textView = findViewById(R.id.textView2);
            textView.setText(message);

            if (playersCount == 1) {
                TextView textPlayer1 = findViewById(R.id.textView3);
                textPlayer1.setText(savedInstanceState.getString("Player 1"));
                player1 = textPlayer1.getText().toString();
            } else if (playersCount == 2) {
                TextView textPlayer1 = findViewById(R.id.textView3);
                textPlayer1.setText(savedInstanceState.getString("Player 1"));
                player1 = textPlayer1.getText().toString();
                TextView textPlayer2 = findViewById(R.id.textView4);
                textPlayer2.setText(savedInstanceState.getString("Player 2"));
                player2 = textPlayer2.getText().toString();
            }

            // changes board tiles to current state
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

            // checks whether board has three of the same tiles in a row
            Tile[] tiles = new Tile[] {Tile.CIRCLE, Tile.CROSS};

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int k = 0 ; k < 3; k++) {
                        GameState gamestate = game.check(j, k, tiles[i]);
                        if (gamestate == GameState.PLAYER_ONE || gamestate == GameState.PLAYER_TWO) {
                            gameOver = true;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); // always call super
        outState.putSerializable("Game", game);
        outState.putString("Player 1", player1);
        outState.putString("Player 2", player2);
        outState.putInt("PlayersCount", playersCount);
        TextView textView = findViewById(R.id.textView2);
        outState.putString("Message", textView.getText().toString());
    }

    /**
     * This function changes UI when a user clicks on one of the buttons. If one of the players
     * has won or all the boxes are filled the game is stopped.
     */
    @SuppressLint("SetTextI18n")
    public void tileClicked(View view) {

        if (!gameOver) {
            TextView textView = findViewById(R.id.textView2);
            textView.setText("");

            // if users have not entered names, 'set' button and textedit are made invisible
            if ( player1 == "") {
                Button button = findViewById(R.id.button);
                EditText editText = findViewById(R.id.editText);
                button.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.INVISIBLE);
            }

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

            // checks whether board has three of the same tiles in a row
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
                textView.setText(player1 + " wins!");
                gameOver = true;
            } else if (gamestate == GameState.PLAYER_TWO) {
                textView.setText(player2 + " wins!");
                gameOver = true;
            }
        }
    }

    // resets UI (i.e. empty board)
    public void resetClicked (View view){

        // retrieves and clears screen views
        TextView textView = findViewById(R.id.textView2);
        TextView textPlayer1 = findViewById(R.id.textView3);
        TextView textPlayer2 = findViewById(R.id.textView4);
        Button set = findViewById(R.id.button);
        EditText editText = findViewById(R.id.editText);

        set.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        editText.setHint("Player");
        editText.setText("");
        textView.setText("");
        textPlayer1.setText("");
        textPlayer2.setText("");

        // creates new game
        game = new Game();
        gameOver = false;
        playersCount = 0;

        // change board's tiles to blank
        for (int i = 0; i < 9; i++) {
            ImageButton button = findViewById(id[i]);
            button.setBackgroundResource(R.drawable.blank);
        }
    }

    // This function stores the name of the players
    @SuppressLint("SetTextI18n")
    public void setClicked(View view) {

    playersCount++;
    TextView textView = findViewById(R.id.textView2);
    TextView textPlayer1 = findViewById(R.id.textView3);
    TextView textPlayer2 = findViewById(R.id.textView4);
    Button button = findViewById(R.id.button);
    EditText editText = findViewById(R.id.editText);

    // adds player's names to screen
    if (playersCount == 1) {
        player1 = editText.getText().toString();
        editText.setHint("Player");
        textPlayer1.setText(player1);
    } else if (playersCount == 2) {
        player2 = editText.getText().toString();
        textPlayer2.setText(player2);
        editText.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        textView.setText("");
    }

    editText.setText("");

    }
}


