package com.white.robot.warehouse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum FuzzyEnum {
    M1("男女比", "果园男女比2:1，当然脱单和男女比无关，具体请参见新生手册"),
    M19("学妹", "学妹小心，不要听怪叔叔的话"),
    M20("学弟", "学弟这个物种，懂得都懂");

    private final String req;
    private final String resp;


}

