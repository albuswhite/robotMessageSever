package com.white.robot.Util;

import com.white.robot.entity.Prop;
import com.white.robot.entity.SignRecord;

public class InitialUtil {

    public static SignRecord newSignRecord(String QQ){
        SignRecord signRecord= new SignRecord();
        signRecord.setDailySign(false);
        signRecord.setUpdateTime(TimeUtil.getNowTimestamp());
        signRecord.setTotalSign(0);
        signRecord.setQQ(QQ);

        return signRecord;
    }
    public static Prop newProp(String QQ){
        Prop prop =new Prop();
        prop.setBuffDuration(0);
        prop.setBuffStatus(0);
        prop.setProtectDuration(0);
        prop.setQQ(QQ);
        prop.setDebris(0);
        prop.setProtectStatus(0);
        prop.setDoubleCard(0);
        prop.setWipeCard(0);
        prop.setLuckCard(0);
        prop.setDuelCard(0);
        prop.setPietyCard(0);
        return prop;

    }

}
