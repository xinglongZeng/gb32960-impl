package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GbRechargeState implements BaseEnum{

    /**
     * 停车充电
     */
    RECHARGE_STOP((byte)1),

    /**
     * 行驶充电
     */
    RECHARGE_RUNNING((byte)2),

    /**
     * 未充电
     */
    UN_RECHARGE((byte)3),

    /**
     * 充电完成
     */
    RECHARGE_FINISH((byte)4),


    /**
     * 异常
     */
    EXCEPTION((byte)0xFE),


    /**
     * 无效
     */
    INVALID((byte)0xFF),



    ;

    private final byte state;


    GbRechargeState(byte state) {
        this.state = state;
    }

    @Override
    public byte[] enum2Bytes() {
        return new byte[]{state};
    }

    @Override
    public Object parseToEnum(byte[] bytes) throws Exception {
        ByteUtil.checkBytesLength(bytes,1);
        return  Arrays.stream(GbRechargeState.values())
                .filter(e->e.getState()==bytes[0])
                .findFirst()
                .orElseThrow(()->new Exception("不支持的字节值:"+bytes[0]));

    }
}
