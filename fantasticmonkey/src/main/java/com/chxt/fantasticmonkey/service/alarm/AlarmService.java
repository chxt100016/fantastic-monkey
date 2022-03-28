package com.chxt.fantasticmonkey.service.alarm;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chxt.fantasticmonkey.bean.alarm.Alarm;
import com.chxt.fantasticmonkey.bean.alarm.AlarmParam;
import com.chxt.fantasticmonkey.dao.alarm.AlarmMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AlarmService {
    @Resource
    private AlarmMapper alarmMapper;

    public List<Alarm> getAlarm(AlarmParam param) {
        LambdaQueryWrapper<Alarm> query = new QueryWrapper<Alarm>().lambda()
                .eq(Alarm::getDeviceId, "G69552993")
                .ge(Alarm::getAlarmTime, new Date(param.getBeginTime()))
                .le(Alarm::getAlarmTime, new Date(param.getEndTime()));
        return this.alarmMapper.selectList(query);
    }


}
