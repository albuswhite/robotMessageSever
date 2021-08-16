package com.white.robot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@TableName(value = "believer", autoResultMap = true)
@NoArgsConstructor
@Data
public class Believer {
    @TableField(value = "QQ")
    private String QQ;
    @TableField(value = "name")
    private String name;
    @TableField(value = "level")
    private String level;
    @TableField(value = "score")
    private long score;
    @TableField(value = "title")
    private String title;
    @TableField(value = "id")
    private int id;
    @TableField(value = "daily")
    private int daily;
    @TableField(value = "frequency")
    private int frequency;
    @TableField(value = "avg")
    private float avg;
    @TableField(value = "dailyScore")
    private int dailyScore;
}
