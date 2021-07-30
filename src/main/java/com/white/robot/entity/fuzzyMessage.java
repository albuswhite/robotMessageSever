package com.white.robot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode
@TableName(value = "fuzzyMatch", autoResultMap = true)
@NoArgsConstructor
@Data
public class fuzzyMessage {
    @TableField(value = "id")
    private String id;
    @TableField(value = "keyword")
    private String keyword;
    @TableField(value = "response")
    private String response;
}
