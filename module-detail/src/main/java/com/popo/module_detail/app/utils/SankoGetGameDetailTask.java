package com.popo.module_detail.app.utils;

import android.os.AsyncTask;

import com.popo.module_detail.mvp.model.entity.GameDetailBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import timber.log.Timber;

public class SankoGetGameDetailTask extends AsyncTask<String,Void,String> {

    GameDetaiListener listener;

    public SankoGetGameDetailTask(GameDetaiListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String[] parms=strings[0].split("&");
        String product_id=parms[0];
        String id=parms[1];
        StringBuilder builder = new StringBuilder();
        try {
            URL url=new URL("https://www.sonkwo.com/api/products/"+product_id+".json?locale=js&sonkwo_version=1&sonkwo_client=web&_="+String.valueOf(System.currentTimeMillis()));
            HttpsURLConnection httpsURLConnection=(HttpsURLConnection)url.openConnection();

            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("Accept","application/vnd.sonkwo.v5+json");
            httpsURLConnection.setRequestProperty("Accept-Encoding","deflate, br");
            httpsURLConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9,zh-TW;q=0.8");
            httpsURLConnection.setRequestProperty("Connection","keep-alive");
            httpsURLConnection.setRequestProperty("Host","www.sonkwo.com");
            httpsURLConnection.setRequestProperty("Referer","https://www.sonkwo.com/products/"+product_id+"?game_id="+id);
            httpsURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36");
            httpsURLConnection.setRequestProperty("X-Requested-With","XMLHttpRequest");
            //httpsURLConnection.setRequestProperty("Cookie","_sonkwo_community_session=eCKQrQ4i8%2BqTX1PhnNSXTxNN1sIjkSe%2BS3agPmJbTJHS9mY3h3gite8fCRlVVP%2F6a%2Fj8g0oYYQ8YdIggPihUqIJ5fH2%2Fdoi5yXfwhXit1r5V6dr4Jxso3OX30iMN7s3ma1PHmKpHR5howHL9lk4mWi9H2ncfEBXde6lcvHPJ2H0C6w%3D%3D--N%2BsFOFFVlHE%2FDVUp--rsjifub42VFgFk5fC2hoWg%3D%3D; Hm_lvt_4abede90a75b2ba39a03e7a40fcec65f="+timestamp+"; Hm_lpvt_4abede90a75b2ba39a03e7a40fcec65f="+timestamp);

            httpsURLConnection.connect();
            if(httpsURLConnection.getResponseCode()==200){
                InputStream inputStream = null;

                if (null == inputStream) {
                    inputStream = httpsURLConnection.getInputStream();
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onGetted(s);
    }
}
