package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Getter;

/**
 * 国标- 数据单元加密方式
 */
@Getter
public enum EncryptWay implements BaseEnum {

    /**
     * 不加密
     */
    NOT_ENCRYPT((byte)1),

    /**
     * rsa加密
     */
    RSA((byte)2),

    /**
     * aes128加密
     */
    AES128((byte)3),


    /**
     * 异常  0xFE
     *
     */
    EXCEPTION((byte)0xFE),


    /**
     * 无效  0xFF
     */
    INVALID((byte)0xFF),

    ;


    private final byte way;

    EncryptWay(byte way) {
        this.way = way;
    }

    @Override
    public byte[] enum2Bytes() {
        return new byte[]{way};
    }

    @Override
    public Object parseToEnum(byte[] bytes) throws Exception {
        ByteUtil.checkBytesLength(bytes,1);
        return getEnumByBytes(bytes[0]);
    }


    public static EncryptWay getEnumByBytes(byte b) throws Exception {

        for (EncryptWay  e: EncryptWay.values()){
            if(e.getWay()==b){
                return e;
            }
        }

        throw new Exception("不支持的字节:"+b);
    }



}
