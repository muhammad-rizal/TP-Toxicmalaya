package com.android.puzzleandroid;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
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

public class GameActivity24 extends Activity {

    private final int N = 5;
    Cards cards;
    private ImageButton[][] button;
    private final int BUT_ID[][] = {{R.id.b2400, R.id.b2401, R.id.b2402, R.id.b2403, R.id.b2404},
            {R.id.b2410, R.id.b2411, R.id.b2412, R.id.b2413, R.id.b2414},
            {R.id.b2420, R.id.b2421, R.id.b2422, R.id.b2423, R.id.b2424},
            {R.id.b2430, R.id.b2431, R.id.b2432, R.id.b2433, R.id.b2434},
            {R.id.b2440, R.id.b2441, R.id.b2442, R.id.b2443, R.id.b2444}};
    private final int CARD_ID[] = {R.drawable.card2400, R.drawable.card2401, R.drawable.card2402, R.drawable.card2403, R.drawable.card2404,
            R.drawable.card2405, R.drawable.card2406, R.drawable.card2407, R.drawable.card2408, R.drawable.card2409,
            R.drawable.card2410, R.drawable.card2411, R.drawable.card2412, R.drawable.card2413, R.drawable.card2414,
            R.drawable.card2415, R.drawable.card2416, R.drawable.card2417, R.drawable.card2418, R.drawable.card2419,
            R.drawable.card2420, R.drawable.card2421, R.drawable.card2422, R.drawable.card2423, R.drawable.card2424};

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
        setContentView(R.layout.activity_game24);

        button = new ImageButton[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                button[i][j] = (ImageButton) this.findViewById(BUT_ID[i][j]);
                button[i][j].setOnClickListener(onClickListener);
            }

        Button acak = (Button) this.findViewById(R.id.acak);
        acak.setOnClickListener(onClickListener);

        myDialog = new Dialog(this);
        victorySound = MediaPlayer.create(this, R.raw.victory);
        victorySound.setVolume(1,1);
        clickSound = MediaPlayer.create(this, R.raw.click);
        clickSound.setVolume(1,1);
        audioBackground = MediaPlayer.create(this, R.raw.game3);
        audioBackground.setVolume(1,1);
        audioBackground.setLooping(true);
        audioBackground.start();

        Button home = (Button) this.findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioBackground.stop();
                newGame();
                Intent intent = new Intent(GameActivity24.this, MainActivity.class);
                startActivity(intent);
            }
        });

        try {
            if(getIntent().getExtras().getInt("id") == 1) {
                ImageView hasil = (ImageView) this.findViewById(R.id.hasil);
                hasil.setImageResource(R.drawable.wbr);
            }
        } catch (Exception e) {
        }

        tScore = (TextView) this.findViewById(R.id.tScore);
        tBestScore = (TextView) this.findViewById(R.id.tBestScore);
        sfx = (ToggleButton) findViewById(R.id.sfx);
        music = (ToggleButton) findViewById(R.id.music);

        AssetManager AM = getAssets();


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
            if (!check)
                for (int i = 0; i < N; i++)
                    for (int j = 0; j < N; j++)
                        if (v.getId() == BUT_ID[i][j])
                            butFunc(i, j);

            switch (v.getId()) {
                case R.id.acak:
                    newGame();
                    break;
                default:
                    break;
            }
        }
    };

    public void butFunc(int row, int columb) {
        cards.moveCards(row, columb);
        if (cards.resultMove()) {
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
        numbBestSteps = Integer.parseInt(readFile("fbs24"));
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
        numbBestSteps = Integer.parseInt(readFile("fbs24"));
        tBestScore.setText(Integer.toString(numbBestSteps));

        showGame();
        check = false;
    }

    public void backMenu() {
        saveValueBoard();
        Intent intent = new Intent(GameActivity24.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
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

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                button[i][j].setImageResource(CARD_ID[cards.getValueBoard(i, j)]);

    }

    public void checkFinish(){
        if(cards.finished(N, N)){
            showGame();
            audioBackground.stop();
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
                writeFile(Integer.toString(numbSteps), "fbs24");
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
                    Intent intent = new Intent(GameActivity24.this, MainActivity.class);
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
                    Intent intent = new Intent(GameActivity24.this, MenuActivity.class);
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
                        GameActivity24.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                Toast.makeText(GameActivity24.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_NAMA,nama);
                params.put(konfigurasi.KEY_EMP_LANGKAH,langkah);
                RequestHandler requestHandler = new RequestHandler();
                return requestHandler.sendPostRequest(konfigurasi.URL_ADD5,params);
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
