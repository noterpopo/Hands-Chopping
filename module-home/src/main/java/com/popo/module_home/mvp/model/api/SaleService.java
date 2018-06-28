package com.popo.module_home.mvp.model.api;

import com.popo.module_home.mvp.model.entity.GameSaleList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.popo.module_home.mvp.model.Api.SANKO_DOMAIN;
import static com.popo.module_home.mvp.model.Api.STEAM_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface SaleService {
    @Headers({DOMAIN_NAME_HEADER+STEAM_DOMAIN_NAME})
    @GET("/search/?sort_by=Reviews_DESC&specials=1")
    Observable<GameSaleList> getSteamSaleGames();

    @Headers({DOMAIN_NAME_HEADER+SANKO_DOMAIN})
    @GET("v5/home.json?locale=js&sonkwo_version=1&sonkwo_client=web&q%5B%5D=sale_tagged_games")
    Observable<GameSaleList> getSankoSaleGames(@Query("_") String timestamp);

}
