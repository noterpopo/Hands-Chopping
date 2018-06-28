package com.popo.module_detail.app.utils;

import android.util.Log;

import com.popo.module_detail.mvp.model.entity.GameDetailBean;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.jessyan.armscomponent.commonsdk.utils.BaseResponseConverter;
import me.jessyan.armscomponent.commonsdk.utils.HtmlUtil;
import me.jessyan.armscomponent.commonsdk.utils.Utils;
import timber.log.Timber;

public class GameDetailResponeConverter extends BaseResponseConverter<GameDetailBean> {
    @Override
    public GameDetailBean parserHtml(String html) {
        Pattern p=null;
        Matcher m=null;
        String gameHLImgRegex="highlight_strip_item highlight_strip_screenshot[\\s\\S]*?</div>";
        String gameHeaderImgRegex="game_header_image_full[\\s\\S]*?\">";
        String gameDescriptionRegex="game_area_description[\\s\\S]*?</div>";
        String gameDescriptionSnippetRegex="game_description_snippet[\\s\\S]*?</div>";
        String gameSysReqRegex="sysreq_contents[\\s\\S]*?text/javascript";

        GameDetailBean bean=null;

        String gameHLImg=null;
        String gameHeaderImg=null;
        String gameDescription=null;
        String gameDescriptionSnippet=null;
        String gameSysReq=null;

        int i=0;

        if(html.contains("highlight_strip_scroll")){
            bean=new GameDetailBean();

            p= Pattern.compile(gameHLImgRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                gameHLImg=html.substring(m.start()+143,m.end()-21);
                gameHLImg=gameHLImg.replace("116x65.","");
                i++;
                bean.getBannnerImages().add(gameHLImg);
                Timber.tag("Steam").w(gameHLImg+" "+i);
            }


            p=Pattern.compile(gameHeaderImgRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                gameHeaderImg=html.substring(m.start()+29,m.end()-2);
                i++;
                bean.setImg(gameHeaderImg);
                Timber.tag("Steam").w(gameHeaderImg);
            }

            p=Pattern.compile(gameDescriptionRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                gameDescription=html.substring(m.start()+58,m.end()-12);
                i++;
                bean.setGameInfo(gameDescription);
                Timber.tag("Steam").w(gameDescription);
            }

            p=Pattern.compile(gameDescriptionSnippetRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                gameDescriptionSnippet=html.substring(m.start()+36,m.end()-13);
                i++;
                bean.setGameDescription(gameDescriptionSnippet);
                Timber.tag("Steam").w(gameDescriptionSnippet);
            }

            p=Pattern.compile(gameSysReqRegex);
            m=p.matcher(html);

            i=0;
            while (m.find()){
                gameSysReq=html.substring(m.start()+26,m.end()-30);
                i++;
                bean.setSyaRequest(gameSysReq);
                Timber.tag("Steam").w(gameSysReq);
            }
        }else if(html.contains("recommendationids")){
            bean=new GameDetailBean();
            //html=HtmlUtil.unicode2String(html);
            //Timber.tag("Steam").w(html);
            String reviewRegex="<div class=\\\\\"content\\\\\">[\\s\\S]*?<div class=\\\\\"gradient\\\\\"><\\\\/div>";
            p=Pattern.compile(reviewRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                String review=html.substring(m.start(),m.end());
                review=HtmlUtil.Unicode2String(review);
                review=review.replaceAll("\\\\t","\t").replaceAll("\\\\r","\r").replaceAll("\\\\n","\n").replaceAll("\\\\/","/").replaceAll("\\\\","\"");
                i++;
                bean.getReviews().add(review);
                Timber.tag("Steam").w(review+" "+i);
            }
        }else {
            bean=new GameDetailBean();
            String bundleTitileRegex="class=\"pageheader\"[\\s\\S]*?</h2>";
            String imgRegex="class=\"package_header\"[\\s\\S]*?alt";
            String desRegex="id=\"game_area_description\"[\\s\\S]*?</div>";
            String bundlesRegex="<!-- Tab Section -->[\\s\\S]*?<!-- End Pricing breakdown -->";

            String title=null;
            String img=null;
            String des=null;
            String bundles=null;

            p=Pattern.compile(bundleTitileRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                title=html.substring(m.start()+19,m.end()-5);
                bean.setTitle(title);
                i++;

                Timber.tag("Steam").w(title+" "+i);
            }

            p=Pattern.compile(imgRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                img=html.substring(m.start()+28,m.end()-5);
                bean.setImg(img);
                i++;

                Timber.tag("Steam").w(img+" "+i);
            }

            p=Pattern.compile(desRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                des=html.substring(m.start()+76,m.end());
                bean.setGameInfo(des);
                i++;

                Timber.tag("Steam").w(des+" "+i);
            }

            p=Pattern.compile(bundlesRegex);
            m=p.matcher(html);
            i=0;
            while (m.find()){
                bundles=html.substring(m.start()+76,m.end());
                bean.setSyaRequest(bundles);
                i++;

                Timber.tag("Steam").w(bundles+" "+i);
            }


            //Timber.tag("Steam").w(html);
        }
        return bean;
    }
}
