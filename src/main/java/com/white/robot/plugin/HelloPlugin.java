package com.white.robot.plugin;

import com.white.robot.warehouse.MessageEnum;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HelloPlugin extends BotPlugin {
    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        // 这里展示了event消息链的用法. List里面可能是 at -> text -> image -> face -> text 形式, 根据元素类型组成 List。
        // List 每一个元素 有type(String)和data(Map<String, String>)，type 表示元素是什么, data 表示元素的具体数据，如at qq，image url，face id
        List<OnebotBase.Message> messageChain = event.getMessageList();
        if (messageChain.size() > 0) {
            OnebotBase.Message message = messageChain.get(0);
            if (message.getType().equals("text")) {
                String text = message.getDataMap().get("text");
                if ("hello".equals(text)) {
                    bot.sendPrivateMsg(event.getUserId(), "hi", false);

                }
            }
        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        // 这里展示了RawMessage的用法（纯String）
        long groupId = event.getGroupId();
        String text = event.getRawMessage();
        for (MessageEnum messageEnum : MessageEnum.values()) {
            if (messageEnum.getReq().equals(text)) {
                bot.sendGroupMsg(groupId, messageEnum.getResp(), false);
                return MESSAGE_BLOCK;
            }
        }


        return MESSAGE_IGNORE;
    }

    @Override
    public int onGroupIncreaseNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupIncreaseNoticeEvent event) {
        long groupId = event.getGroupId();
        Msg msg = Msg.builder().at(event.getUserId()).text("欢迎新生来到北邮国院，请仔细阅读‼️群公告‼️和群文件并修改群名片，有问题在群里问。");
        bot.sendGroupMsg(groupId, msg, false);
        return MESSAGE_BLOCK;
    }

    @Override
    public int onGroupRecallNotice(@NotNull Bot bot, @NotNull OnebotEvent.GroupRecallNoticeEvent event) {
        Msg msg= Msg.builder().text("怀孕了就直说啊,撤回干什么?大家一起帮你想办法嘛");
        bot.sendGroupMsg(event.getGroupId(),msg,false);
        return MESSAGE_BLOCK;


    }
}
