package com.white.robot.plugin;

import com.white.robot.entity.Believer;
import com.white.robot.entity.FeteMessage;
import com.white.robot.service.BelieverService;
import com.white.robot.service.FeteService;
import lombok.SneakyThrows;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Component
public class FetePlugin extends BotPlugin {

    @Autowired
    private BelieverService believerService;
    @Autowired
    private FeteService feteService;

    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        Random rd = new Random();
        Calendar c1 = Calendar.getInstance();
        int hour = c1.get(Calendar.HOUR_OF_DAY);
        int minute = c1.get(Calendar.MINUTE);
        int second = c1.get(Calendar.SECOND);
        long sleepTime = (18 - hour) * 60 * 60 - 60 * minute - second;
        long groupId = event.getGroupId();
        long userId = event.getUserId();
        int probability = rd.nextInt(10000);
        String text = event.getRawMessage();

        List<OnebotBase.Message> messageChain = event.getMessageList();
        if (messageChain.size() > 0) {
            OnebotBase.Message message = messageChain.get(0);
            if (message.getType().equals("text")) {
                String info = message.getDataMap().get("text");
                if (info.startsWith(" 为日立献礼，我是")) {
                    info = info.replace(" 为日立献礼，我是", "");
                    Believer believer = new Believer();
                    believer.setQQ(String.valueOf(event.getUserId()));
                    believer.setLevel("浅信徒");
                    believer.setName(info);
                    try {
                        believerService.saveOrUpdate(believer);
                    } catch (Exception e) {
                        bot.sendGroupMsg(groupId, "用户名已存在", false);
                        return MESSAGE_BLOCK;
                    }
                    bot.sendGroupMsg(groupId, "恭喜" + info + "，日立神将注视着你前行", false);
                    return MESSAGE_BLOCK;

                }


            }
        }


        if (text.equals("煌煌天日渊渟岳立")) {
            if (userId == 1250473565) {


                bot.sendGroupMsg(groupId, "权限验证通过，祭祀模式启动", false);
                Thread.sleep(1000);
                bot.sendGroupMsg(groupId, String.format("祭祀将在18：00准时开始，当前时间为%s:%s:%s,距离祭祀开始还有%s秒", hour, minute, second, sleepTime), false);
                Thread.sleep(sleepTime * 1000);
                bot.sendGroupMsg(groupId, "祭祀开始", false);
                Thread.sleep(2000);
                bot.sendGroupMsg(groupId, "日立，我的超人你去哪了?", false);
                Thread.sleep(2000);
                bot.sendGroupMsg(groupId, "日立是我们大家的超人(齐声)", false);
                Thread.sleep(2000);
                bot.sendGroupMsg(groupId, "双膝跪下，头顶叩地，舒两掌过额承空，以示头触日立足，恭敬至诚，诵日立圣名：“文刀日立..“，得日立神之庇佑，方可永生。", false);

            } else {
                bot.sendGroupMsg(groupId, "权限不足，请联系大祭司开通权限", false);
            }
            return MESSAGE_BLOCK;
        }

        if (text.equals("日立经")) {
//        if ((hour != 18) && (hour != 12)) {
            if ((hour != 22) ) {
            bot.sendGroupMsg(groupId, "当前非祭祀时间，本月祭祀时间为每日12:00-13:00，18:00-19:00", false);
            return MESSAGE_BLOCK;
        }
            int level = Lottery(probability);
            FeteMessage feteMessage = feteService.getByLevel(level);
            if (!(feteMessage == null)) {
                Msg msg = Msg.builder().at(event.getUserId()).text("恭喜，获得积分"+feteMessage.getScore());

                bot.sendGroupMsg(groupId, feteMessage.getResponse(), false);
                bot.sendGroupMsg(groupId, msg, false);
                return MESSAGE_BLOCK;
            }
        }


        return MESSAGE_IGNORE;
    }

    public int Lottery(int rd) {
        if (rd <= 2) {
            return 1;
        } else if (rd <= 20) {
            return 2;
        } else if (rd <= 200) {
            return 3;
        } else if (rd <= 2000) {
            return 4;
        } else {
            return 5;
        }


    }


}
