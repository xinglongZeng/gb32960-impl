package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * 驱动电机状态
 */
@Getter
public enum GbDriverMoterStateEnum implements BaseEnum{

    /**
     * 耗电
     */
    POWER_SUPPLY((byte) 1),

    /**
     * 发电
     */
    POWER_GENERATION((byte) 2),

    /**
     * 关闭状态
     */
    CLOSE((byte) 3),

    /**
     * 准备状态
     */
    READY((byte) 4),

    /**
     * 异常
     */
    EXCEPTION((byte) 254),

    /**
     * 无效
     */
    INVALID((byte) 255),

    ;


    /**
     * 类型
     */
    private final byte state;

    private GbDriverMoterStateEnum(byte state) {
        this.state = state;
    }

    @Override
    public byte[] enum2Bytes() {
        return new byte[]{state};
    }

    @Override
    public Object parseToEnum(byte[] bytes) throws Exception {
        ByteUtil.checkBytesLength(bytes,1);
        return Arrays.stream(GbDriverMoterStateEnum.values())
                .filter(e->e.getState()==bytes[0])
                .findFirst()
                .orElseThrow(()->new Exception("不支持的字节值:"+bytes[0]));
    }


}
