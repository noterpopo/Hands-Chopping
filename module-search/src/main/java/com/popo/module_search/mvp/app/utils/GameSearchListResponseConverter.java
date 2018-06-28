package com.popo.module_search.mvp.app.utils;

import com.popo.module_search.mvp.mvp.model.entity.GameSearchBean;
import com.popo.module_search.mvp.mvp.model.entity.GameSearchList;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.jessyan.armscomponent.commonsdk.utils.BaseResponseConverter;
import timber.log.Timber;

public class GameSearchListResponseConverter extends BaseResponseConverter<GameSearchList> {
    @Override
    public GameSearchList parserHtml(String html) {

        Pattern p=null;
        Matcher m=null;

        GameSearchList bean=null;

        String id=null;
        String imgUrl=null;
        String title=null;
        String price=null;
        String sanko_id=null;

        int i=0;

        Timber.tag("Steam").w("converter");
        if(html.contains("searchbar_left"))
        {
            String idRegex="data-ds-.*=\"[0-9]*\"";
            String imgRegex="<img src=.*>";
            String titleRegex="\"title.*</span>";
            String searchPriceRegex="col search_price [\\s\\S]*?</div>";

            bean=new GameSearchList();
            int startPos=html.indexOf("<!-- End Extra empty div -->");
            int endPos=html.indexOf("<!-- End List Items -->");
            if(startPos==-1||endPos==-1){
                return bean;
            }
            html=html.substring(startPos,endPos);
            //Timber.tag("Steam").w(html);

            p=Pattern.compile(idRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                bean.getGameSearchBeanArrayList().add(new GameSearchBean());
                if(html.substring(m.start(),m.start()+17).contains("bundleid"))
                {
                    id=html.substring(m.start()+18,m.end()-1);
                    bean.getGameSearchBeanArrayList().get(i).setBundle(true);

                }else {
                    id=html.substring(m.start()+15,m.end()-1);
                }
                bean.getGameSearchBeanArrayList().get(i).setId(id);
                bean.getGameSearchBeanArrayList().get(i).setSource("Steam");
                i++;
                //Timber.tag("Steam").w(id+" "+i);
            }

            p=Pattern.compile(imgRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                imgUrl=html.substring(m.start()+10,m.end()-8);
                bean.getGameSearchBeanArrayList().get(i).setImgUrl(imgUrl);
                i++;
                //Timber.tag("Steam").w(imgUrl+" "+i);
            }

            p=Pattern.compile(titleRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                title=html.substring(m.start()+8,m.end()-7);
                bean.getGameSearchBeanArrayList().get(i).setTitle(title);
                i++;
                //Timber.tag("Steam").w(title+" "+i);
            }

            p=Pattern.compile(searchPriceRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                if(html.substring(m.start(),m.start()+29).contains("discounted")){
                    startPos=html.indexOf("¥",m.start());
                    endPos=html.indexOf("</",startPos);
                    price=html.substring(startPos,endPos);
                }else {
                    price=html.substring(m.start()+50,m.end()-13);
                }
                bean.getGameSearchBeanArrayList().get(i).setPrice(price);
                i++;
                //Timber.tag("Steam").w(price+" "+i);
            }



        }else {
            bean=new GameSearchList();
            //Timber.tag("Sanko").w(html);

            String idRegex="\"id\":[0-9]*?,\"sku_name\"";
            String imgRegex="sku_cover\":\".*?\"";
            String titleRegex="sku_name\":\".*?\"";
            String priceRegex="list_price\":\".*?\"";
            String sankoIdRegex="\"product\"[\\s\\S]*?,";

            p=Pattern.compile(idRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                id=html.substring(m.start()+5,m.end()-11);
                bean.getGameSearchBeanArrayList().add(new GameSearchBean());
                bean.getGameSearchBeanArrayList().get(i).setId(id);
                bean.getGameSearchBeanArrayList().get(i).setSource("Sanko");
                i++;
                //Timber.tag("Sanko").w(id+" "+i);
            }

            p=Pattern.compile(imgRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                imgUrl=html.substring(m.start()+12,m.end()-1);
                bean.getGameSearchBeanArrayList().get(i).setImgUrl(imgUrl);
                i++;
                //Timber.tag("Sanko").w(imgUrl+" "+i);
            }

            p=Pattern.compile(titleRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                title=html.substring(m.start()+11,m.end()-1);
                bean.getGameSearchBeanArrayList().get(i).setTitle(title);
                i++;
                //Timber.tag("Sanko").w(title+" "+i);
            }

            p=Pattern.compile(priceRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                price=html.substring(m.start()+13,m.end()-1);
                bean.getGameSearchBeanArrayList().get(i).setPrice("￥ "+price);
                i++;
                //Timber.tag("Sanko").w(price+" "+i);
            }

            p=Pattern.compile(sankoIdRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                sanko_id=html.substring(m.start()+16,m.end()-1);
                bean.getGameSearchBeanArrayList().get(i).setSanko_id(sanko_id);
                i++;
                //Timber.tag("Sanko").w(sanko_id+" "+i);
            }
        }

        return bean;
    }
}