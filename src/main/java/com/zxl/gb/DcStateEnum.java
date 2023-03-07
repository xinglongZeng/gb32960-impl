package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DcStateEnum implements BaseEnum{
    /**
     * 工作
     */
    WORK((byte)1),

    /**
     * 断开
     */
    BREAK((byte)2),



    /**
     * 异常
     */
    EXCEPTION((byte)0xFE),


    /**
     * 无效
     */
    INVALID((byte)0xFF),

    ;

    private byte v;


    DcStateEnum(byte v) {
        this.v = v;
    }

    @Override
    public byte[] enum2Bytes() {
        return new byte[]{v};
    }

    @Override
    public Object parseToEnum(byte[] bytes) throws Exception {
        ByteUtil.checkBytesLength(bytes,1);
       return  Arrays.stream(DcStateEnum.values())
                .filter(e->e.getV()==bytes[0])
                .findFirst()
                .orElseThrow(()->new Exception("不支持的字节值:"+bytes[0]));

    }
}
