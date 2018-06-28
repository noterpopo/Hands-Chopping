package com.popo.module_detail.mvp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.popo.module_detail.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private Context context;
    private ArrayList<String> reviews;

    public ReviewAdapter(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        WebView review;

        public ViewHolder(View itemView) {
            super(itemView);
            review=itemView.findViewById(R.id.reviewitem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.detail_review_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String review=reviews.get(position);
        holder.review.loadData(review,"text/html", "UTF-8");
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
