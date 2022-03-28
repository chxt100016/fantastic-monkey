package com.chxt.fantasticmonkey.dependency.ezviz;

import com.alibaba.fastjson.JSON;
import com.chxt.fantasticmonkey.bean.ezviz.CaptureResponse;
import com.chxt.fantasticmonkey.bean.ezviz.EzvizToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class EzvizClientTest {
    @Resource
    private EzvizClient ezvizClient;

    @Test
    public void getToken(){
        EzvizToken token = this.ezvizClient.getToken();
        System.out.println(JSON.toJSONString(token));
    }

    @Test
    public void capture(){
        CaptureResponse f04463389 = this.ezvizClient.capture("F04463389", "at.7alox2n04g96av0l18k10x6w5t5a38rp-9ri9b7u3iv-1kuzmvu-b0ziiq3vf");
        System.out.println(f04463389);
    }

}