package com.white.robot.warehouse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum MessageEnum {

    M3("官方群", "学校是没有官方群的，说自己是官方群的都是骗子"),
    M4("新生手册", "https://docs.qq.com/doc/BLIGHi0op6F60KZQIK1hrUCb4nD98o3xmHNn0spAgc3ojLjX3vwexQ0eCYwB0qAvGk251TRI4");
    private final String req;
    private final String resp;


}
