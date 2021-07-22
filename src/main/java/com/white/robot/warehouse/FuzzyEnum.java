package com.white.robot.warehouse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum FuzzyEnum {
    M1("男女比", "果园男女比2:1，当然脱单和男女比无关，具体请参见新生手册"),
    M19("学妹", "学妹小心，他们都是怪蜀黍怪阿姨"),
    M21("学姐","请加可爱的白白学姐"),
    M4("哐次","嘟嘟嘟，哐次哐次o(￣▽￣)ｄ，叫我干嘛"),
    M20("学弟", "学弟这个物种，懂得都懂");

    private final String req;
    private final String resp;


}

