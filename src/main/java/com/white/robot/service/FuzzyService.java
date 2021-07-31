package com.white.robot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.robot.mapper.TFuzzyMessageMapper;
import com.white.robot.entity.FuzzyMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuzzyService extends ServiceImpl<TFuzzyMessageMapper, FuzzyMessage> {
    public FuzzyMessage getLikeByKeyword(String keyword) {
        LambdaQueryWrapper<FuzzyMessage> wrapper =
                new LambdaQueryWrapper<FuzzyMessage>()
                        .like(FuzzyMessage::getKeyword, keyword);
        return getOne(wrapper);
    }

    public List<FuzzyMessage> getKeywordList() {
        LambdaQueryWrapper<FuzzyMessage> wrapper =
                new LambdaQueryWrapper<>();
        return list(wrapper);
    }

}
