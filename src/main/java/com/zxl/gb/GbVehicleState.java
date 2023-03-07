package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GbVehicleState implements BaseEnum{

    /**
     * 启动
     */
    STARING((byte)1),

    /**
     * 熄火
     */
    FLAME_OUT((byte)2),

    /**
     * 其他
     */
    OTHER((byte)3),

    /**
     * 异常
     */
    EXCEPTION((byte)0xFE),


    /**
     * 无效
     */
    INVALID((byte)0xFF),

    ;

    private byte state;


    GbVehicleState(byte state) {
        this.state = state;
    }

    @Override
    public byte[] enum2Bytes() {
        return new byte[]{state};
    }

    @Override
    public Object parseToEnum(byte[] bytes) throws Exception {
        ByteUtil.checkBytesLength(bytes,1);
        return  Arrays.stream(GbVehicleState.values())
                .filter(e->e.getState()==bytes[0])
                .findFirst()
                .orElseThrow(()->new Exception("不支持的字节值:"+bytes[0]));

    }
}
