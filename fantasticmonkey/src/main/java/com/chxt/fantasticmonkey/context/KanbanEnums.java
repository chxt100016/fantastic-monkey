package com.chxt.fantasticmonkey.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class KanbanEnums {


    @Getter
    @AllArgsConstructor
    public enum TypeEnum {
        WORK("work", "工作"),
        LIVE("live", "生活"),
        STUDY("study", "学习");

        private final String code;
        private final String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum MissionStatusEnum {
        TODO("todo", "待办"),
        DOING("doing", "正在进行"),
        DONE("done", "已完成");

        private final String code;
        private final String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum ProjectStatusEnum {
        CREATE("create", "创建"),
        START("start", "进行中"),
        FINISH("finish", "完成");

        private final String code;
        private final String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum PriorityEnum {
        LOW("low", "低"),
        MEDIUM("medium", "中"),
        HIGH("high", "高");

        private final String code;
        private final String desc;
    }
}
