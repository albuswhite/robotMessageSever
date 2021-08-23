package com.white.robot.plugin;

import com.white.robot.entity.Believer;
import com.white.robot.entity.Prop;
import com.white.robot.entity.SignRecord;
import com.white.robot.service.BelieverService;
import com.white.robot.service.PropService;
import com.white.robot.service.SignService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class PropPlugin extends BotPlugin {

    @Autowired
    private PropService propService;
    @Autowired
    private SignService signService;
    @Autowired
    private BelieverService believerService;


    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        long groupId = event.getGroupId();
        long userId = event.getUserId();
        String QQ = String.valueOf(userId);

        String text = event.getRawMessage();
        Prop prop = propService.getByQQ(QQ);
        Msg msg = Msg.builder().at(userId);
        // 注册
        if (text.equals("查看背包")) {

            msg.text("你的碎片数是： " + prop.getDebris() + "\n").text("你拥有的道具有:");
            if (prop.getLuckCard() > 0) {
                msg.text("运气卡x" + prop.getLuckCard() + " ");
            }
            if (prop.getDuelCard() > 0) {
                msg.text("决斗卡x" + prop.getDuelCard() + " ");
            }
            if (prop.getPietyCard() > 0) {
                msg.text("虔诚卡x" + prop.getPietyCard() + " ");
            }
            if (prop.getPropCard() > 0) {
                msg.text("随机道具卡x" + prop.getPropCard() + " ");
            }
            if (prop.getDoubleCard() > 0) {
                msg.text("翻倍卡x" + prop.getDoubleCard() + " ");
            }
            if (prop.getWipeCard() > 0) {
                msg.text("反制卡x" + prop.getWipeCard() + " ");
            }
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }

        if (text.equals("#随机道具卡")) {
            if (prop.getPropCard() > 0) {
                prop.setPropCard(prop.getPropCard() - 1);
                Random rd = new Random();
                int probability = rd.nextInt(5);
                if (probability == 1) {
                    msg.text("恭喜获得运气卡x1");
                    prop.setLuckCard(prop.getLuckCard() + 1);
                } else if (probability == 2) {
                    msg.text("恭喜获得虔诚卡x1");
                    prop.setPietyCard(prop.getPietyCard() + 1);
                } else if (probability == 3) {
                    msg.text("恭喜获得决斗卡x1");
                    prop.setDuelCard(prop.getDuelCard() + 1);
                } else if (probability == 4) {
                    msg.text("恭喜获得翻倍卡x1");
                    prop.setDoubleCard(prop.getDuelCard() + 1);
                } else {
                    msg.text("恭喜获得反制卡x1");
                    prop.setWipeCard(prop.getWipeCard() + 1);
                }
            } else {
                msg.text("没卡的别试了");

            }
            propService.saveOrUpdate(prop);
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }

        if (text.equals("#运气卡")) {
            if (prop.getLuckCard() > 0) {
                msg.text("使用运气卡，获得次数+3 ");
                Believer believer = believerService.getByQQ(QQ);
                believer.setDaily(believer.getDaily() + 3);
                prop.setLuckCard(prop.getLuckCard() - 1);
                believerService.saveOrUpdate(believer);
                propService.saveOrUpdate(prop);
            } else {
                msg.text("没卡的别试了");
            }
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }

        if (text.equals("#虔诚卡")) {
            if (prop.getPietyCard() > 0) {
                msg.text("使用虔诚卡，获得抽卡次数永久+1 ");
                Believer believer = believerService.getByQQ(QQ);
                believer.setFixedTime(believer.getFixedTime() + 1);
                believer.setDaily(believer.getDaily() + 1);
                prop.setPietyCard(prop.getPietyCard() - 1);
                believerService.saveOrUpdate(believer);
                propService.saveOrUpdate(prop);
            } else {
                msg.text("没卡的别试了");
            }
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }

        if (text.equals("#反制卡")) {
            if (prop.getWipeCard() > 0) {
                msg.text("使用反制卡，获得反制buff 1次 ");
                prop.setWipeCard(prop.getWipeCard() - 1);
                prop.setProtectDuration(prop.getProtectDuration() + 1);
                propService.saveOrUpdate(prop);
            } else {
                msg.text("没卡的别试了");
            }
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }

        if (text.equals("#翻倍卡")) {
            if (prop.getDoubleCard() > 0) {
                Believer believer = believerService.getByQQ(QQ);
                if (believer.getDaily() < believer.getFixedTime()) {
                    msg.text("请在今日抽卡前提前使用翻倍卡");
                } else {
                    msg.text("使用翻倍卡，获得翻倍buff +100% ");
                    prop.setDoubleCard(prop.getDoubleCard() - 1);
                    prop.setBuffDuration(prop.getBuffDuration() + 1);
                    propService.saveOrUpdate(prop);
                }
            } else {
                msg.text("没卡的别试了");
            }
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }

        if (text.startsWith("#决斗卡")) {
            if (prop.getDuelCard() > 0) {
                List<OnebotBase.Message> messageChain = event.getMessageList();
                OnebotBase.Message message = messageChain.get(1);
                if (message.getType().equals("at")) {
                    String qq = message.getDataMap().get("qq");


                }


                msg.text("使用决斗卡，触发决斗 ");
                prop.setDuelCard(prop.getDuelCard() - 1);
                propService.saveOrUpdate(prop);
            } else {
                msg.text("决斗卡暂时屏蔽等前面的debug完成");
                //msg.text("没卡的别试了");
            }
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }


        return MESSAGE_IGNORE;

    }

}
