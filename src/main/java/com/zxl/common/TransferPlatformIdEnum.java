package com.zxl.common;

import lombok.Getter;

/**
 * 支持的新能源数据上报的渠道的枚举
 */
@Getter
public enum TransferPlatformIdEnum {

    /**
     * 国标渠道
     */
    GB("GB")


    ;


    private String id;


    TransferPlatformIdEnum(String id) {
        this.id = id;
    }
}
