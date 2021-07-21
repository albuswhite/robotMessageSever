package com.white.robot.plugin;

import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class YellowPlugin extends BotPlugin {
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        long groupId = event.getGroupId();
        String text = event.getRawMessage();
        if (text.equals("提神醒脑")) {
            if (groupId == 966560123) {
                bot.sendGroupMsg(groupId, "检测到当前处于新生群, YellowRobot模式启动失败", false);
            }
            return MESSAGE_BLOCK;
        }
//        for (MessageEnum messageEnum : MessageEnum.values()) {
//            if (messageEnum.getReq().equals(text)) {
//                bot.sendGroupMsg(groupId, messageEnum.getResp(), false);
//                return MESSAGE_BLOCK;
//            }
//        }


        return MESSAGE_IGNORE;
    }
}
