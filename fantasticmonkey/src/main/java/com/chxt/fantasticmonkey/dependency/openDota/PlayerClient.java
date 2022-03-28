package com.chxt.fantasticmonkey.dependency.openDota;


import com.alibaba.fastjson.JSON;
import com.chxt.fantasticmonkey.bean.http.HttpExecuteDTO;
import com.chxt.fantasticmonkey.bean.openDota.OpenDotaWinLose;
import com.chxt.fantasticmonkey.dependency.MyHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerClient extends MyHttpClient {
    @Value("${open_dota.token}")
    public String token;

    @Value("${open_dota.url}")
    public String url;

    public OpenDotaWinLose getWL(String accountId, String date) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("api_key", token));
        params.add(new BasicNameValuePair("date", date));
        String res = this.doGet(HttpExecuteDTO.builder()
                        .uri(this.url + String.format("/players/%s/wl", accountId))
                        .params(params)
                        .message("open dota WL数据")
                        .build()
        );
        return JSON.parseObject(res, OpenDotaWinLose.class);
    }

}
