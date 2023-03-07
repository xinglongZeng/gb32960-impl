package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Getter;

/**
 * 国标-应答标志
 */
@Getter
public enum ReplyFlgEnum implements BaseEnum{

    /**
     * 成功
     */
    SUCCESS((byte)1),

    /**
     * 错误
     */
    ERR((byte)2),

    /**
     * vin重复
     */
    VIN_REPEAT((byte)3),

    /**
     * 命令, 相当于16进制的0xFE
     */
    COMMAND((byte)0xFE),

    ;

    /**
     * 命令标识
     */
    private byte flg;


    ReplyFlgEnum(byte flg) {
        this.flg = flg;
    }



    @Override
    public byte[] enum2Bytes() {
         return new byte[]{flg};
    }

    @Override
    public Object parseToEnum(byte[] bytes) throws Exception {
        ByteUtil.checkBytesLength(bytes,1);
        return  getEnumByByte(bytes[0]);
    }


    public static ReplyFlgEnum getEnumByByte(byte b) throws Exception {
        switch (b){
            case 0x01:
                return SUCCESS;
            case 0x02:
                return ERR;
            case 0x03:
                return VIN_REPEAT;
            case (byte)0xFE:
                return COMMAND;
            default:
                throw new Exception("不支持的字节:"+b);
        }


    }


}
