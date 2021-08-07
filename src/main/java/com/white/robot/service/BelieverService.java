package com.white.robot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.robot.entity.Believer;
import com.white.robot.mapper.TBelieverMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BelieverService extends ServiceImpl<TBelieverMapper, Believer> {


    public List<Believer> getKeywordList() {
        LambdaQueryWrapper<Believer> wrapper =
                new LambdaQueryWrapper<>();
        return list(wrapper);
    }

    public boolean insertBeliever(Believer believer){
        return save( believer);
    }
}
