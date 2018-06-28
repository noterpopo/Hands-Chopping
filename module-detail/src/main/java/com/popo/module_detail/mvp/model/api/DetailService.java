package com.popo.module_detail.mvp.model.api;

import com.popo.module_detail.mvp.model.entity.GameDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.popo.module_detail.mvp.model.Api.SANKO_DOMAIN_NAME;
import static com.popo.module_detail.mvp.model.Api.STEAM_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface DetailService {
    /**
     * 游戏详情
     */
    @Headers({DOMAIN_NAME_HEADER+STEAM_DOMAIN_NAME})
    @GET("/app/{id}/{name}/")
    Observable<GameDetailBean> getSteamGameDetail(@Path("id") String id,@Path("name") String name);

    @Headers({DOMAIN_NAME_HEADER+STEAM_DOMAIN_NAME})
    @GET("/bundle/{id}/{name}/")
    Observable<GameDetailBean> getSteamGameDetailForBundle(@Path("id") String id,@Path("name") String name);

    /**
     * 游戏评论
     */
    @Headers({DOMAIN_NAME_HEADER+STEAM_DOMAIN_NAME})
    @GET("/appreviews/{id}?start_offset=0&day_range=30&start_date=-1&end_date=-1&date_range_type=all&filter=summary&language=schinese&l=schinese&review_type=all&purchase_type=all&review_beta_enabled=1")
    Observable<GameDetailBean> getSteamGameReviews(@Path("id") String id);

    /**
     * 游戏详情
     */
    @Headers({DOMAIN_NAME_HEADER+SANKO_DOMAIN_NAME})
    @GET("/products/{id}.json?locale=js&sonkwo_version=1&sonkwo_client=web")
    Observable<GameDetailBean> getSankoGameReviews(@Path("id") String id , @Query("_") String timestamp);

}
