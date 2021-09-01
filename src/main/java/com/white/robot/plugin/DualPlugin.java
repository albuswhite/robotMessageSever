package com.white.robot.plugin;

import com.white.robot.entity.Believer;
import com.white.robot.entity.Prop;
import com.white.robot.service.BelieverService;
import com.white.robot.service.PropService;
import com.white.robot.service.RiLiLearningService;
import com.white.robot.service.SignService;
import lombok.SneakyThrows;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Random;

@Component
public class DualPlugin extends BotPlugin {


    @Autowired
    private SignService signService;
    @Autowired
    private BelieverService believerService;
    @Autowired
    private PropService propService;

    @Autowired
    BotContainer botContainer;

    private final int fixedAward = 200;

    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        long groupId = event.getGroupId();
        long userId = event.getUserId();
        String QQ = String.valueOf(userId);
        Msg msg = Msg.builder();
        String text = event.getRawMessage();


        if (text.startsWith("快点我们打一架")) {
            List<OnebotBase.Message> messageChain = event.getMessageList();
            OnebotBase.Message people = messageChain.get(1);
            if (people.getType().equals("at")) {
                String qq = people.getDataMap().get("qq");
                if (qq.equals(QQ)) {
                    msg.text("你左脚踩右脚干嘛？");
                    bot.sendGroupMsg(groupId, msg, false);
                    return MESSAGE_BLOCK;
                }
                Believer existBeliever = believerService.getByQQ(QQ);
                if (ObjectUtils.isEmpty(existBeliever)) {
                    msg.text("请先注册，注册教程请输入'教程'查看");
                    bot.sendGroupMsg(groupId, msg, false);
                    return MESSAGE_BLOCK;
                }
                Believer believer = believerService.getByQQ(qq);
                if (ObjectUtils.isEmpty(believer)) {
                    msg.text("找个注册过的人行不行啊");
                    bot.sendGroupMsg(groupId, msg, false);
                    return MESSAGE_BLOCK;
                }
                OnebotBase.Message message = messageChain.get(0);
                text = message.getDataMap().get("text");
                text = text.replace("快点我们打一架", "").replace("倍", "").trim();
                Prop prop = propService.getByQQ(QQ);
                if (prop.getDebris() < 100) {
                    msg.text("碎片不够的一边呆着去");
                    bot.sendGroupMsg(groupId, msg, false);
                    return MESSAGE_BLOCK;
                }


                int multi;
                try {
                    multi = Integer.parseInt(text);
                } catch (Exception e) {
                    msg.text("又有傻篮子想搞我的机器人，这次我直接扣你们100碎片");
                    bot.sendGroupMsg(groupId, msg, false);
                    prop.setDebris(prop.getDebris() - 100);
                    propService.saveOrUpdate(prop);
                    return MESSAGE_BLOCK;

                }

                if (multi <= 0) {
                    msg.text("又有傻篮子想搞我的机器人，这次我直接扣你们100碎片");
                    bot.sendGroupMsg(groupId, msg, false);
                    prop.setDebris(prop.getDebris() - 100);
                    propService.saveOrUpdate(prop);
                    return MESSAGE_BLOCK;
                }

                msg.text("指令正确，开始分数校验\n");
                int limit = multi * 50;
                if (existBeliever.getScore() < limit || believer.getScore() < limit) {
                    msg.text("倍率错误，分数校验失败，双方必须同时拥有倍率x50倍的分数");
                    bot.sendGroupMsg(groupId, msg, false);
                    return MESSAGE_BLOCK;
                }
                Random rd = new Random();
                int one = rd.nextInt(6) + 1;
                int two = rd.nextInt(6) + 1;
                msg.text("验证通过");
                msg.text("\n你的点数是：" + one);
                msg.text("\n对手的点数是：" + two);
                int result = (one - two) * multi;
                if (result == 0) {
                    msg.text("\n这场平局，下一个");
                    bot.sendGroupMsg(groupId, msg, false);
                    return MESSAGE_BLOCK;
                }
                if (result > 0) {
                    msg.text("\n恭喜，你赢了，积分+" + result);

                }
                if (result < 0) {
                    msg.text("\n很遗憾，你输了，积分" + result);
                }
                believer.setScore(believer.getScore() - result);
                existBeliever.setScore(existBeliever.getScore() + result);
                prop.setDebris(prop.getDebris() - 100);
                propService.saveOrUpdate(prop);
                believerService.saveOrUpdate(believer);
                believerService.saveOrUpdate(existBeliever);
                bot.sendGroupMsg(groupId, msg, false);
                return MESSAGE_BLOCK;
            } else {
                return MESSAGE_IGNORE;
            }
        }


        return MESSAGE_IGNORE;
    }


}
