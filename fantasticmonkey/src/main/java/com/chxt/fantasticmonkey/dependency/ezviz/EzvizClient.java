package com.chxt.fantasticmonkey.dependency.ezviz;

import com.alibaba.fastjson.JSON;
import com.chxt.fantasticmonkey.bean.ezviz.CaptureResponse;
import com.chxt.fantasticmonkey.bean.ezviz.EzvizToken;
import com.chxt.fantasticmonkey.bean.http.HttpExecuteDTO;
import com.chxt.fantasticmonkey.dependency.MyHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class EzvizClient extends MyHttpClient {

    @Value("${ezviz.appKey}")
    private String appKey;

    @Value("${ezviz.appSecret}")
    private String appSecret;

    public EzvizToken getToken() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appKey", this.appKey));
        params.add(new BasicNameValuePair("appSecret", this.appSecret));
        String res = this.doPost(
                HttpExecuteDTO.builder()
                        .uri("https://open.ys7.com/api/lapp/token/get")
                        .params(params)
                        .message("萤石开发平台-获取token")
                        .build()
        );
        return JSON.parseObject(res, EzvizToken.class);
    }

    public CaptureResponse capture(String deviceSerial, String accessToken) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("accessToken", accessToken));
        params.add(new BasicNameValuePair("deviceSerial", deviceSerial));
        String res = this.doPost(
                HttpExecuteDTO.builder()
                        .uri("https://open.ys7.com/api/lapp/device/capture")
                        .params(params)
                        .message("萤石开发平台-抓拍")
                        .build()
        );
        return JSON.parseObject(res, CaptureResponse.class);
    }

    public void downloadImg(String uri, String path) {
        this.download(HttpExecuteDTO.builder().uri(uri).path(path).message("下载告警图片").build());
    }
}
