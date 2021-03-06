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
    M6("学妹", "学妹小心，他们都是怪蜀黍怪阿姨"),
    M7("学姐","请加可爱的白白学姐"),
    M9("就业问题","就业问题请看群公告"),
    M10("保研率","关于保研率，保研人数，保研顺延后的百分比概念，请参考常见问题"),
    M11("学长","这个群不存在学长这种生物"),
    M20("学弟", "学弟这个物种，懂得都懂"),
    M21("日立","日立好可爱！快加可爱的日立！"),
    M22("哐哐","哐哐是个怪阿姨"),
    M23("奶盖","是个温柔到不见人影的阿姨"),
    M24("呵呵","呵呵呵"),
    M100("新生手册", "请自行查看群公告");
    private final String req;
    private final String resp;


}
