package com.white.robot.warehouse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum FuzzyEnum {
    M1("男女比", "果园男女比2:1，当然脱单和男女比无关，具体请参见新生手册"),
    M2("学妹", "学妹小心，他们都是怪蜀黍怪阿姨"),
    M3("学姐","请加可爱的白白学姐"),
    M4("哐次","嘟嘟嘟，哐次哐次o(￣▽￣)ｄ，叫我干嘛"),
    M5("学长","这个群不存在学长这种生物"),
    M6("买电脑","电脑问题请看常见问题4"),
    M7("考研","考研问题在常见问题中有详细阐述，请仔细阅读后再提问"),
    M8("上岸","考研问题在常见问题中有详细阐述，请仔细阅读后再提问"),
    M9("转专业","果园不可以转专业"),
    M20("学弟", "学弟这个物种，懂得都懂");

    private final String req;
    private final String resp;


}

