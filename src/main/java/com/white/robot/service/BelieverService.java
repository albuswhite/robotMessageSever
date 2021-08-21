package com.white.robot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.robot.entity.Believer;
import com.white.robot.mapper.TBelieverMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BelieverService extends ServiceImpl<TBelieverMapper, Believer> {


    public Believer getByQQ(String QQ) {
        LambdaQueryWrapper<Believer> wrapper =
                new LambdaQueryWrapper<Believer>().eq(Believer::getQQ, QQ);
        return getOne(wrapper);
    }

    public boolean refreshDaily(String QQ, int fixedTime) {
        UpdateWrapper<Believer> wrapper = new UpdateWrapper<Believer>().set("daily",fixedTime).set("dailyScore",0).eq("QQ",QQ);
        baseMapper.update(null, wrapper);
        return true;
    }

    public List<Believer> getOrderByScoreDesc() {
        LambdaQueryWrapper<Believer> wrapper =
                new LambdaQueryWrapper<Believer>().orderByDesc(Believer::getScore).last("limit 11");
        return list(wrapper);
    }
    public List<Believer> getList() {
        LambdaQueryWrapper<Believer> wrapper =
                new LambdaQueryWrapper<>();
        return list(wrapper);
    }




    public List<Believer> getOrderByScoreAsc() {
        LambdaQueryWrapper<Believer> wrapper =
                new LambdaQueryWrapper<Believer>().orderByAsc(Believer::getScore).last("limit 11");
        return list(wrapper);
    }
    public List<Believer> getOrderByAvgAsc() {
        LambdaQueryWrapper<Believer> wrapper =
                new LambdaQueryWrapper<Believer>().orderByAsc(Believer::getAvg).gt(Believer::getFrequency,10).last("limit 10");
        return list(wrapper);
    }
}
