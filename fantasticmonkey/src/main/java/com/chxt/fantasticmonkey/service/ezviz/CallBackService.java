package com.chxt.fantasticmonkey.service.ezviz;

import com.alibaba.fastjson.JSON;
import com.chxt.fantasticmonkey.bean.alarm.Alarm;
import com.chxt.fantasticmonkey.bean.ezviz.AlarmRequest;
import com.chxt.fantasticmonkey.bean.ezviz.CaptureResponse;
import com.chxt.fantasticmonkey.bean.wechatWork.EzvizProperty;
import com.chxt.fantasticmonkey.bean.wechatWork.SendMessageRequest;
import com.chxt.fantasticmonkey.dao.alarm.AlarmMapper;
import com.chxt.fantasticmonkey.dependency.ezviz.EzvizClient;
import com.chxt.fantasticmonkey.dependency.wechatWork.WechatWorkClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CallBackService {

    @Resource
    private EzvizClient ezvizClient;

    @Resource
    private WechatWorkClient wechatWorkClient;

    @Resource
    private EzvizProperty ezvizProperty;

    @Resource
    private AlarmMapper alarmMapper;

    @Value("${wechat_work.alarm.agentid}")
    private Long agentId;

    @Value("${ezviz.alarm_img_path}")
    private String imgPath;


    @SneakyThrows
    public void alarm(AlarmRequest request) {
        log.info("收到萤石告警消息, {}", JSON.toJSONString(request));

        // 保存告警信息
        this.save(request);

        if (!ezvizProperty.getInclude().contains(request.getHeader().getDeviceId())) {
            log.info("收到萤石告警消息-不处理, request: {}", JSON.toJSONString(request));
            return;
        }

        List<String> pictureList = request.getBody().getPictureList().stream().map(AlarmRequest.Picture::getUrl).collect(Collectors.toList());

        // 萤石token
        String ezvizToken = this.ezvizClient.getToken().getData().getAccessToken();
        // 抓照片
        for (int i = 0; i < ezvizProperty.getTimes(); i++) {
            CaptureResponse captureRes = this.ezvizClient.capture(request.getBody().getDevSerial(), ezvizToken);
            pictureList.add(captureRes.getData().getPicUrl());
        }

        // 企业微信token
        String wechatWorkToken = this.wechatWorkClient.getToken().getAccessToken();

        // 添加推送应用消息
        Date now = new Date();
        List<Map<String, String>> articles = new ArrayList<>();
        for (int i = 0; i < pictureList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            String date = DateFormatUtils.format(now, "MM月dd日 HH时mm分");
            if (i == 0) {
                Date addMinutes = DateUtils.addMinutes(now, 1);
                String format = "yyyyMMddHHmmss";
                String ezopen = String.format("ezopen://open.ys7.com/G69552993/1.rec?begin=%s&end=%s", DateFormatUtils.format(now, format), DateFormatUtils.format(addMinutes, format));
                String url = String.format("https://open.ys7.com/ezopen/h5/iframe?url=%s&accessToken=%s&audio=1", URLEncoder.encode(ezopen, "utf-8"), ezvizToken);

                map.put("title", date + "点击查看回放.");
                map.put("url", url);
            }
            else {
                map.put("title", "抓拍" + i);
                map.put("url", pictureList.get(i));
            }
            map.put("picurl", pictureList.get(i));

            articles.add(map);
        }

        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .touser("@all")
                .msgtype("news")
                .agentid(this.agentId)
                .news(SendMessageRequest.SendMessageNews.builder().articles(articles).build())
                .build();
        this.wechatWorkClient.sendMessage(sendMessageRequest, wechatWorkToken);
    }

    private void save(AlarmRequest request){
        AlarmRequest.Body body = request.getBody();
        String url = body.getPictureList().get(0).getUrl();
        String path = this.imgPath + "/" + body.getDevSerial() + "/" + body.getAlarmTime();
        Date alarmTime = null;
        try {
            alarmTime = DateUtils.parseDate(body.getAlarmTime(), "yyyy-MM-ddTHH:mm:ss");
        } catch (Exception e) {
            alarmTime = new Date();
            e.printStackTrace();
        }
        this.ezvizClient.downloadImg(url, path);
        this.alarmMapper.insert(Alarm.builder().outId(body.getAlarmId()).imgPath(path).deviceId(body.getDevSerial()).alarmTime(alarmTime).build());
    }

}
