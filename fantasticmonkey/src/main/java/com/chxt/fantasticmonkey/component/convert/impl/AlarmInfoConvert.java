package com.chxt.fantasticmonkey.component.convert.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chxt.fantasticmonkey.bean.alarm.Alarm;
import com.chxt.fantasticmonkey.component.convert.KeywordConvert;
import com.chxt.fantasticmonkey.dao.alarm.AlarmMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

@Component
public class AlarmInfoConvert extends KeywordConvert {

    @Resource
    private AlarmMapper alarmMapper;

    @Override
    protected String getPattern() {
        return "\\{\\{(alarm.today):(.*)}}";
    }

    @Override
    protected String getParam(Matcher matcher) {
        return matcher.group(2);
    }

    @Override
    public String getDest(String param) {
        LambdaQueryWrapper<Alarm> query = new QueryWrapper<Alarm>().lambda()
                .ge(Alarm::getAlarmTime, DateFormatUtils.format(new Date(), "yyyy-MM-dd"))
                .le(Alarm::getAlarmTime, DateFormatUtils.format(DateUtils.addDays(new Date(), 1), "yyyy-MM-dd"))
                .eq(Alarm::getDeviceId, param);
        List<Alarm> alarms = this.alarmMapper.selectList(query);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < alarms.size(); i++) {
            Alarm alarm = alarms.get(i);
            String dateTime = DateFormatUtils.format(alarm.getAlarmTime(), "yyyy-MM-dd ***HH:mm:ss***");
            String format = "%s. [%s](http://2202.com/file/img?path=%s) \r\n";
            String info = String.format(format, i+1, dateTime, alarm.getImgPath());
            sb.append(info);
        }
        return sb.toString();
    }
}
