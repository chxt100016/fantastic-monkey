package com.chxt.fantasticmonkey.controller.alarm;

import com.chxt.fantasticmonkey.bean.alarm.Alarm;
import com.chxt.fantasticmonkey.bean.alarm.AlarmParam;
import com.chxt.fantasticmonkey.service.alarm.AlarmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

    @Resource
    private AlarmService alarmService;

    @GetMapping("")
    public List<Alarm> getAlarm(AlarmParam param) {
        return this.alarmService.getAlarm(param);
    }
}
