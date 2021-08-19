package com.white.robot.plugin;

import com.white.robot.Util.TimeUtil;
import com.white.robot.entity.PreciseMessage;
import com.white.robot.entity.RiLiLearning;
import com.white.robot.service.RiLiLearningService;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class LearnPlugin extends BotPlugin {

    @Autowired
    private RiLiLearningService riLiLearningService;


    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        // 这里展示了RawMessage的用法（纯String）
        long groupId = event.getGroupId();
        String text = event.getRawMessage();
        long userId = event.getUserId();

        RiLiLearning riLiLearning = riLiLearningService.getByDate(TimeUtil.getToday());

        if (userId == 1290827899) {
            riLiLearning.setSpeakCount(riLiLearning.getSpeakCount() + 1);
            riLiLearningService.saveOrUpdate(riLiLearning);
        }

        return MESSAGE_IGNORE;


    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void refresh() {
        RiLiLearning riLiLearning = new RiLiLearning();
        riLiLearning.setLearnDate(TimeUtil.getToday());
        riLiLearning.setLearn(false);
        riLiLearning.setSpeakCount(0);
        riLiLearningService.saveOrUpdate(riLiLearning);
    }
}
