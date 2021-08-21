package com.white.robot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.robot.entity.Prop;
import com.white.robot.mapper.TPropMapper;
import org.springframework.stereotype.Service;

@Service
public class PropService extends ServiceImpl<TPropMapper, Prop> {
    public Prop getByQQ(String QQ) {
        LambdaQueryWrapper<Prop> wrapper =
                new LambdaQueryWrapper<Prop>()
                        .like(Prop::getQQ, QQ);
        return getOne(wrapper);
    }
}
