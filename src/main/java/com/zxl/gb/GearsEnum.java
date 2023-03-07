package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * 挡位枚举
 */
@Getter
public enum GearsEnum implements BaseEnum{

    //空挡
    NEUTRAL_GEAR((byte)0x00),
    //1挡
    GEAR_1((byte)0x01),
    //2挡
    GEAR_2((byte)0x02),
    //3挡
    GEAR_3((byte)0x03),
    //4挡
    GEAR_4((byte)0x04),
    //5挡
    GEAR_5((byte)0x05),
    //6挡
    GEAR_6((byte)0x06),
    //7挡
    GEAR_7((byte)0x07),
    //8挡
    GEAR_8((byte)0x08),
    //9挡
    GEAR_9((byte)0x09),
    //10挡
    GEAR_10((byte)0xA),
    //11挡
    GEAR_11((byte)0xB),
    //12挡
    GEAR_12((byte)0xC),
    //倒挡
    REVERSE_GEAR((byte)0xD),
    //自动D当
    GEAR_D((byte)0xE),
    //停车P档
    GEAR_P((byte)0xF),


    ;

    /**
     * 类型
     */
    private final byte gear;

    GearsEnum(byte gear) {
        this.gear = gear;
    }

    @Override
    public byte[] enum2Bytes() {
        return new byte[]{this.getGear()};
    }

    @Override
    public Object parseToEnum(byte[] bytes) throws Exception {
        ByteUtil.checkBytesLength(bytes,1);
        return  Arrays.stream(GearsEnum.values())
                .filter(e->e.getGear()==bytes[0])
                .findFirst()
                .orElseThrow(()->new Exception("不支持的字节值:"+bytes[0]));

    }
}
