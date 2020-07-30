package com.android.puzzleandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    int backpress = 0;
    MediaPlayer audioBackground;
    MediaPlayer click;
    Button playbtn;
    ToggleButton sfx;
    ToggleButton music;
    boolean on;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        sfx = (ToggleButton) findViewById(R.id.sfx);
        music = (ToggleButton) findViewById(R.id.music);
        music.setBackgroundResource(R.drawable.musicon);

        click = MediaPlayer.create(this, R.raw.click);
        click.setLooping(false);

        playsound();
        playbtn = (Button) findViewById(R.id.playbtn);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopsound();
                click.start();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
            }
        });
    }

    private void playsound(){
        audioBackground = MediaPlayer.create(this, R.raw.menu);
        audioBackground.setLooping(false);
        audioBackground.start();
    }

    private void stopsound(){
        audioBackground.stop();
        audioBackground.release();
    }

    @Override
    public void onBackPressed() {
        backpress = (backpress + 1);
        Toast.makeText(getApplicationContext(), " Tekan sekali lagi untuk keluar ", Toast.LENGTH_SHORT).show();

        if (backpress>1) {
            audioBackground.stop();
            audioBackground.release();
            this.finish();
        }
    }

    public void musicclick(View view) {
        on = ((ToggleButton) view).isChecked();
        if (on) {
            /*Mematikan suara audio*/
            audioBackground.setVolume(0, 0);
            music.setBackgroundResource(R.drawable.musicoff);
        } else {
            /*Menghidupkan kembali audio background*/
            audioBackground.setVolume(1, 1);
            music.setBackgroundResource(R.drawable.musicon);
        }
    }
}