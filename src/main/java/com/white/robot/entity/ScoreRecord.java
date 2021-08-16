package com.white.robot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@EqualsAndHashCode
@TableName(value = "scoreRecord", autoResultMap = true)
@NoArgsConstructor
@Data
public class ScoreRecord {
    @TableField(value = "QQ")
    private String QQ;
    @TableField(value = "score")
    private int score;
    @TableField(value = "createTime")
    private Timestamp createTime;
}
