package com.android.puzzleandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter2 extends  RecyclerView.Adapter<RecyclerViewHolder> {

    private final Context context;

    Integer[] menuImage = {
            R.drawable.mode3,
            R.drawable.mode4,
            R.drawable.mode5};

    Integer[] id = {1,2,3};

    LayoutInflater inflater;

    public RecyclerAdapter2(Context context) {
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
            Intent intent = new Intent();
            intent = new Intent(context, Mode.class);
            intent.putExtra("id", id[position]);
            context.startActivity(intent);
        }
    };

    @Override
    public int getItemCount() {
        return menuImage.length;
    }
}
