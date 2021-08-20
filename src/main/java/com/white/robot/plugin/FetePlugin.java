package com.white.robot.plugin;

import com.white.robot.Util.TimeUtil;
import com.white.robot.entity.Believer;
import com.white.robot.entity.FeteMessage;
import com.white.robot.entity.RiLiLearning;
import com.white.robot.entity.ScoreRecord;
import com.white.robot.service.BelieverService;
import com.white.robot.service.FeteService;
import com.white.robot.service.RiLiLearningService;
import com.white.robot.service.ScoreService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class FetePlugin extends BotPlugin {

    @Autowired
    private BelieverService believerService;
    @Autowired
    private FeteService feteService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RiLiLearningService riLiLearningService;

    @Autowired
    BotContainer botContainer;




    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {


        long groupId = event.getGroupId();
        long userId = event.getUserId();

        String text = event.getRawMessage();

        // 注册
        if (text.startsWith("为日立献礼，我是")) {
            text = text.replace("为日立献礼，我是", "");
            if (text.length() > 12 || text.contains("\n")) {
                bot.sendGroupMsg(groupId, "违规名字，你知不知道你名字取成这样，你爸爸我都不认识你了", false);
                return MESSAGE_BLOCK;
            }
            Believer believer = new Believer();
            believer.setQQ(String.valueOf(userId));
            believer.setName(text);
            Believer existBeliever = believerService.getByQQ(String.valueOf(userId));
            if (!ObjectUtils.isEmpty(existBeliever)) {
                BeanUtils.copyProperties(existBeliever, believer);
                believer.setName(text);

                if (text.equals(existBeliever.getName())) {
                    bot.sendGroupMsg(groupId, "请勿重复注册", false);
                    return MESSAGE_BLOCK;
                }
            } else {
                believer.setLevel("浅信徒");
                believer.setDaily(3);
                believer.setFixedTime(3);
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


        if (text.equals("日立经")) {
            Believer existBeliever = believerService.getByQQ(String.valueOf(userId));

            if (ObjectUtils.isEmpty(existBeliever)) {
                bot.sendGroupMsg(groupId, "请先注册，注册教程请输入'教程'查看", false);
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
                scoreRecord.setCreateTime(TimeUtil.getNowTimestamp());

                long score = existBeliever.getScore() + feteMessage.getScore();
                int dailyScore = feteMessage.getScore() + existBeliever.getDailyScore();
                int fre = existBeliever.getFrequency() + 1;
                float avg = (float) score / fre;
                String levelTitle = this.LevelJudge(score);

                scoreService.save(scoreRecord);

                existBeliever.setScore(score);
                existBeliever.setFrequency(fre);
                existBeliever.setAvg(avg);
                existBeliever.setDaily(existBeliever.getDaily() - 1);
                existBeliever.setLevel(levelTitle);
                existBeliever.setDailyScore(dailyScore);

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
            if (existBeliever.getDaily() == 0) {

                if (existBeliever.getDailyScore() == 15) {
                    Msg dailyMsg = Msg.builder().at(userId).text("\n" + existBeliever.getName()).text("你今天的积分是" + existBeliever.getDailyScore() + "\n")
                            .text("你真的太可怜了，日立神都看不下去了，触发扶贫机制，次数加二");
                    existBeliever.setDaily(existBeliever.getDaily() + 2);
                    existBeliever.setDailyScore(75);
                    bot.sendGroupMsg(groupId, dailyMsg, false);
                    believerService.saveOrUpdate(existBeliever);
                    return MESSAGE_BLOCK;
                }


                if (existBeliever.getDailyScore() < 75) {
                    Msg dailyMsg = Msg.builder().at(userId).text("\n" + existBeliever.getName()).text("你今天的积分是" + existBeliever.getDailyScore() + "\n")
                            .text("日立神可怜每一个非洲难民，触发扶贫机制，次数加一");
                    existBeliever.setDaily(existBeliever.getDaily() + 1);
                    existBeliever.setDailyScore(75);
                    bot.sendGroupMsg(groupId, dailyMsg, false);
                    believerService.saveOrUpdate(existBeliever);
                    return MESSAGE_BLOCK;
                }

            }

            return MESSAGE_BLOCK;
        }


        if (text.equals("哐次哐次，谁是世界上最虔诚的信徒")) {
            List<Believer> believers = believerService.getOrderByScoreDesc();
            Msg msg = Msg.builder().text("今天虔诚的信徒们又在祷告，那么最虔诚的他们是谁呢\n");
            for (int i = 1; i < believers.size(); i++) {
                msg.text(i + ". " + believers.get(i).getName() + " 积分 " + believers.get(i).getScore() + "\n");
            }
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }

        if (text.equals("哐次哐次，谁是世界上最惨的信徒")) {
            List<Believer> believers = believerService.getOrderByAvgAsc();
            Msg msg = Msg.builder().text("今天很惨的信徒们又很惨\n");
            for (int i = 1; i < believers.size(); i++) {
                msg.text(i + ". ").text(believers.get(i).getName())
                        .text(" 积分： " + believers.get(i).getScore())
                        .text("  次数： " + believers.get(i).getFrequency())
                        .text("\n");
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
        List<String> levelList = Arrays.asList("浅信徒", "善男信女", "普通门徒", "坚定信徒", "狂热教徒", "见习司铎", "传教司铎", "大司铎", "主教", "大主教", "红衣主教", "教宗", "白衣教宗", "神使");
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
        RiLiLearning riLiLearning = riLiLearningService.getByDate(TimeUtil.getLastToday());
        for (Believer believer : believerService.getList())
            if (riLiLearning.getLearn()) {
                believerService.refreshDaily(believer.getQQ(), believer.getFixedTime());
            } else {
                believerService.refreshDaily(believer.getQQ(), believer.getFixedTime()-1);
            }
    }



}
