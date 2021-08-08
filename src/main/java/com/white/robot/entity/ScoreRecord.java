package com.white.robot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@TableName(value = "scoreRecord", autoResultMap = true)
@NoArgsConstructor
@Data
public class ScoreRecord {
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
}
