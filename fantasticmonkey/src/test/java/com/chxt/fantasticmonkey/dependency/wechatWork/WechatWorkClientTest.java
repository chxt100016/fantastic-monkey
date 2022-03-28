package com.chxt.fantasticmonkey.dependency.wechatWork;

import com.alibaba.fastjson.JSON;
import com.chxt.fantasticmonkey.bean.wechatWork.WechatWorkToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class WechatWorkClientTest {

    @Resource
    private WechatWorkClient wechatWorkClient;

    @Test
    public void getToken() {
        WechatWorkToken token = this.wechatWorkClient.getToken();
        System.out.println(JSON.toJSONString(token));
    }
}