package com.white.robot;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public  enum MessageEnum {
    M1("学妹加我","学妹小心，不要听怪叔叔的话"),
    M2("学弟","学弟这个物种，懂得都懂");

    String req;
    String resp;


    MessageEnum(String req, String resp) {
        this.req=req;
        this.resp=resp;
    }
}
