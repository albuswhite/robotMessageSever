package com.white.robot.plugin;

import com.white.robot.entity.FuzzyMessage;
import com.white.robot.service.FuzzyService;
import com.white.robot.warehouse.FuzzyEnum;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FuzzyPlugin extends BotPlugin {

    @Autowired
    private FuzzyService fuzzyService;

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        // 模糊匹配
        String text = event.getRawMessage();
        List<FuzzyMessage> fuzzyMessageList = fuzzyService.getKeywordList();
        for (FuzzyMessage fuzzyMessage : fuzzyMessageList)
        {
        if (text.contains(fuzzyMessage.getKeyword())) {
            bot.sendGroupMsg(event.getGroupId(), fuzzyMessage.getResponse(), false);
            return MESSAGE_BLOCK;
        }
        }

        return MESSAGE_BLOCK;
    }
}
