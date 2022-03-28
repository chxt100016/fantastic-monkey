package com.chxt.fantasticmonkey.controller.ezviz;

import com.alibaba.fastjson.JSON;
import com.chxt.fantasticmonkey.bean.ezviz.AlarmRequest;
import com.chxt.fantasticmonkey.bean.ezviz.AlarmResponse;
import com.chxt.fantasticmonkey.service.ezviz.CallBackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ezviz/callback")
@Slf4j
public class CallBackController {

    @Resource
    private CallBackService callBackService;

    @RequestMapping("/alarm")
    public AlarmResponse alarm(@RequestBody String text){
        AlarmRequest request = JSON.parseObject(text, AlarmRequest.class);
        new Thread(()-> this.callBackService.alarm(request)).start();
        return AlarmResponse.builder().messageId(request.getHeader().getMessageId()).build();
    }
}
