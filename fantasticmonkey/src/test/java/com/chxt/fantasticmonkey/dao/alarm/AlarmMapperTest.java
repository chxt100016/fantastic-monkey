package com.chxt.fantasticmonkey.dao.alarm;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chxt.fantasticmonkey.bean.alarm.Alarm;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlarmMapperTest {

    @Resource
    private AlarmMapper alarmMapper;

    @Test
    public void test() {
        LambdaQueryWrapper<Alarm> query = new QueryWrapper<Alarm>().lambda()
                .ge(Alarm::getAlarmTime, DateFormatUtils.format(new Date(), "yyyy-MM-dd"))
                .le(Alarm::getAlarmTime, DateFormatUtils.format(DateUtils.addDays(new Date(), 1), "yyyy-MM-dd"))
                .eq(Alarm::getDeviceId, "G69552993");
        List<Alarm> alarms = this.alarmMapper.selectList(query);
        System.out.println(JSON.toJSONString(alarms));
    }

}