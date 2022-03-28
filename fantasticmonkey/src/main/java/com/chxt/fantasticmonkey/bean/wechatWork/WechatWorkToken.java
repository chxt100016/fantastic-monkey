package com.chxt.fantasticmonkey.bean.wechatWork;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatWorkToken {
    private String errcode;
    private String errmsg;
    private String accessToken;
    private String expiresIn;
}
