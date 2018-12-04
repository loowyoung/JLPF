package com.anxin.common.utils.httputils;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * Created by Administrator on 2017/8/17.
 */
public class Okhhtp3Utils {
    /**
     * 发起get请求
     * 设置网络连接、读写超时时间
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        String result = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发起get请求(带header参数,可根据需要修改)
     * @param url
     * @param header ([K,V])格式
     * @return
     */
    public static String httptokenGet(String url,String header[]) {
        String result = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).header(header[0],header[1]).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送httppost请求
     *
     * @param url
     * @param data
     * 常见的post提交数据类型有四种：
    <br>
    1.第一种：application/json：这是最常见的json格式如下
    {"input1":"xxx","input2":"ooo","remember":false}
     <br>
    2.第二种：application/x-www-form-urlencoded：浏览器的原生 form 表单，如果不设置 enctype 属性，那么最终就会以 application/x-www-form-urlencoded 方式提交数
    input1=xxx&input2=ooo&remember=false
    <br>
    3.第三种：multipart/form-data:这一种是表单格式的，数据类型如下
    ------WebKitFormBoundaryrGKCBY7qhFd3TrwAContent-Disposition: form-data; name="text"
    title------WebKitFormBoundaryrGKCBY7qhFd3TrwAContent-Disposition:form-data;name="file";filename="chrome.png"Content-Type: image/pngPNG ... content of chrome.png ...
    ------WebKitFormBoundaryrGKCBY7qhFd3TrwA--
    <br>
    4.第四种：text/xml:这种直接传的xml格式
     * @return
     */
    public static String httpPost(String url,String type, String data) {
        String result = null;
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse(type), data);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = httpClient.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}


