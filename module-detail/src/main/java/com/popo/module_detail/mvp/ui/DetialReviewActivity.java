package com.popo.module_detail.mvp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.popo.module_detail.R;

import java.util.ArrayList;

import me.jessyan.armscomponent.commonsdk.core.RouterHub;


public class DetialReviewActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial_review);
        ArrayList<String> reviews=getIntent().getStringArrayListExtra("reviews");
        if(reviews==null){
            reviews=new ArrayList<>();
            reviews.add("没有评论哦~");
        }else if(reviews.isEmpty()){
            reviews.add("没有评论哦~");
        }
        recyclerView=(RecyclerView)findViewById(R.id.reviewlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ReviewAdapter(reviews));
    }
}
