package com.chxt.fantasticmonkey.bean.ezviz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmRequest {
    private Header header;
    private Body body;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header {
        // 	消息类型：ys.alarm表示设备告警消息
        private String type;
        private String deviceId;
        private String channelNo;
        private String messageId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body {
        private String devSerial;
        // 设备通道号
        private Integer channel;
        // 设备通道类型: 1-视频通道信息; 2-IO通道
        private Integer channelType;
        // 告警类型, 见 附录:萤石设备告警消息类型
        private String alarmType;
        // 设备自己生成的UUID, 用来标识唯一的告警,统一告警的开始、结束采用统一alarmId
        private String alarmId;
        // 告警关联ID，由发起联动方产生，用来表示联动的关联关系
        private String relationId;
        // 告警状态 1-开始
        private Integer status;
        // 告警位置信息：长度不能超过80字节
        private String location;
        // 告警描述，需要推送给客户的信息
        private String describe;
        // 告警时间，格式： yyyy-MM-ddTHH:mm:ss
        private String alarmTime;
        // 自定义协议类型，命名规则：设备型号_协议标识 如：CS-A1-32W_XX
        private String customType;
        // 图片加密类型：0-不加密，1-用户加密，2-平台加密
        private Integer crypt;
        // 服务端记录的请求时间
        private Long requestTime;
        // 告警图片相关信息
        private List<Picture> pictureList;
        // 告警图片短地址
        private String shortUrl;
        // 平台生成的告警Id
        private String id;
        // 告警图片URL
        private String url;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Picture {
        private String id;
        private String url;
    }
}
