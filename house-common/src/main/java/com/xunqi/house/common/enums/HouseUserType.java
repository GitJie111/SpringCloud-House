package com.xunqi.house.common.enums;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-08 15:41
 **/
public enum HouseUserType {

    SALE(1),
    BOOKMARK(2)
    ;

    public final Integer value;

    private HouseUserType(Integer value) {
        this.value = value;
    }

}
