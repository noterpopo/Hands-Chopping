package com.popo.module_search.mvp.app.utils;

import com.popo.module_search.mvp.mvp.model.api.SearchService;
import com.popo.module_search.mvp.mvp.model.entity.GameSearchList;

import java.io.IOException;

import me.jessyan.armscomponent.commonsdk.utils.BaseConverterFactory;
import me.jessyan.armscomponent.commonsdk.utils.BaseResponseConverter;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.popo.module_search.mvp.mvp.model.Api.SANKO_DOMAIN;
import static com.popo.module_search.mvp.mvp.model.Api.STEAM_DOMAIN;

public class RetrofitFactory {
    private  static SearchService searchSteamService;
    private  static SearchService searchSankoService;
    public static  SearchService getSearchSteamService(){
        if(searchSteamService==null){
            searchSteamService=new Retrofit.Builder()
                    .baseUrl(STEAM_DOMAIN)
                    .addConverterFactory(new BaseConverterFactory<GameSearchList>() {
                        @Override
                        public BaseResponseConverter responseConverter() {
                            return new GameSearchListResponseConverter();
                        }
                    })
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(SearchService.class);
        }
        return searchSteamService;
    }

    public static  SearchService getSearchSankoService(){
        if(searchSankoService==null){
            searchSankoService=new Retrofit.Builder()
                    .baseUrl(SANKO_DOMAIN)
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request=chain.request()
                                            .newBuilder()
                                            .addHeader("Accept","application/vnd.sonkwo.v5+json")
                                            .addHeader("Accept-Encoding","deflate, br")
                                            .addHeader("Connection","keep-alive")
                                            .addHeader("Host","www.sonkwo.com")
                                            .addHeader("Referer","https://www.sonkwo.com/")
                                            .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36")
                                            .addHeader("X-Requested-With","XMLHttpRequest")
                                            .build();
                                    return chain.proceed(request);
                                }
                            }).build())
                    .addConverterFactory(new BaseConverterFactory<GameSearchList>() {
                        @Override
                        public BaseResponseConverter responseConverter() {
                            return new GameSearchListResponseConverter();
                        }
                    })
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(SearchService.class);
        }
        return searchSankoService;
    }
}
