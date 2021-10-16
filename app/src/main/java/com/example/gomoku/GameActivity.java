package com.example.gomoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class GameActivity extends AppCompatActivity {
    GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameBoard = findViewById(R.id.gameBoard) ;
    }

    public void resetGame(View view){
        gameBoard.resetGame();
    }
}