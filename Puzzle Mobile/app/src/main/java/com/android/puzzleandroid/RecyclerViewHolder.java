package com.android.puzzleandroid;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    // ViewHolder akan mendeskripisikan item view yang ditempatkan di dalam RecyclerView.
    ImageView imageView, logo;  //deklarasi imageview

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        imageView= (ImageView) itemView.findViewById(R.id.daftar_icon);
        //menampilkan gambar atau icon pada widget Cardview pada id daftar_icon
    }
}
