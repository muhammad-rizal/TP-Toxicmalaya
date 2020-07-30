package com.android.puzzleandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Mode extends AppCompatActivity {
    private RecyclerView leaderboard;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    String url;
    String array;

    ArrayList<HashMap<String, String>> list_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        try {
            if(getIntent().getExtras().getInt("id") == 1) {
                url = "https://crippled-ditches.000webhostapp.com/getdata3x3.php";
                array = "3x3";
                ImageView hasil = (ImageView) this.findViewById(R.id.imagetxt);
                hasil.setImageResource(R.drawable.txt3);
            }else if (getIntent().getExtras().getInt("id") == 2){
                url = "https://crippled-ditches.000webhostapp.com/getdata4x4.php";
                array = "4x4";
                ImageView hasil = (ImageView) this.findViewById(R.id.imagetxt);
                hasil.setImageResource(R.drawable.txt4);
            }else {
                url = "https://crippled-ditches.000webhostapp.com/getdata5x5.php";
                array = "5x5";
                ImageView hasil = (ImageView) this.findViewById(R.id.imagetxt);
                hasil.setImageResource(R.drawable.txt5);
            }
        } catch (Exception e) {
        }

        leaderboard = (RecyclerView) findViewById(R.id.leaderboard);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        leaderboard.setLayoutManager(llm);

        requestQueue = Volley.newRequestQueue(Mode.this);
        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(array);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", json.getString("id"));
                        map.put("nama", json.getString("nama"));
                        map.put("langkah", json.getString("langkah"));
                        list_data.add(map);
                        AdapterList adapter = new AdapterList(Mode.this, list_data);
                        leaderboard.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mode.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
}