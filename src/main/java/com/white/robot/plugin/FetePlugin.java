package com.white.robot.plugin;

import com.white.robot.Util.InitialUtil;
import com.white.robot.Util.TimeUtil;
import com.white.robot.entity.*;
import com.white.robot.service.*;
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
    private RiLiLearningService riLiLearningService;
    @Autowired
    private SignService signService;
    @Autowired
    private PropService propService;

    @Autowired
    BotContainer botContainer;





    @SneakyThrows
    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        long groupId = event.getGroupId();
        long userId = event.getUserId();
        String QQ = String.valueOf(userId);

        String text = event.getRawMessage();
        Msg msg = Msg.builder();

        // 注册
        if (text.startsWith("为日立献礼，我是")) {
            text = text.replace("为日立献礼，我是", "");
            if (text.length() > 12 || text.contains("\n")) {
                bot.sendGroupMsg(groupId, "违规名字，你知不知道你名字取成这样，你爸爸我都不认识你了", false);
                return MESSAGE_BLOCK;
            }
            Believer believer = new Believer();
            Believer existBeliever = believerService.getByQQ(QQ);
            if (!ObjectUtils.isEmpty(existBeliever)) {
                BeanUtils.copyProperties(existBeliever, believer);
                believer.setName(text);

                if (text.equals(existBeliever.getName())) {
                    bot.sendGroupMsg(groupId, "请勿重复注册", false);
                    return MESSAGE_BLOCK;
                }
            } else {
                believer.setQQ(String.valueOf(userId));
                believer.setName(text);
                believer.setLevel("浅信徒");
                believer.setDaily(5);
                believer.setFixedTime(5);

                SignRecord signRecord = InitialUtil.newSignRecord(QQ);
                signService.save(signRecord);
                Prop prop = InitialUtil.newProp(QQ);
                propService.save(prop);

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


        if (text.startsWith("日立经")) {
            Believer existBeliever = believerService.getByQQ(String.valueOf(userId));

            if (ObjectUtils.isEmpty(existBeliever)) {

                bot.sendGroupMsg(groupId, "请先注册，注册教程请输入'教程'查看", false);
                return MESSAGE_BLOCK;
            }

            if (existBeliever.getDaily() == 0) {
                bot.sendGroupMsg(groupId, "今天次数用完了，求求别试了", false);
                return MESSAGE_BLOCK;
            }


            text = text.replace("日立经", "").trim();
            int multi = 1;
            if (text.equals("加倍")) {

                Prop prop = propService.getByQQ(QQ);
                if (prop.getDebris() < 800) {
                    msg.text("碎片不够的一边呆着去");
                    bot.sendGroupMsg(groupId, msg, false);
                    return MESSAGE_BLOCK;
                }
                msg.text("加倍成功\n");
                prop.setDebris(prop.getDebris() - 800);
                propService.saveOrUpdate(prop);
                multi = 2;


            } else if (text.equals("超级加倍")) {
                Prop prop = propService.getByQQ(QQ);
                if (prop.getDebris() < 2000) {
                    msg.text("碎片不够的一边呆着去");
                    bot.sendGroupMsg(groupId, msg, false);
                    return MESSAGE_BLOCK;
                }
                msg.text("超级加倍成功\n");
                prop.setDebris(prop.getDebris() - 2000);
                propService.saveOrUpdate(prop);
                multi = 4;


            } else if (!text.equals("")){
                bot.sendGroupMsg(groupId, "就两个选项，这周不扣东西，下周乱说就直接扣次数", false);
                return MESSAGE_BLOCK;
            }


            int pic = existBeliever.getDaily();
            long score = existBeliever.getScore();
            int sco = 0;
            for (int i = 0; i < pic; i++) {
                int level = Lottery();
                FeteMessage feteMessage = feteService.getByLevel(level);

                if (!(feteMessage == null)) {
                    // bot.sendGroupMsg(groupId, feteMessage.getResponse()+"\n", false);
                    sco = sco + feteMessage.getScore();
                    msg.text(feteMessage.getResponse());
                }

            }
            if (existBeliever.getDailyScore()+ sco <= 125) {
                if (existBeliever.getDailyScore()+ sco <= 25) {
                    msg.text("\n你真的太可怜了，日立神都看不下去了，触发扶贫机制，次数加二" +
                            "\n");
                    pic = 2;

                } else {
                    msg.text("\n日立神可怜每一个非洲难民，触发扶贫机制，次数加一\n");
                    pic = 1;
                }

                for (int i = 0; i < pic; i++) {
                    int level = Lottery();
                    FeteMessage feteMessage = feteService.getByLevel(level);

                    if (!(feteMessage == null)) {
                        // bot.sendGroupMsg(groupId, feteMessage.getResponse()+"\n", false);
                        sco = sco + feteMessage.getScore();
                        msg.text(feteMessage.getResponse());
                    }

                }
            }

            sco=sco*multi;
            score = score + sco;
            long dailyScore = sco + existBeliever.getDailyScore();
            int daily = 0;
            int fre = existBeliever.getFrequency() + existBeliever.getDaily();
            float avg = (float) score / fre;
            msg.text("\n");
            String levelTitle = this.LevelJudge(score);

            existBeliever.setScore(score);
            existBeliever.setFrequency(fre);
            existBeliever.setAvg(avg);
            existBeliever.setDaily(daily);
            existBeliever.setLevel(levelTitle);
            existBeliever.setDailyScore((int) dailyScore);


            msg.at(event.getUserId())
                    .text("\n恭喜，获得积分" + sco);


            believerService.saveOrUpdate(existBeliever);

            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }


        if (text.equals("虔诚如我")) {
            Believer existBeliever = believerService.getByQQ(String.valueOf(userId));
            SignRecord signRecord = signService.getByQQ(QQ);
            Prop prop = propService.getByQQ(QQ);
            if (ObjectUtils.isEmpty(existBeliever)) {
                bot.sendGroupMsg(groupId, "请先注册，注册教程请输入'教程'查看", false);
                return MESSAGE_BLOCK;
            }
            String title = existBeliever.getTitle() != null ? existBeliever.getTitle() : existBeliever.getLevel();
            msg.at(userId).text("\n" + existBeliever.getName()).text("你目前的积分是" + existBeliever.getScore() + "\n")
                    .text("你目前的等级是 " + title + " 你目前的固定抽卡次数是" + existBeliever.getFixedTime());
            int debris;
            if (!signRecord.isDailySign()) {
                signRecord.setTotalSign(signRecord.getTotalSign() + 1);
                signRecord.setUpdateTime(TimeUtil.getNowTimestamp());
                signRecord.setDailySign(true);
                RiLiLearning riLiLearning = riLiLearningService.getByDate(TimeUtil.getLastToday());
                prop = propService.getByQQ(QQ);
                msg.text("\n" + "签到成功,");

                if (riLiLearning.getLearn()) {
                    debris = 300 + riLiLearning.getSpeakCount();
                } else {
                    debris = 300 - riLiLearning.getSpeakCount();
                }
                if (debris < 0) {
                    debris = 0;
                    msg.text("日立昨天没学习不倒扣就不错了哦");
                }
                prop.setDebris(prop.getDebris() + debris);
                msg.text("\n获得碎片:" + debris);
                signService.saveOrUpdate(signRecord);
                propService.saveOrUpdate(prop);
            }
            msg.text("\n你目前的碎片数是" + prop.getDebris());

            bot.sendGroupMsg(groupId, msg, false);



            return MESSAGE_BLOCK;
        }


        if (text.equals("哐次哐次，谁是世界上最虔诚的信徒")) {
            List<Believer> believers = believerService.getOrderByScoreDesc();
            msg.text("今天虔诚的信徒们又在祷告，那么最虔诚的他们是谁呢\n");
            for (int i = 1; i < believers.size(); i++) {
                msg.text(i + ". " + believers.get(i).getName() + " 积分 " + believers.get(i).getScore() + "\n");
            }
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }

        if (text.equals("哐次哐次，谁是世界上最惨的信徒")) {
            List<Believer> believers = believerService.getOrderByAvgAsc();
            msg.text("今天很惨的信徒们又很惨\n");
            int seq;
            for (int i = 0; i < believers.size(); i++) {
                seq = i + 1;
                msg.text(seq + ". " + believers.get(i).getName() + " 积分 " + believers.get(i).getScore())
                        .text("  次数： " + believers.get(i).getFrequency() + "\n");
            }
            bot.sendGroupMsg(groupId, msg, false);
            return MESSAGE_BLOCK;
        }


        return MESSAGE_IGNORE;
    }

    public int Lottery() {

        Random rd = new Random();
        int probability = rd.nextInt(100) + 1;
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


    @Scheduled(cron = "0 0 3 * * ?")
    public void refresh() {
        signService.refreshDaily();
        RiLiLearning riLiLearning = riLiLearningService.getByDate(TimeUtil.getLastToday());
        for (Believer believer : believerService.getList())
            if (riLiLearning.getLearn()) {
                believerService.refreshDaily(believer.getQQ(), believer.getFixedTime());
            } else {
                believerService.refreshDaily(believer.getQQ(), believer.getFixedTime() - 1);
            }
    }


}
