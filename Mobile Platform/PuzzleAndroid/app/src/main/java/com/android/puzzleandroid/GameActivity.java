package com.android.puzzleandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class GameActivity extends AppCompatActivity {
    private final int N = 4;
    Cards cards;
    private ImageButton[][] button;
    private final int BUTTON_ID[][] = {{R.id.b1500, R.id.b1501, R.id.b1502, R.id.b1503},
            {R.id.b1510, R.id.b1511, R.id.b1512, R.id.b1513},
            {R.id.b1520, R.id.b1521, R.id.b1522, R.id.b1523},
            {R.id.b1530, R.id.b1531, R.id.b1532, R.id.b1533}};
    private final int CADRS_ID[] = {R.drawable.card1500, R.drawable.card1501, R.drawable.card1502, R.drawable.card1503,
            R.drawable.card1504, R.drawable.card1505, R.drawable.card1506, R.drawable.card1507,
            R.drawable.card1508, R.drawable.card1509, R.drawable.card1510, R.drawable.card1511,
            R.drawable.card1512, R.drawable.card1513, R.drawable.card1514, R.drawable.card1515};

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        button = new ImageButton[N][N];
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++) {
                button[i][j] = (ImageButton) this.findViewById(BUTTON_ID[i][j]);
                button[i][j].setOnClickListener(onClickListener);
            }

        try {
            if(getIntent().getExtras().getInt("id") == 1) {
                Integer id = getIntent().getExtras().getInt("id");
                ImageView hasil = (ImageView) this.findViewById(R.id.hasil);
                hasil.setImageResource(R.drawable.wbr);
            }
        } catch (Exception e) {
        }

        myDialog = new Dialog(this);
        victorySound = MediaPlayer.create(this, R.raw.victory);
        victorySound.setVolume(1,1);
        clickSound = MediaPlayer.create(this, R.raw.click);
        clickSound.setVolume(1,1);
        audioBackground = MediaPlayer.create(this, R.raw.game2);
        audioBackground.setVolume(1,1);
        audioBackground.setLooping(true);
        audioBackground.start();

        Button acak = (Button) this.findViewById(R.id.acak);
        acak.setOnClickListener(onClickListener);

        Button home = (Button) this.findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioBackground.stop();
                newGame();
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tScore = (TextView) this.findViewById(R.id.tScore);
        tBestScore = (TextView) this.findViewById(R.id.tBestScore);
        sfx = (ToggleButton) findViewById(R.id.sfx);
        music = (ToggleButton) findViewById(R.id.music);


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
        numbBestSteps = Integer.parseInt(readFile("fbs15"));
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
        numbBestSteps = Integer.parseInt(readFile("fbs15"));
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
                button[i][j].setImageResource(CADRS_ID[cards.getValueBoard(i, j)]);
    }

    public void checkFinish(){
        if(cards.finished(N, N)){
            audioBackground.stop();
            showGame();
            victorySound.start();
            final Integer id = getIntent().getExtras().getInt("id");
            myDialog.setContentView(R.layout.activity_finish);

            final EditText name = (EditText) myDialog.findViewById(R.id.nama);
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
                writeFile(Integer.toString(numbSteps), "fbs15");
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
                    Intent intent = new Intent(GameActivity.this, MainActivity.class);
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
                    Intent intent = new Intent(GameActivity.this, GameActivity24.class);
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
                        GameActivity.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                Toast.makeText(GameActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_NAMA,nama);
                params.put(konfigurasi.KEY_EMP_LANGKAH,langkah);
                RequestHandler requestHandler = new RequestHandler();
                return requestHandler.sendPostRequest(konfigurasi.URL_ADD4,params);
            }
        }

        new AddEmployee().execute();
    }

    private void reset(){

    }

    public void writeFile(String text, String FILENAME) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME, MODE_PRIVATE)));
            bw.write(text);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String FILENAME) {
        String text;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
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