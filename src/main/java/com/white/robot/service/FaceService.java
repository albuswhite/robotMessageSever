package com.white.robot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.robot.entity.FaceMessage;
import com.white.robot.mapper.TFaceMessageMapper;
import org.springframework.stereotype.Service;

@Service
public class FaceService extends ServiceImpl<TFaceMessageMapper, FaceMessage> {
    public FaceMessage getByKeyword(String faceId) {
        LambdaQueryWrapper<FaceMessage> wrapper = new LambdaQueryWrapper<FaceMessage>().eq(FaceMessage::getFaceId, faceId);
        return getOne(wrapper);
    }

}
