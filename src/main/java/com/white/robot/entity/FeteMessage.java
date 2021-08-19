package com.white.robot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@TableName(value = "feteMatch", autoResultMap = true)
@NoArgsConstructor
@Data
public class FeteMessage {
    @TableField(value = "id")
    private int id;
    @TableField(value = "keyword")
    private String keyword;
    @TableField(value = "response")
    private String response;
    @TableField(value = "level")
    private int level;
    @TableField(value = "score")
    private int score;
    @TableField(value = "store")
    private int store;
}
