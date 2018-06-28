package com.popo.module_detail.app.utils;

import com.popo.module_detail.mvp.model.api.DetailService;
import com.popo.module_detail.mvp.model.entity.GameDetailBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonsdk.utils.BaseConverterFactory;
import me.jessyan.armscomponent.commonsdk.utils.BaseResponseConverter;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.popo.module_detail.mvp.model.Api.SANKO_DOMAIN;
import static com.popo.module_detail.mvp.model.Api.STEAM_DOMAIN;

public class RetrofitFactory {
    private  static DetailService steamDetailService;
    private  static DetailService sankoDetailService;
    public static  DetailService getSteamDetailService(String id){
        if(steamDetailService==null){
            steamDetailService=new Retrofit.Builder()
                    .baseUrl(STEAM_DOMAIN)
                    .client(new OkHttpClient.Builder()
                            .followRedirects(false)
                            .followSslRedirects(false)
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request=chain.request()
                                            .newBuilder()
                                            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                                            .addHeader("Accept-Encoding","deflate, br")
                                            .addHeader("Accept-Language","zh-CN,zh;q=0.9,zh-TW;q=0.8")
                                            .addHeader("Connection","keep-alive")
                                            .addHeader("Host","store.steampowered.com")
                                            .addHeader("Referer","https://store.steampowered.com/app/"+id+"/agecheck")
                                            .addHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36")
                                            .addHeader("Cookie","mature_content=1; birthtime=758476801; lastagecheckage=14-January-1994")
                                            .build();
                                    return chain.proceed(request);
                                }
                            })
                    .build())
                    .addConverterFactory(new BaseConverterFactory<GameDetailBean>() {
                        @Override
                        public BaseResponseConverter responseConverter() {
                            return new GameDetailResponeConverter();
                        }
                    })
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(DetailService.class);
        }
        return steamDetailService;
    }

    public static  DetailService getSankoDetailService(String product_id,String id){
        sankoDetailService=new Retrofit.Builder()
                .baseUrl(SANKO_DOMAIN)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request=chain.request()
                                        .newBuilder()
                                        .addHeader("Accept","application/vnd.sonkwo.v5+json")
                                        .addHeader("Accept-Encoding","gzip, deflate, br")
                                        .addHeader("Connection","keep-alive")
                                        .addHeader("Referer","https://www.sonkwo.com/products/"+product_id+"?game_id="+id)
                                        .addHeader("Host","www.sonkwo.com")
                                        .addHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36")
                                        .addHeader("X-Requested-With","XMLHttpRequest")
                                        .build();
                                return chain.proceed(request);
                            }
                        }).build())
                .addConverterFactory(new BaseConverterFactory<GameDetailBean>() {
                    @Override
                    public BaseResponseConverter responseConverter() {
                        return new GameDetailResponeConverter();
                    }
                })
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DetailService.class);
        return sankoDetailService;
    }

}

class LocalCookieJar implements CookieJar {
    List<Cookie> cookies;
    @Override
    public List<Cookie> loadForRequest(HttpUrl arg0) {
        if (cookies != null)
            return cookies;
        return new ArrayList<Cookie>();
    }

    @Override
    public void saveFromResponse(HttpUrl arg0, List<Cookie> cookies) {
        this.cookies = cookies;
    }

}
