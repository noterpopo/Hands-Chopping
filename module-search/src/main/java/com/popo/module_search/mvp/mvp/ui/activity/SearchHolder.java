package com.popo.module_search.mvp.mvp.ui.activity;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.popo.module_search.R;
import com.popo.module_search.mvp.mvp.model.entity.GameSearchBean;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

public class SearchHolder extends BaseHolder<GameSearchBean>{
    private ImageView imageView;
    private TextView titleTv;
    private TextView nowpriceTv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;

    public SearchHolder(View itemView) {
        super(itemView);
        mAppComponent= ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader=mAppComponent.imageLoader();
        imageView=(ImageView)itemView.findViewById(R.id.img);
        titleTv=(TextView)itemView.findViewById(R.id.name);
        nowpriceTv=(TextView)itemView.findViewById(R.id.nowprice);
    }

    @Override
    public void setData(GameSearchBean data, int position) {
        mImageLoader.loadImage(itemView.getContext(),
                CommonImageConfigImpl
                        .builder()
                        .url(data.getImgUrl())
                        .imageView(imageView)
                        .build());
        titleTv.setText(data.getTitle());
        nowpriceTv.setText(data.getPrice());
    }

    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.application(), CommonImageConfigImpl.builder()
                .imageViews(imageView)
                .build());
    }
}
