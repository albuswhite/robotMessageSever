package com.white.robot.warehouse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum MessageEnum {
    M1("太难了", "会则不难，难则不会！\n" + "还是因为你不会嘛！"),
    M2("官方群", "学校是没有官方群的，说自己是官方群的都是骗子"),
    M3("可以","可以可以，这很哐次"),
    M4("哐次","嘟嘟嘟，哐次哐次o(￣▽￣)ｄ，叫我干嘛"),
    M5("哐次哐次","嘟嘟嘟，哐次哐次o(￣▽￣)ｄ，叫我干嘛"),
    M100("新生手册", "https://docs.qq.com/doc/BLIGHi0op6F60KZQIK1hrUCb4nD98o3xmHNn0spAgc3ojLjX3vwexQ0eCYwB0qAvGk251TRI4");
    private final String req;
    private final String resp;


}
