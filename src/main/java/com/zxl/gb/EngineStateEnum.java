package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * 发动机状态
 */
@Getter
public enum EngineStateEnum implements BaseEnum{

    /**
     * 启动
     */
    ON((byte)0x01),

    /**
     * 关闭
     */
    OFF((byte)0x02),

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

    EngineStateEnum(byte state) {
        this.state = state;
    }

    ;

    @Override
    public byte[] enum2Bytes() {
        return new byte[]{state};
    }

    @Override
    public Object parseToEnum(byte[] bytes) throws Exception {
        ByteUtil.checkBytesLength(bytes,1);
        return  Arrays.stream(EngineStateEnum.values())
                .filter(e->e.getState()==bytes[0])
                .findFirst()
                .orElseThrow(()->new Exception("不支持的字节值:"+bytes[0]));

    }
}
