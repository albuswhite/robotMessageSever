package com.white.robot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.robot.entity.ScoreRecord;
import com.white.robot.mapper.TScoreRecordMapper;
import org.springframework.stereotype.Service;

@Service
public class ScoreService extends ServiceImpl<TScoreRecordMapper, ScoreRecord> {
    public ScoreRecord getByQQ(String QQ) {
        LambdaQueryWrapper<ScoreRecord> wrapper =
                new LambdaQueryWrapper<ScoreRecord>()
                        .like(ScoreRecord::getQQ, QQ);
        return getOne(wrapper);
    }
}
