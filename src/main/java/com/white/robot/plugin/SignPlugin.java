package com.white.robot.plugin;

import com.white.robot.Util.TimeUtil;
import com.white.robot.entity.FeteMessage;
import com.white.robot.entity.RiLiLearning;
import com.white.robot.entity.SignRecord;
import com.white.robot.service.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;



@Slf4j
@Component
public class SignPlugin extends BotPlugin {
    @Autowired
    private FeteService feteService;
    @Autowired
    private SignService signService;

    @Autowired
    private RiLiLearningService riLiLearningService;

    @Autowired
    BotContainer botContainer;

    private final int fixedAward = 200;

    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        Calendar c = Calendar.getInstance();


        long groupId = event.getGroupId();
        long userId = event.getUserId();

        String text = event.getRawMessage();

        FeteMessage feteMessage = feteService.getById(6);
        if (text.equals("赞美日立")) {
            SignRecord signRecord = signService.getByQQ(String.valueOf(userId));
            if (ObjectUtils.isEmpty(signRecord)) {
                bot.sendGroupMsg(groupId, "请先注册，注册教程请输入'教程'查看", false);
                return MESSAGE_BLOCK;
            }


            if (signRecord.isDailySign()) {
                bot.sendGroupMsg(groupId, "求求了别重复签到", false);

            } else {

                signRecord.setTotalSign(signRecord.getTotalSign() + 1);
                signRecord.setUpdateTime(TimeUtil.getNowTimestamp());
                signRecord.setDailySign(true);
                RiLiLearning riLiLearning = riLiLearningService.getByDate(TimeUtil.getLastToday());
                int debris=signRecord.getDebris()+fixedAward+riLiLearning.getSpeakCount();
                signRecord.setDebris(debris);
                Msg msg =Msg.builder().at(userId).text("签到成功,").text("获得碎片:").text(String.valueOf(debris));
                bot.sendGroupMsg(groupId, msg, false);
                signService.saveOrUpdate(signRecord);
            }
            return MESSAGE_BLOCK;


        }


        if (text.equals("煌煌天日渊渟岳立")) {
            if (userId == 1250473565) {
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);
                long sleepTime = (18 - TimeUtil.getHour()) * 60 * 60 - 60 * TimeUtil.getMin() - TimeUtil.getSec();

                bot.sendGroupMsg(groupId, "权限验证通过，祭祀模式启动", false);
                Thread.sleep(1000);
                bot.sendGroupMsg(groupId, String.format("祭祀将在18：00准时开始，当前时间为%s:%s:%s,距离祭祀开始还有%s秒", hour, minute, second, sleepTime), false);
                Thread.sleep(sleepTime * 1000);
                bot.sendGroupMsg(groupId, "祭祀开始", false);
                Thread.sleep(2000);
                bot.sendGroupMsg(groupId, "日立，我的超人你去哪了?", false);
                Thread.sleep(2000);
                bot.sendGroupMsg(groupId, "日立是我们大家的超人(齐声)", false);
                Thread.sleep(2000);
                bot.sendGroupMsg(groupId, "双膝跪下，头顶叩地，舒两掌过额承空，以示头触日立足，恭敬至诚，诵日立圣名：“文刀日立..“，得日立神之庇佑，方可永生。", false);

            } else {
                bot.sendGroupMsg(groupId, "权限不足，请联系大祭司开通权限", false);
            }
            return MESSAGE_BLOCK;
        }

        return MESSAGE_IGNORE;

    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void refresh() {
        signService.refreshDaily();
    }


}
