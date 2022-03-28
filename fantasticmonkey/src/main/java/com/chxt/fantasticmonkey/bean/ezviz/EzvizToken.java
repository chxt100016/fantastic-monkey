package com.chxt.fantasticmonkey.bean.ezviz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EzvizToken {
    private String msg;
    private String code;
    private EzvizTokenData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EzvizTokenData {
        private String accessToken;
        private Long expireTime;
    }
}
