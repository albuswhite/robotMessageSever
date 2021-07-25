package com.white.robot.plugin;

import com.white.robot.warehouse.FuzzyEnum;
import com.white.robot.warehouse.QuestionEnum;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionPlugin extends BotPlugin {

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        long groupId = event.getGroupId();

        List<OnebotBase.Message> messageChain = event.getMessageList();
        if (messageChain.size() > 0) {
            OnebotBase.Message message = messageChain.get(0);
            if (message.getType().equals("text")) {
                String text = message.getDataMap().get("text");
                if ("#list".equals(text)) {
                    Msg msg = Msg.builder()
                            .text("请输入#数字标号来自动获取答案\n")
                            .text("1. 国际学院在北邮究竟是什么样的地位？会不会被其他学院歧视？\n")
                            .text("2. 国际学院三个专业分别如何？可以转专业吗？\n")
                            .text("3. 国院未来就业情况如何？深造情况如何？\n")
                            .text("4. 国际学院的课程对电脑有什么要求？我该如何选购电脑？\n")
                            .text("5. 国际学院在哪里上课？和其他学院有什么区别？\n")
                            .text("6. 已经知道在本省的位次，想了解能不能上国际学院或者其中某一个专业\n")
                            .text("7. 国际学院教学质量如何？都是哪里的老师？\n")
                            .text("8. 宿舍环境如何？浴室是开放式的大澡堂吗？\n")
                            .text("9. 国际学院课程是双语教学还是纯英文教学？我能听懂吗？\n")
                            .text("10. 国际学院毕业证有中外合作的字样吗？毕业后证书认可度怎么样？\n")
                            .text("11. 我们有奖学金吗？具体奖项和金额是多少？");


                    bot.sendGroupMsg(groupId, msg, false);
                    return MESSAGE_BLOCK;

                }
                for (QuestionEnum questionEnum : QuestionEnum.values()) {
                    if (text.equals(questionEnum.getReq())) {
                        bot.sendGroupMsg(event.getGroupId(), questionEnum.getResp(), false);

                        return MESSAGE_BLOCK;
                    }
                }
            }
        }

        return MESSAGE_IGNORE;
    }
}
