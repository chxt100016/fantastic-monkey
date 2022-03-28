package com.chxt.fantasticmonkey.bean.kanban;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("mission")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private String owner;
    private String priority;
    private String status;
    private Integer projectId;
    private Integer estimatedCost;
    private Integer actualCost;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private Date updateTime;
    private Date deadline;
}
