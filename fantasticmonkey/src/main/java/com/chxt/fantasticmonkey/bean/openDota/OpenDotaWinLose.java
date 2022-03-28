package com.chxt.fantasticmonkey.bean.openDota;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenDotaWinLose {
    private Integer win;
    private Integer lose;
}
