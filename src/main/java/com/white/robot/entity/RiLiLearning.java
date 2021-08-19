package com.white.robot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@TableName(value = "RiLiLearning", autoResultMap = true)
@NoArgsConstructor
@Data
public class RiLiLearning {
    @TableField(value = "id")
    private int id;
    @TableField(value = "learn")
    private Boolean learn;
    @TableField(value = "speakCount")
    private int speakCount;
    @TableField(value = "learnDate")
    private String learnDate;
}