package com.zxl.gb;

import lombok.Getter;

/**
 * 国标数据类型标识
 */
@Getter
public enum GbDataTypeFlg {


    /**
     * 整车数据
     */
    WHOLE_DATA("01"),

    /**
     * 驱动电机数据
     */
    DRIVE_MOTOR("02"),

    /**
     * 燃料电池数据
     */
    FUEL_CELL("03"),

    /**
     * 发动机数据
     */
    ENGINE("04"),

    /**
     * 车辆位置数据
     */
    VEHICLE_LOCATION("05"),

    /**
     * 极值数据
     */
    EXTREMUM("06"),

    /**
     * 报警数据
     */
    warning("07"),




    ;
    /**
     * 标识
     */
    private String flg;


    GbDataTypeFlg(String flg) {
        this.flg = flg;
    }
}
