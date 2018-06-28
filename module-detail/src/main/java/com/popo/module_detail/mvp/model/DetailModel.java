package com.popo.module_detail.mvp.model;

import android.os.AsyncTask;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.popo.module_detail.app.utils.GameDetaiListener;
import com.popo.module_detail.app.utils.GetGameReviewListener;
import com.popo.module_detail.app.utils.RetrofitFactory;
import com.popo.module_detail.app.utils.SankoGetGameDetailTask;
import com.popo.module_detail.app.utils.SankoGetGameReview;
import com.popo.module_detail.app.utils.SankoListener;
import com.popo.module_detail.mvp.contract.DetailContract;
import com.popo.module_detail.mvp.model.entity.GameDetailBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.utils.HtmlUtil;
import timber.log.Timber;

@ActivityScope
public class DetailModel extends BaseModel implements DetailContract.Model{
    @Inject
    DetailModel(IRepositoryManager repositoryManager){super(repositoryManager);}

    @Override
    public Observable<GameDetailBean> getSteamGameDetail(String id, String name,Boolean isBundle) {
        if(isBundle){
            return RetrofitFactory.getSteamDetailService(id).getSteamGameDetailForBundle(id, name);
        }
        return RetrofitFactory.getSteamDetailService(id).getSteamGameDetail(id, name);
    }

    @Override
    public Observable<GameDetailBean> getSteamGameReview(String id, String name) {
        return RetrofitFactory.getSteamDetailService(id).getSteamGameReviews(id);
    }

    @Override
    public Observable<GameDetailBean> getSankoGameDetail(String product_id, String id) {

        return RetrofitFactory.getSankoDetailService(product_id,id).getSankoGameReviews(product_id,String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void getSankoGame(String id, String name, SankoListener listener) {
        GameDetailBean bean=new GameDetailBean();
        SankoGetGameDetailTask task=new SankoGetGameDetailTask(new GameDetaiListener() {
            @Override
            public void onGetted(String data) {
                parseJson(HtmlUtil.Unicode2String(data),bean);
                getSankoGameReview(listener,id,name,bean.getId(),bean);
            }
        });
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,id+"&"+name);
    }

    private void getSankoGameReview(SankoListener listener,String id,String name,String groupid,GameDetailBean bean){
        SankoGetGameReview task=new SankoGetGameReview(new GetGameReviewListener() {
            @Override
            public void onGetted(String s) {
                int i=0;
                Pattern p=Pattern.compile("\"content\"[\\s\\S]*?,");
                Matcher m=p.matcher(s);
                while (m.find()){
                    String review=s.substring(m.start()+11,m.end()-2);
                    bean.getReviews().add(review);
                    i++;
                    Timber.tag("Sanko").w(review+" "+i);
                }
                listener.onfinished(bean);
            }
        });
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,id+"&"+name+"&"+groupid);
    }

    private GameDetailBean parseJson(String json,GameDetailBean bean){
        Pattern p;
        Matcher m;

        int i=0;

        String introduct=null;
        String snapshots=null;
        String sysReq=null;
        String price=null;
        String img=null;
        String title=null;
        String id=null;

        String introRegex="\"introduce\"[\\s\\S]*?\\},";
        String snapshotsRegex="\"url\":[\\s\\S]*?\\}";
        String sysReqRegex="\"system_requirements\":[\\s\\S]*?\\]";
        String priceRegex="\"list_price\"[\\s\\S]*?,";
        String imgRegex="\"sku_cover\"[\\s\\S]*?,";
        String titleRegex="\"sku_name\"[\\s\\S]*?,";
        String idRegex="\"group\":[\\s\\S]*?,";

        p=Pattern.compile(introRegex);
        m=p.matcher(json);
        while (m.find()){
            introduct=json.substring(m.start()+24,m.end()-3);
            introduct=introduct.replaceAll("\",\"show_copyright\":\"","").replaceAll("\",\"show_headline\":\"","").replaceAll("\",\"show_description\":\"","").replaceAll("\\\\r","").replaceAll("\\\\n","").replaceAll("\\\\t","");
            bean.setGameDescription(introduct);
            //Timber.tag("Sanko").w(introduct);
        }

        p=Pattern.compile(snapshotsRegex);
        m=p.matcher(json);
        i=0;
        while (m.find()){
            snapshots=json.substring(m.start()+7,m.end()-2);
            bean.getBannnerImages().add(snapshots);
            i++;
            //Timber.tag("Sanko").w(snapshots + " "+i);
        }

        p=Pattern.compile(sysReqRegex);
        m=p.matcher(json);
        i=0;
        while (m.find()){
            sysReq=json.substring(m.start()+23,m.end()-1);
            sysReq=sysReq.replaceAll("\",\"","<br />").replaceAll("\"","").replaceAll("\\{","").replaceAll("\\},","<hr />").replaceAll("\\}","");
            bean.setSyaRequest(sysReq);
            i++;
            Timber.tag("Sanko").w(sysReq + " "+i);
        }

        p=Pattern.compile(idRegex);
        m=p.matcher(json);
        i=0;
        while (m.find()){
            id=json.substring(m.start()+14,m.end()-1);
            bean.setId(id);
            i++;
            //Timber.tag("Sanko").w(id + " "+i);
        }

        p=Pattern.compile("\"skus\"[\\s\\S]*?\\}");
        m=p.matcher(json);
        while (m.find()){
            json=json.substring(m.start(),m.end());
        }

        p=Pattern.compile(priceRegex);
        m=p.matcher(json);
        i=0;
        while (m.find()){
            price=json.substring(m.start()+14,m.end()-2);
            bean.setPrice(price);
            i++;
            //Timber.tag("Sanko").w(price + " "+i);
        }

        p=Pattern.compile(imgRegex);
        m=p.matcher(json);
        i=0;
        while (m.find()){
            img=json.substring(m.start()+13,m.end()-2);
            bean.setImg(img);
            i++;
            //Timber.tag("Sanko").w(img + " "+i);
        }

        p=Pattern.compile(titleRegex);
        m=p.matcher(json);
        i=0;
        while (m.find()){
            title=json.substring(m.start()+12,m.end()-2);
            bean.setTitle(title);
            i++;
            //Timber.tag("Sanko").w(title + " "+i);
        }

        return bean;
    }
}
