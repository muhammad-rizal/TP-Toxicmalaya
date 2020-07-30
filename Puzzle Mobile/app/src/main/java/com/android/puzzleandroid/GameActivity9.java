package com.android.puzzleandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class GameActivity9 extends Activity {
    private final int N = 3;
    Cards cards;
    private ImageButton[][] button;
    private final int BUTTON_ID[][] = {{R.id.b900, R.id.b901, R.id.b902},
            {R.id.b910, R.id.b911, R.id.b912},
            {R.id.b920, R.id.b921, R.id.b922}};
    private final int CARDS_ID[] = {R.drawable.card900, R.drawable.card901, R.drawable.card902,
            R.drawable.card903, R.drawable.card904, R.drawable.card905,
            R.drawable.card906, R.drawable.card907, R.drawable.card908};

    private TextView tScore;
    private int numbSteps;
    private TextView tBestScore;
    private int numbBestSteps;
    ToggleButton music;
    ToggleButton sfx;

    MediaPlayer audioBackground;
    MediaPlayer clickSound;
    MediaPlayer victorySound;
    Dialog myDialog;

    boolean on;
    private boolean check;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game9);

        myDialog = new Dialog(this);
        victorySound = MediaPlayer.create(this, R.raw.victory);
        victorySound.setVolume(1,1);
        clickSound = MediaPlayer.create(this, R.raw.click);
        clickSound.setVolume(1,1);
        audioBackground = MediaPlayer.create(this, R.raw.game1);
        audioBackground.setVolume(1,1);
        audioBackground.setLooping(true);
        audioBackground.start();

        button = new ImageButton[N][N];
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++) {
                button[i][j] = (ImageButton) this.findViewById(BUTTON_ID[i][j]);
                button[i][j].setOnClickListener(onClickListener);
            }

        try {
            if(getIntent().getExtras().getInt("id") == 1) {
                ImageView hasil = (ImageView) this.findViewById(R.id.hasil);
                hasil.setImageResource(R.drawable.wbr);
            }
        } catch (Exception e) {
        }

        Button acak = (Button) this.findViewById(R.id.acak);
        acak.setOnClickListener(onClickListener);
        tScore = (TextView) this.findViewById(R.id.tScore);
        tBestScore = (TextView) this.findViewById(R.id.tBestScore);
        sfx = (ToggleButton) findViewById(R.id.sfx);
        music = (ToggleButton) findViewById(R.id.music);

        Button home = (Button) this.findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioBackground.stop();
                clickSound.start();
                newGame();
                Intent intent = new Intent(GameActivity9.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cards = new Cards(N, N);
        try {
            if(getIntent().getExtras().getInt("keyGame") == 1) {
                continueGame();
                checkFinish();
            } else newGame();
        } catch (Exception e) {
            newGame();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!check)
                for(int i = 0; i < N; i++)
                    for(int j = 0; j < N; j++)
                        if(v.getId() == BUTTON_ID[i][j])
                            buttonFunction(i, j);

            switch(v.getId()) {
                case R.id.acak:
                    newGame();
                    break;
                default:
                    break;
            }
        }
    };

    public void buttonFunction(int row, int columb) {
        cards.moveCards(row, columb);
        if(cards.resultMove()) {
            clickSound.start();
            numbSteps++;
            showGame();
            checkFinish();
        }
    }

    public void newGame() {
        clickSound.start();
        cards.getNewCards();
        numbSteps = 0;
        numbBestSteps = Integer.parseInt(readFile("fbs9"));
        tBestScore.setText(Integer.toString(numbBestSteps));
        showGame();
        check = false;
    }

    private void continueGame() {
        String text = getPreferences(MODE_PRIVATE).getString("savePyatnashki", "");
        int k = 0;
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++) {
                cards.setValueBoard(i, j,  Integer.parseInt("" + text.charAt(k) + text.charAt(k + 1)));
                k += 2;
            }

        numbSteps = Integer.parseInt(readFile("fileScore"));
        numbBestSteps = Integer.parseInt(readFile("fbs9"));
        tBestScore.setText(Integer.toString(numbBestSteps));

        showGame();
        check = false;
    }

    private void saveValueBoard() {
        String text = "";
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++) {
                if(cards.getValueBoard(i, j) < 10)
                    text += "0" + cards.getValueBoard(i, j);
                else text += cards.getValueBoard(i, j);
            }

        getPreferences(MODE_PRIVATE).edit().putString("savePyatnashki", text).commit();
        writeFile(Integer.toString(numbSteps), "fileScore");
        writeFile(Integer.toString(N), "fileN");
    }

    public void showGame() {
        tScore.setText(Integer.toString(numbSteps));

        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                button[i][j].setImageResource(CARDS_ID[cards.getValueBoard(i, j)]);
    }

    public void checkFinish(){
        if(cards.finished(N, N)){
            showGame();
            audioBackground.stop();
            victorySound.start();
            final Integer id = getIntent().getExtras().getInt("id");
            myDialog.setContentView(R.layout.activity_finish);

            final EditText name = (EditText) myDialog.findViewById(R.id.nama);
            pd = new ProgressDialog(this);
            Button homebtn = (Button) myDialog.findViewById(R.id.homebtn);
            Button ulangbtn = (Button) myDialog.findViewById(R.id.ulangbtn);
            Button nextbtn = (Button) myDialog.findViewById(R.id.nextbtn);
            TextView Score = (TextView) myDialog.findViewById(R.id.Score);
            TextView bestScore = (TextView) myDialog.findViewById(R.id.bestScore);
            ImageView hs = (ImageView) myDialog.findViewById(R.id.hs);

            Button save = (Button) myDialog.findViewById(R.id.save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nama = name.getText().toString().trim();
                    String langkah = Integer.toString(numbSteps);
                    if (!nama.isEmpty()) {
                        AddScore(nama,langkah);
                    } else if (nama.isEmpty()) {
                        name.setError("nama tidak boleh kosong");
                        name.requestFocus();
                    }
                }
            });

            Score.setText(Integer.toString(numbSteps));
            if ((numbSteps < numbBestSteps) || (numbBestSteps == 0)) {
                writeFile(Integer.toString(numbSteps), "fbs9");
                tBestScore.setText(Integer.toString(numbSteps));
                bestScore.setText(Integer.toString(numbSteps));
                hs.setImageResource(R.drawable.hs);
            }else {
                bestScore.setText(Integer.toString(numbBestSteps));
            }

            homebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickSound.start();
                    Intent intent = new Intent(GameActivity9.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            ulangbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newGame();
                    clickSound.start();
                    myDialog.dismiss();
                }
            });

            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickSound.start();
                    Intent intent = new Intent(GameActivity9.this, GameActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
            check = true;
        }
    }

    private void AddScore(final String nama, final String langkah){
        class AddEmployee extends AsyncTask<Void,Void,String > {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        GameActivity9.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                Toast.makeText(GameActivity9.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_NAMA,nama);
                params.put(konfigurasi.KEY_EMP_LANGKAH,langkah);
                RequestHandler requestHandler = new RequestHandler();
                return requestHandler.sendPostRequest(konfigurasi.URL_ADD,params);
            }
        }

        new AddEmployee().execute();
    }

    private void reset(){

    }

    public void writeFile(String text, String file) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(file, MODE_PRIVATE)));
            bw.write(text);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String file) {
        String text;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(file)));
            text = br.readLine();
        } catch(IOException e) {
            text = "0";
        }
        return text;
    }

    public void sfxclick(View view) {
        on = ((ToggleButton) view).isChecked();
        if (on) {
            /*Mematikan suara audio*/
            clickSound.setVolume(0, 0);
            sfx.setBackgroundResource(R.drawable.sfxoff);
        } else {
            /*Menghidupkan kembali audio background*/
            clickSound.setVolume(1, 1);
            sfx.setBackgroundResource(R.drawable.sfxon);
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
