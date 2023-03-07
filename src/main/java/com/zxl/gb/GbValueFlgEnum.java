package com.zxl.gb;

import lombok.Getter;

@Getter
public enum GbValueFlgEnum {


    /**
     * 正常
     */
    OK(1),

    /**
     * 无效
     */
    INVALID(0),


    /**
     * 异常
     */
    EXCEPTION(-1),


    ;

    private int flg;

    GbValueFlgEnum(int flg) {
        this.flg = flg;
    }
}
