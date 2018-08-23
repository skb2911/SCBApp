package com.example.subhamtandon.scbapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterForVideoCategories extends RecyclerView.Adapter<AdapterForVideoCategories.ViewHolder> {

    RecyclerView recyclerView;
    Context context;
    ArrayList<String> listOfNamesOfVideoCategories= new ArrayList<>();

    public AdapterForVideoCategories(RecyclerView recyclerView, Context context, ArrayList<String> listOfNamesOfVideoCategories) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.listOfNamesOfVideoCategories = listOfNamesOfVideoCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_of_video_categories, parent, false);
        return new AdapterForVideoCategories.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameOfVideoCategories.setText(listOfNamesOfVideoCategories.get(position));

    }

    @Override
    public int getItemCount() {
        return listOfNamesOfVideoCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameOfVideoCategories;

        public ViewHolder(View itemView) {
            super(itemView);
            nameOfVideoCategories = itemView.findViewById(R.id.nameOfVideoCategories);
        }
    }
}