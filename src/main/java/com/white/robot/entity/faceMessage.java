package com.white.robot.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@TableName(value = "faceMatch", autoResultMap = true)
@NoArgsConstructor
@Data
public class faceMessage {
    @TableField(value = "id")
    private String id;
    @TableField(value = "faceId")
    private String faceId;
    @TableField(value = "response")
    private String response;


}
