package com.white.robot.plugin;

import com.white.robot.entity.Believer;
import com.white.robot.entity.FeteMessage;
import com.white.robot.entity.ScoreRecord;
import com.white.robot.service.BelieverService;
import com.white.robot.service.FeteService;
import com.white.robot.service.ScoreService;
import lombok.SneakyThrows;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Component
public class FetePlugin extends BotPlugin {

    @Autowired
    private BelieverService believerService;
    @Autowired
    private FeteService feteService;
    @Autowired
    private ScoreService scoreService;

    @Autowired
    BotContainer botContainer;


    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        Calendar c1 = Calendar.getInstance();
        int hour = c1.get(Calendar.HOUR_OF_DAY);
        int minute = c1.get(Calendar.MINUTE);
        int second = c1.get(Calendar.SECOND);
        long sleepTime = (18 - hour) * 60 * 60 - 60 * minute - second;
        long groupId = event.getGroupId();
        long userId = event.getUserId();

        String text = event.getRawMessage();

        // 注册
        if (text.startsWith("为日立献礼，我是")) {
            text = text.replace("为日立献礼，我是", "");
            Believer believer = new Believer();
            believer.setQQ(String.valueOf(userId));
            believer.setName(text);
            Believer existBeliever = believerService.getByQQ(String.valueOf(userId));
            if (!ObjectUtils.isEmpty(existBeliever)) {
                believer.setId(existBeliever.getId());
                believer.setScore(existBeliever.getScore());
                believer.setDaily(existBeliever.getDaily());
                if (text.equals(existBeliever.getName())) {
                    bot.sendGroupMsg(groupId, "请勿重复注册", false);
                    return MESSAGE_BLOCK;
                }
            } else {
                believer.setLevel("浅信徒");
                believer.setDaily(3);
            }
            try {
                believerService.saveOrUpdate(believer);
            } catch (Exception e) {
                bot.sendGroupMsg(groupId, "用户名已存在", false);
                return MESSAGE_BLOCK;
            }
            bot.sendGroupMsg(groupId, "恭喜" + text + "，日立神将注视着你前行", false);
            return MESSAGE_BLOCK;

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
//            if ((hour != 18) && (hour != 12)) {
//            if ((false)) {
//                bot.sendGroupMsg(groupId, "当前非祭祀时间，本月祭祀时间为每日12:00-13:00，18:00-19:00", false);
//                return MESSAGE_BLOCK;
//            }

            Believer existBeliever = believerService.getByQQ(String.valueOf(userId));

            if (ObjectUtils.isEmpty(existBeliever)) {
                bot.sendGroupMsg(groupId, "请先注册", false);
                return MESSAGE_BLOCK;
            }

            if (existBeliever.getDaily() == 0) {
                bot.sendGroupMsg(groupId, "今天次数用完了，求求别试了", false);
                return MESSAGE_BLOCK;
            }

            int level = Lottery();
            FeteMessage feteMessage = feteService.getByLevel(level);
            if (!(feteMessage == null)) {
                // bot.sendGroupMsg(groupId, feteMessage.getResponse()+"\n", false);

                ScoreRecord scoreRecord = new ScoreRecord();
                scoreRecord.setQQ(String.valueOf(event.getUserId()));
                scoreRecord.setScore(feteMessage.getScore());

                long score=existBeliever.getScore() + feteMessage.getScore();
                String levelTitle=this.LevelJudge(score);

                scoreService.save(scoreRecord);

                existBeliever.setScore(score);
                existBeliever.setDaily(existBeliever.getDaily() - 1);
                existBeliever.setLevel(levelTitle);

                Msg msg = Msg.builder().text(feteMessage.getResponse() + "\n").at(event.getUserId())
                        .text("恭喜，获得积分" + feteMessage.getScore() + "\n").text("今日还剩" + existBeliever.getDaily() + "次");


                believerService.saveOrUpdate(existBeliever);

                bot.sendGroupMsg(groupId, msg, false);
                return MESSAGE_BLOCK;
            }
        }

        if (text.equals("虔诚如我")) {
            Believer existBeliever = believerService.getByQQ(String.valueOf(userId));
            if (ObjectUtils.isEmpty(existBeliever)) {
                bot.sendGroupMsg(groupId, "请先注册，注册教程请输入'教程'查看", false);
                return MESSAGE_BLOCK;
            }
            String title = existBeliever.getTitle() != null ? existBeliever.getTitle() : existBeliever.getLevel();
            Msg msg = Msg.builder().at(userId).text("\n" + existBeliever.getName()).text("你目前的积分是" + existBeliever.getScore() + "\n")
                    .text("你目前的等级是" + title);
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }


        if (text.equals("哐次哐次，谁是世界上最虔诚的信徒")) {
            List<Believer> believers = believerService.getOrderDesc();
            Msg msg = Msg.builder().text("今天虔诚的信徒们又在祷告，那么最虔诚的他们是谁呢\n");
            for (int i = 1; i < believers.size(); i++) {
                msg.text(i + ". " + believers.get(i).getName() + " 积分 " + believers.get(i).getScore() + "\n");
            }
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }

        if (text.equals("哐次哐次，谁是世界上最惨的信徒")) {
            List<Believer> believers = believerService.getOrderAsc();
            Msg msg = Msg.builder().text("今天很惨的信徒们又很惨\n");
            for (int i = 1; i < believers.size(); i++) {
                msg.text(i + ". " + believers.get(i).getName() + " 积分 " + believers.get(i).getScore() + "\n");
            }
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }


        return MESSAGE_IGNORE;
    }

    public int Lottery() {

        Random rd = new Random();
        int probability = rd.nextInt(100);
        if (probability <= 1) {
            return 1;
        } else if (probability <= 10) {
            return 2;
        } else if (probability <= 20) {
            return 3;
        } else if (probability <= 50) {
            return 4;
        } else {
            return 5;
        }


    }


    public String LevelJudge(long score) {
        List<String> levelList = Arrays.asList("浅信徒", "善男信女", "普通门徒", "坚定信徒", "狂热教徒", "见习司铎", "传教司铎", "大司铎","主教", "大主教", "红衣主教", "教宗","白衣教宗", "神使");
        int i = 0;
        if (score < 100) {
            i = 1;
        } else if (score < 200) {
            i = 2;
        } else if (score < 400) {
            i = 3;
        } else if (score < 800) {
            i = 4;
        } else if (score < 1600) {
            i = 5;
        } else if (score < 3200) {
            i = 6;
        } else if (score < 6400) {
            i = 7;
        } else if (score < 10000) {
            i = 8;
        } else if (score < 20000) {
            i = 9;
        } else if (score < 40000) {
            i = 10;
        } else if (score < 80000) {
            i = 11;
        }


        return levelList.get(i);
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void refresh() {
        believerService.refreshDaily();
    }


}
