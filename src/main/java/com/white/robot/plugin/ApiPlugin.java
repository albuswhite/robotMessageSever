package com.white.robot.plugin;


import com.white.robot.entity.SignRecord;
import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ApiPlugin extends BotPlugin {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        String text = event.getRawMessage();




        long groupId = event.getGroupId();
        long userId = event.getUserId();

        if (text.equals("舔狗日记")) {

            String url="http://api.iyk0.com/tiangou";

            String result =restTemplate.getForObject(url,String.class);

            bot.sendGroupMsg(groupId, "result", false);
            return MESSAGE_BLOCK;
        }
        if (text.equals("土味情话")) {

            String url="https://api.iyk0.com/twqh";

            String result =restTemplate.getForObject(url,String.class);

            bot.sendGroupMsg(groupId, "result", false);
            return MESSAGE_BLOCK;
        }


        return MESSAGE_IGNORE;
    }
}