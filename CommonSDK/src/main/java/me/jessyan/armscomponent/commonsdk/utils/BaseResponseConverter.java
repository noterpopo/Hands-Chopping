package me.jessyan.armscomponent.commonsdk.utils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public abstract class BaseResponseConverter<T> implements Converter<ResponseBody,T>{
    @Override
    public T convert(ResponseBody value) throws IOException {
        return parserHtml(value.string());
    }
    public abstract T parserHtml(String html);
}
