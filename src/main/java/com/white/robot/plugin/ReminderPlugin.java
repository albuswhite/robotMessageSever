package com.white.robot.plugin;


import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

@Component
public class ReminderPlugin extends BotPlugin {

    @Autowired
    BotContainer botContainer;

    private final String RL = "1290827899";
    private final String NG = "2573932166";
    private final long botId = 824722914;
    Calendar c = Calendar.getInstance();//可以对每个时间域单独修改


    @Scheduled(cron = "0 0 1 * * ?")
    public void goodEve() {
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        Map<Long, Bot> allBots = botContainer.getBots();

        Bot bot = allBots.get(botId);

        Msg msg = Msg.builder().text("日立该就寝了，日立该睡觉了，日立该安眠了，再不睡头发要掉光了");
        Msg testMsg = Msg.builder().text("奶盖该就寝了，奶盖该睡觉了，奶盖该安眠了，再不睡头发要掉光了");
        if ((month == 9 && date == 2) || (month == 9 && date == 7)) {
            Msg selectMsg = Msg.builder().text("今天要选课了！别忘了！");
            bot.sendPrivateMsg(Long.parseLong(NG), selectMsg, false);
            bot.sendPrivateMsg(Long.parseLong(RL), selectMsg, false);
        }
        if (bot != null) {
            bot.sendPrivateMsg(Long.parseLong(RL), msg, false);
            bot.sendPrivateMsg(Long.parseLong(NG), testMsg, false);
        }
    }

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        String text = event.getRawMessage();
        if (text.startsWith("精致睡眠")) {
            text = text.replace("精致睡眠", "");
            int time = 0;
            System.out.println(text);
            System.out.println(text.split("min")[0]);
            try {
            if (text.contains("d")) {
                int day = Integer.parseInt(text.split("d")[0]) * 3600 * 24;
                time = time + day;
                if (text.contains("h")) {
                    text = text.split("d")[1];
                    int hour = Integer.parseInt(text.split("h")[0]) * 3600;
                    time = time + hour;
                    if (text.contains("min")) {
                        text = text.split("h")[1];
                        int min = Integer.parseInt(text.split("min")[0]) * 60;
                        time = time + min;
                    }
                }
            }
            if (text.contains("h")) {
                int hour = Integer.parseInt(text.split("h")[0]) * 3600;
                time = time + hour;
                if (text.contains("min")) {
                    text = text.split("h")[1];
                    int min = Integer.parseInt(text.split("min")[0]) * 60;
                    time = time + min;

                }


            }
            if (text.contains("min")) {
                int min = Integer.parseInt(text.split("min")[0]) * 60;
                time = time + min;
            }
            if (time != 0) {
                bot.setGroupBan(event.getGroupId(), event.getUserId(), time);
                bot.sendGroupMsg(event.getGroupId(), "日立神赐你安眠", false);
            }
            return MESSAGE_BLOCK;
            } catch (Exception e) {
                Msg msg = Msg.builder().at(event.getUserId())
                        .text("啊喂，谁又想搞我的机器人，是不是你给我出来，赶紧赔钱知不知道,看我直接就是一个禁言");
                bot.sendGroupMsg(event.getGroupId(), msg, false);
                bot.setGroupBan(event.getGroupId(), event.getUserId(), 300);
                return MESSAGE_BLOCK;
            }


        }

        return MESSAGE_IGNORE;
    }
}
