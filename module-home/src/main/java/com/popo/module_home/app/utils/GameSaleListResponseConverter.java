package com.popo.module_home.app.utils;

import com.popo.module_home.mvp.model.entity.GameListBean;
import com.popo.module_home.mvp.model.entity.GameSaleList;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.jessyan.armscomponent.commonsdk.utils.BaseResponseConverter;
import timber.log.Timber;

public class GameSaleListResponseConverter extends BaseResponseConverter<GameSaleList> {
    @Override
    public GameSaleList parserHtml(String html) {

        Pattern p=null;
        Matcher m=null;

        GameSaleList bean=null;

        String id=null;
        String imgUrl=null;
        String title=null;
        String oldPrice=null;
        String nowPrice=null;
        String sanko_id=null;

        int i=0;

        Timber.tag("Steam").w("converter");
        if(html.contains("searchbar_left"))
        {
            String idRegex="data-ds-.*=\"[0-9]*\"";
            String imgRegex="<img src=.*>";
            String titleRegex="\"title.*</span>";
            String priceRegex="<span style=\"color: #888888;\"><strike>.*</strike>";

            bean=new GameSaleList();
            int startPos=html.indexOf("<!-- End Extra empty div -->");
            int endPos=html.indexOf("<!-- End List Items -->");
            html=html.substring(startPos,endPos);
            //Timber.tag("Steam").w(html);

            p=Pattern.compile(idRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                bean.getGameListBeanArrayList().add(new GameListBean());
                if(html.substring(m.start(),m.start()+17).contains("bundleid"))
                {
                    id=html.substring(m.start()+18,m.end()-1);
                    bean.getGameListBeanArrayList().get(i).setBundle(true);

                }else {
                    id=html.substring(m.start()+15,m.end()-1);
                }

                bean.getGameListBeanArrayList().get(i).setId(id);
                bean.getGameListBeanArrayList().get(i).setSource("Steam");
                i++;
                //Timber.tag("Steam").w(id+" "+i);
            }

            p=Pattern.compile(imgRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                imgUrl=html.substring(m.start()+10,m.end()-8);
                bean.getGameListBeanArrayList().get(i).setImgUrl(imgUrl);
                i++;
                //Timber.tag("Steam").w(imgUrl);
            }

            p=Pattern.compile(titleRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                title=html.substring(m.start()+8,m.end()-7);
                bean.getGameListBeanArrayList().get(i).setTitle(title);
                i++;
                //Timber.tag("Steam").w(title+" "+i);
            }

            p=Pattern.compile(priceRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                oldPrice=html.substring(m.start()+38,m.end()-9);
                int tempPos=html.indexOf("</div>", m.end());
                nowPrice=html.substring(m.end()+11,tempPos-7);
                bean.getGameListBeanArrayList().get(i).setOldPrice(oldPrice);
                bean.getGameListBeanArrayList().get(i).setNowPrice(nowPrice);
                Double off=(1-(Double.parseDouble(nowPrice.substring(1))/Double.parseDouble(oldPrice.substring(1))))*100;
                NumberFormat nf = NumberFormat.getInstance();
                String offcount=nf.format(off.intValue());
                bean.getGameListBeanArrayList().get(i).setOff(" - "+offcount+"% ");
                i++;
                //Timber.tag("Steam").w(nowPrice);
            }



        }else {
            String idRegex="\"id\":[0-9]*?,\"sku_name\"";
            String imgRegex="sku_cover\":\".*?\"";
            String titleRegex="sku_name\":\".*?\"";
            String priceRegex="list_price\":\".*?\"";
            String sankoIdRegex="\"product\"[\\s\\S]*?,";

            bean=new GameSaleList();

            p=Pattern.compile(idRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                id=html.substring(m.start()+5,m.end()-11);
                bean.getGameListBeanArrayList().add(new GameListBean());
                bean.getGameListBeanArrayList().get(i).setId(id);
                bean.getGameListBeanArrayList().get(i).setSource("Sanko");
                i++;
                //Timber.tag("Sanko").w(id+" "+i);
            }

            p=Pattern.compile(imgRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                imgUrl=html.substring(m.start()+12,m.end()-1);
                bean.getGameListBeanArrayList().get(i).setImgUrl(imgUrl);
                i++;
                //Timber.tag("Sanko").w(imgUrl+" "+i);
            }

            p=Pattern.compile(titleRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                title=html.substring(m.start()+11,m.end()-1);
                bean.getGameListBeanArrayList().get(i).setTitle(title);
                i++;
                //Timber.tag("Sanko").w(title+" "+i);
            }

            p=Pattern.compile(priceRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                nowPrice=html.substring(m.start()+13,m.end()-1);
                bean.getGameListBeanArrayList().get(i).setNowPrice("￥ "+nowPrice);
                i++;
                //Timber.tag("Sanko").w(nowPrice+" "+i);
            }
            p=Pattern.compile(sankoIdRegex);
            m=p.matcher(html);
            i=0;
            while(m.find()){
                sanko_id=html.substring(m.start()+16,m.end()-1);
                bean.getGameListBeanArrayList().get(i).setSankoId(sanko_id);
                i++;
                Timber.tag("Sanko").w(sanko_id+" "+i);
            }


        }
        return bean;
    }
}
