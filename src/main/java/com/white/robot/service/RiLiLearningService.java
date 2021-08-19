package com.white.robot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.robot.entity.RiLiLearning;
import com.white.robot.mapper.TRiLiLearning;
import org.springframework.stereotype.Service;

@Service
public class RiLiLearningService extends ServiceImpl<TRiLiLearning, RiLiLearning> {
    public RiLiLearning getByDate(String learnDate) {
        LambdaQueryWrapper<RiLiLearning> wrapper =
                new LambdaQueryWrapper<RiLiLearning>()
                        .like(RiLiLearning::getLearnDate, learnDate);
        return getOne(wrapper);
    }
}
