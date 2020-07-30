package com.android.puzzleandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Rank extends AppCompatActivity {

    Button game;
    MediaPlayer click;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);

        click = MediaPlayer.create(this, R.raw.click);
        click.setLooping(false);

        RecyclerAdapter2 adapter=new RecyclerAdapter2(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        game = (Button) findViewById(R.id.game);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.start();
                Intent intent = new Intent(Rank.this, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}