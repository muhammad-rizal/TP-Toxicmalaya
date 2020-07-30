package com.android.puzzleandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    MediaPlayer click;
    Button homebtn;
    Button rank;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);

        click = MediaPlayer.create(this, R.raw.click);
        click.setLooping(false);

        RecyclerAdapter adapter=new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        homebtn = (Button) findViewById(R.id.homebtn);
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.start();
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
            }
        });

        rank = (Button) findViewById(R.id.rank);
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.start();
                Intent intent = new Intent(MenuActivity.this, Rank.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}