package com.chxt.fantasticmonkey.bean.ezviz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptureResponse {
    private String msg;
    private String code;
    private CaptureResponseData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CaptureResponseData {
        private String picUrl;
    }
}
