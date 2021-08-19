package com.white.robot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.robot.entity.SignRecord;
import com.white.robot.mapper.TSignRecordMapper;
import org.springframework.stereotype.Service;

@Service
public class SignService  extends ServiceImpl<TSignRecordMapper, SignRecord> {
    public SignRecord getByQQ(String QQ) {
        LambdaQueryWrapper<SignRecord> wrapper =
                new LambdaQueryWrapper<SignRecord>()
                        .like(SignRecord::getQQ, QQ);
        return getOne(wrapper);
    }
    public boolean refreshDaily() {
        UpdateWrapper<SignRecord> wrapper = new UpdateWrapper<SignRecord>().set("dailySign",false);
        baseMapper.update(null, wrapper);
        return true;
    }


}
