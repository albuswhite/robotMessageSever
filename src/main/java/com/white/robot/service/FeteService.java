package com.white.robot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.robot.entity.FeteMessage;
import com.white.robot.mapper.TFeteMessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeteService extends ServiceImpl<TFeteMessageMapper, FeteMessage> {
    public FeteMessage getByLevel(int level) {
        LambdaQueryWrapper<FeteMessage> wrapper =
                new LambdaQueryWrapper<FeteMessage>()
                        .eq(FeteMessage::getLevel, level);
        return getOne(wrapper);
    }
    public List<FeteMessage> getList() {
        LambdaQueryWrapper<FeteMessage> wrapper =
                new LambdaQueryWrapper<>();
        return list(wrapper);
    }
}
