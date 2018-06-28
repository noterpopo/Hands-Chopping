package com.popo.module_home.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.popo.module_home.app.utils.RetrofitFactory;
import com.popo.module_home.mvp.contract.SaleContract;
import com.popo.module_home.mvp.model.entity.GameSaleList;


import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class SaleModel extends BaseModel implements SaleContract.Model{
    @Inject SaleModel(IRepositoryManager repositoryManager){super(repositoryManager);}

    @Override
    public Observable<GameSaleList> getSteamSaleGame() {
        return RetrofitFactory.getSearchSteamService().getSteamSaleGames();
    }

    @Override
    public Observable<GameSaleList> getSankoSaleGame() {
        return RetrofitFactory.getSearchSankoService().getSankoSaleGames(String.valueOf(System.currentTimeMillis()));
    }
}
