package com.white.robot.plugin;

import com.white.robot.Util.TimeUtil;
import com.white.robot.entity.RiLiLearning;
import com.white.robot.service.RiLiLearningService;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LearnPlugin extends BotPlugin {

    @Autowired
    private RiLiLearningService riLiLearningService;
    @Autowired
    BotContainer botContainer;

    private final long botId = 824722914;
    private final long group = 966560123;


    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        // 这里展示了event消息链的用法. List里面可能是 at -> text -> image -> face -> text 形式, 根据元素类型组成 List。
        // List 每一个元素 有type(String)和data(Map<String, String>)，type 表示元素是什么, data 表示元素的具体数据，如at qq，image url，face id

        if (event.getUserId() == 1250473565) {
            List<OnebotBase.Message> messageChain = event.getMessageList();
            if (messageChain.size() > 0) {
                OnebotBase.Message message = messageChain.get(0);
                if (message.getType().equals("text")) {
                    String text = message.getDataMap().get("text");
                    if ("今天学了".equals(text)) {

                        RiLiLearning riLiLearning = riLiLearningService.getByDate(TimeUtil.getToday());
                        riLiLearning.setLearn(true);
                        riLiLearningService.saveOrUpdate(riLiLearning);
                    }
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

    @Scheduled(cron = "0 0 12,16,18,20,22,23 * * ?")
    public void reminder() {
        Map<Long, Bot> allBots = botContainer.getBots();
        Bot bot = allBots.get(botId);
        Msg msg = Msg.builder();
        RiLiLearning riLiLearning = riLiLearningService.getByDate(TimeUtil.getToday());
        if (!riLiLearning.getLearn()) {
            msg.text("日立今天学习了吗，哦，还没有啊？");
            bot.sendGroupMsg(group, msg, false);
        }
    }


}
