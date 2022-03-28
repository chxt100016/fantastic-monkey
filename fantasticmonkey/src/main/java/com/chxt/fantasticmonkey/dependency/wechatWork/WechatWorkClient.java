package com.chxt.fantasticmonkey.dependency.wechatWork;


import com.alibaba.fastjson.JSON;
import com.chxt.fantasticmonkey.bean.http.HttpExecuteDTO;
import com.chxt.fantasticmonkey.bean.wechatWork.SendMessageRequest;
import com.chxt.fantasticmonkey.bean.wechatWork.WechatWorkToken;
import com.chxt.fantasticmonkey.dependency.MyHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class WechatWorkClient extends MyHttpClient {

    @Value("${wechat_work.corpid}")
    private String corpId;

    @Value("${wechat_work.corpsecret}")
    private String corpSecret;

    public WechatWorkToken getToken(){
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("corpid", this.corpId));
        params.add(new BasicNameValuePair("corpsecret", this.corpSecret));
        String str = this.doGet(
                HttpExecuteDTO.builder()
                        .uri("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                        .params(params)
                        .message("企业微信-获取token")
                        .build()
        );
        return JSON.parseObject(str, WechatWorkToken.class);
    }

    public void sendMessage(SendMessageRequest entity, String token){
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", token));
        this.doPost(
                HttpExecuteDTO.builder()
                        .uri("https://qyapi.weixin.qq.com/cgi-bin/message/send")
                        .params(params)
                        .entity(entity)
                        .message("企业微信-发送应用消息")
                        .build()
        );
    }
}
