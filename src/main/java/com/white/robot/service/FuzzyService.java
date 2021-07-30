package com.white.robot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.robot.mapper.TFuzzyMessageMapper;
import com.white.robot.entity.fuzzyMessage;
import org.springframework.stereotype.Service;

@Service
public class FuzzyService extends ServiceImpl<TFuzzyMessageMapper, fuzzyMessage> {
    public fuzzyMessage getByKeyword(String keyword) {
        LambdaQueryWrapper<fuzzyMessage> wrapper = new LambdaQueryWrapper<fuzzyMessage>().eq(fuzzyMessage::getKeyword, keyword);
        return getOne(wrapper);
    }


}
