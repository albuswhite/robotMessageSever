package com.white.robot.plugin;

import lombok.SneakyThrows;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class FetePlugin extends BotPlugin {
    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        long groupId = event.getGroupId();
        long userId = event.getUserId();
        String text = event.getRawMessage();
        if (text.equals("煌煌天日渊渟岳立")) {
            if (userId == 1250473565) {
                Calendar c1 = Calendar.getInstance();
                int hour = c1.get(Calendar.HOUR_OF_DAY);
                int minute = c1.get(Calendar.MINUTE);
                int second = c1.get(Calendar.SECOND);
                long sleepTime = (18 - hour) * 60 * 60 - 60 * minute - second;
                bot.sendGroupMsg(groupId, "权限验证通过，祭祀模式启动", false);
                Thread.sleep(1000);
                bot.sendGroupMsg(groupId, String.format("祭祀将在18：00准时开始，当前时间为%s:%s:%s,距离祭祀开始还有%s秒", hour, minute, second, sleepTime), false);
                Thread.sleep(sleepTime * 1000);
                bot.sendGroupMsg(groupId, "祭祀开始", false);
                Thread.sleep(1000);
                bot.sendGroupMsg(groupId, "日立，我的超人你去哪了?", false);
                Thread.sleep(1000);
                bot.sendGroupMsg(groupId, "日立是我们大家的超人(齐声)", false);
                Thread.sleep(1000);
                bot.sendGroupMsg(groupId, "双膝跪下，头顶叩地，舒两掌过额承空，以示头触日立足，恭敬至诚，诵日立圣名：“文刀日立..“，得日立神之庇佑，方可永生。", false);
                Thread.sleep(1000);
                bot.sendGroupMsg(groupId, "请大家有序拿号祷告，不要刷屏，防止吓跑信任", false);

            } else {
                bot.sendGroupMsg(groupId, "权限不足，请联系大祭司开通权限", false);
            }
            return MESSAGE_BLOCK;
        }


        return MESSAGE_IGNORE;
    }

}
