package com.chxt.fantasticmonkey.bean.wechatWork;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest{
    private String touser;
    private String msgtype;
    private Long agentid;
    private SendMessageNews news;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendMessageNews {
        List<Map<String, String>> articles;
    }
}
