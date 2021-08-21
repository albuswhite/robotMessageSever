package com.white.robot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode
@TableName(value = "prop", autoResultMap = true)
@NoArgsConstructor
@Data
public class Prop {
    @TableField(value = "id")
    private int id;
    @TableField(value = "QQ")
    private String QQ;
    @TableField(value = "protectStatus")
    private int protectStatus;
    @TableField(value = "protectDuration")
    private int protectDuration;
    @TableField(value = "buffStatus")
    private int buffStatus;
    @TableField(value = "buffDuration")
    private int buffDuration;
    @TableField(value = "debris")
    private int debris;
    @TableField(value = "luckCard")
    private int luckCard;
    @TableField(value = "doubleCard")
    private int doubleCard;
    @TableField(value = "pietyCard")
    private int pietyCard;
    @TableField(value = "duelCard")
    private int duelCard;
    @TableField(value = "wipeCard")
    private int wipeCard;
}

