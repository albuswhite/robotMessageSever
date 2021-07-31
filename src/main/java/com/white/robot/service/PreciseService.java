package com.white.robot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.robot.entity.PreciseMessage;
import com.white.robot.mapper.TPreciseMessageMapper;
import org.springframework.stereotype.Service;

@Service
public class PreciseService extends ServiceImpl<TPreciseMessageMapper, PreciseMessage> {
    public PreciseMessage getByKeyword(String keyword) {
        LambdaQueryWrapper<PreciseMessage> wrapper =
                new LambdaQueryWrapper<PreciseMessage>()
                        .eq(PreciseMessage::getKeyword, keyword);
        return getOne(wrapper);
    }

}
