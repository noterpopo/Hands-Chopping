package com.popo.module_search.mvp.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.popo.module_search.mvp.app.utils.RetrofitFactory;
import com.popo.module_search.mvp.mvp.contract.SearchContarct;
import com.popo.module_search.mvp.mvp.model.api.SearchService;
import com.popo.module_search.mvp.mvp.model.entity.GameSearchList;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class SearchModel extends BaseModel implements SearchContarct.Model{
    @Inject
    SearchModel(IRepositoryManager repositoryManager){super(repositoryManager);}

    @Override
    public Observable<GameSearchList> getSteamSearchGame(String key) {
        return RetrofitFactory.getSearchSteamService().getSteamSearchGames(key);
    }

    @Override
    public Observable<GameSearchList> getSankoSearchGame(String key) {
        return RetrofitFactory.getSearchSankoService().getSankoSearchGames(key,String.valueOf(System.currentTimeMillis()));
    }
}
