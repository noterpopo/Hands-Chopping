package com.popo.module_home.mvp.ui.activity;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.popo.module_home.R;
import com.popo.module_home.mvp.model.entity.GameListBean;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

public class SaleHolder extends BaseHolder<GameListBean> {
    private ImageView imageView;
    private TextView titleTv;
    private TextView offTv;
    private TextView nowpriceTv;
    private TextView oldpriceTv;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;

    public SaleHolder(View itemView) {
        super(itemView);
        mAppComponent= ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader=mAppComponent.imageLoader();
        imageView=(ImageView)itemView.findViewById(R.id.img);
        titleTv=(TextView)itemView.findViewById(R.id.name);
        nowpriceTv=(TextView)itemView.findViewById(R.id.nowprice);
        oldpriceTv=(TextView)itemView.findViewById(R.id.oldprice);
        offTv=(TextView)itemView.findViewById(R.id.off);

    }

    @Override
    public void setData(GameListBean data, int position) {
        mImageLoader.loadImage(itemView.getContext(),
                CommonImageConfigImpl
                        .builder()
                        .url(data.getImgUrl())
                        .imageView(imageView)
                        .build());
        titleTv.setText(data.getTitle());
        offTv.setText(data.getOff());
        oldpriceTv.setText(data.getOldPrice());
        oldpriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        nowpriceTv.setText(data.getNowPrice());
    }

    @Override
    protected void onRelease() {
        mImageLoader.clear(mAppComponent.application(), CommonImageConfigImpl.builder()
                .imageViews(imageView)
                .build());
    }
}
