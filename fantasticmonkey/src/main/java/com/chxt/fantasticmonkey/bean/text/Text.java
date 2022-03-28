package com.chxt.fantasticmonkey.bean.text;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_text")
public class Text{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String prefix;
    private String filename;
    private String data;
}
