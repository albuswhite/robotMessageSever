package com.white.robot.plugin;

import com.white.robot.warehouse.FuzzyEnum;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class FuzzyPlugin extends BotPlugin {

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        // 模糊匹配
        String text = event.getRawMessage();
        for (FuzzyEnum fuzzyEnum : FuzzyEnum.values()) {
            if (text.contains(fuzzyEnum.getReq())) {
                bot.sendGroupMsg(event.getGroupId(), fuzzyEnum.getResp(), false);
                return MESSAGE_BLOCK;
            }
        }


        return MESSAGE_BLOCK;
    }
}
