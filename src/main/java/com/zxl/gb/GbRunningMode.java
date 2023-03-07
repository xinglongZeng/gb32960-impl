package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GbRunningMode implements BaseEnum{

    // 纯电
    ELETRIC((byte)0x01),

    // 混动
    MIXED((byte)0x02),

    // 燃油
    OIL((byte)0x03),

    /**
     * 异常
     */
    EXCEPTION((byte)0xFE),


    /**
     * 无效
     */
    INVALID((byte)0xFF),



    ;


    private final byte mode;


    GbRunningMode(byte mode) {
        this.mode = mode;
    }

    @Override
    public byte[] enum2Bytes() {
        return new byte[]{this.getMode()};
    }

    @Override
    public Object parseToEnum(byte[] bytes) throws Exception {
        ByteUtil.checkBytesLength(bytes,1);
        return  Arrays.stream(GbRunningMode.values())
                .filter(e->e.getMode()==bytes[0])
                .findFirst()
                .orElseThrow(()->new Exception("不支持的字节值:"+bytes[0]));

    }
}
