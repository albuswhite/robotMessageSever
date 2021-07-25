package com.white.robot.warehouse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum MessageEnum {
    M1("太难了", "会则不难，难则不会！\n" + "还是因为你不会嘛！"),
    M2("官方群", "学校是没有官方群的，说自己是官方群的都是骗子"),
    M3("可以","可以可以，这很哐次"),
    M4("白白","你们找可爱的白白干嘛"),
    M5("考研问题","考研问题请看群公告"),
    M6("就业问题","就业问题请看群公告"),
    M100("新生手册", "请自行查看群公告");
    private final String req;
    private final String resp;


}
