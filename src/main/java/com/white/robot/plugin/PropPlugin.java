package com.white.robot.plugin;

import com.white.robot.entity.Prop;
import com.white.robot.entity.SignRecord;
import com.white.robot.service.PropService;
import com.white.robot.service.SignService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PropPlugin extends BotPlugin {

    @Autowired
    private PropService propService;
    @Autowired
    private SignService signService;


    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        long groupId = event.getGroupId();
        long userId = event.getUserId();
        String QQ =String.valueOf(userId);

        String text = event.getRawMessage();

        // 注册
        if (text.startsWith("查看背包")) {
            Prop prop=propService.getByQQ(QQ);
            Msg msg = Msg.builder().at(userId).text("你的碎片数是： "+ prop.getDebris()+"\n");
            msg.text("你拥有的道具有:");


            bot.sendGroupMsg(groupId,msg,false);
        }
        return MESSAGE_IGNORE;

    }
}
