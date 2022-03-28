package com.chxt.fantasticmonkey.bean.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmParam {

    private Long beginTime;

    private Long endTime;
}
