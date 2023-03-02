package com.leopard4.alcoholrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class GameActivity extends AppCompatActivity {

    ImageButton ibtnFace, ibtnToast, ibtnDice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ibtnFace = findViewById(R.id.ibtnFace);
        ibtnToast = findViewById(R.id.ibtnToast);
        ibtnDice = findViewById(R.id.ibtnDice);

        ibtnFace.setOnClickListener(view -> {
            Intent intent = new Intent(GameActivity.this, GameFaceActivity.class);
            startActivity(intent);
        });

        ibtnToast.setOnClickListener(view -> {
            Intent intent = new Intent(GameActivity.this, GameToastActivity.class);
            startActivity(intent);
        });

        ibtnDice.setOnClickListener(view -> {
            Intent intent = new Intent(GameActivity.this, GameDiceActivity.class);
            startActivity(intent);
        });

    }
}