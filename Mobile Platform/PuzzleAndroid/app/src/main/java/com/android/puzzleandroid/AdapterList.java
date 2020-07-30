package com.android.puzzleandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {
    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public AdapterList(Activity mainActivity, ArrayList<HashMap<String, String>> list_data) {
        this.context = mainActivity;
        this.list_data = list_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String angka = String.valueOf(position+1);
        holder.rank.setText(angka);
        holder.nama.setText(list_data.get(position).get("nama"));
        holder.langkah.setText(list_data.get(position).get("langkah"));
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        TextView langkah;
        TextView rank;

        public ViewHolder(View itemView) {
            super(itemView);

            rank = (TextView) itemView.findViewById(R.id.peringkat);
            nama = (TextView) itemView.findViewById(R.id.nama);
            langkah = (TextView) itemView.findViewById(R.id.langkah);
        }
    }
}
