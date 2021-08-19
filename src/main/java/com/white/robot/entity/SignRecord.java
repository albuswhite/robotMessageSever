package com.white.robot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@EqualsAndHashCode
@TableName(value = "signRecord", autoResultMap = true)
@NoArgsConstructor
@Data
public class SignRecord {
    @TableField(value = "QQ")
    private String QQ;
    @TableField(value = "dailySign")
    private boolean dailySign;

    @TableField(value = "debris")
    private int debris;
    @TableField(value = "totalSign")
    private int totalSign;
    @TableField(value = "id")
    private int id;
    @TableField(value = "updateTime")
    private Timestamp updateTime;
}
