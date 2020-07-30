package com.android.puzzleandroid;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerViewHolder> {

    private final Context context;

    Integer[] menuImage = {
            R.drawable.satu,
            R.drawable.dua,
            R.drawable.tiga};

    Integer[] id = {1,2,3};

    LayoutInflater inflater;

    public RecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_list, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.imageView.setImageResource(menuImage[position]);
        holder.imageView.setOnClickListener(clickListener);
        holder.imageView.setTag(holder);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();

            int position = vholder.getPosition();
            if (id[position] == 2 || id[position] == 3){
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent();
                intent = new Intent(context, GameActivity9.class);
                intent.putExtra("id", id[position]);
                context.startActivity(intent);
            }
        }
    };

    @Override
    public int getItemCount() {
        return menuImage.length;
    }
}
