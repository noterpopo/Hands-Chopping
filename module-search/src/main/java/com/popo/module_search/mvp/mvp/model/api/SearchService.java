package com.popo.module_search.mvp.mvp.model.api;

import com.popo.module_search.mvp.mvp.model.entity.GameSearchList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.popo.module_search.mvp.mvp.model.Api.SANKO_DOMAIN;
import static com.popo.module_search.mvp.mvp.model.Api.STEAM_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface SearchService {
    @Headers({DOMAIN_NAME_HEADER+STEAM_DOMAIN_NAME})
    @GET("/search/")
    Observable<GameSearchList> getSteamSearchGames(@Query("term") String keyword);

    @Headers({DOMAIN_NAME_HEADER+SANKO_DOMAIN})
    @GET("search/skus.json?locale=js&sonkwo_version=1&sonkwo_client=web&per=20&page=1&q%5Bwhere%5D%5Bcate%5D=game&q%5Border%5D%5Breleased_at%5D=desc")
    Observable<GameSearchList> getSankoSearchGames(@Query("keyword") String keyword,@Query("_") String timestamp);


}
