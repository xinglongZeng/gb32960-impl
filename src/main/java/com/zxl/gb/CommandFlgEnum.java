package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Getter;

@Getter
public enum CommandFlgEnum implements BaseEnum{

    /**
     * 车辆登入
     */
    VEHICLE_LOGIN((byte)1),

    /**
     * 实时信息上报
     */
    REAL_TIME_DATA((byte)2),


    /**
     * 补发信息上报
     */
    REISSUE_DATA((byte)3),

    /**
     * 车辆登出
     */
    VEHICLE_LOGOUT((byte)4),

    /**
     * 平台登入
     */
    PLATFORM_LOGIN((byte)5),

    /**
     * 平台登出
     */
    PLATFORM_LOGOUT((byte)6),


    /**
     * todo: 07至FE预留
     */
    RESERVE((byte)7);

    ;


    /**
     * 命令标识
     */
    private byte flg;


    CommandFlgEnum(byte flg) {

        this.flg = flg;
    }

    @Override
    public byte[] enum2Bytes() {
        return new byte[]{flg};
    }

    @Override
    public Object parseToEnum(byte[] bytes) throws Exception {
        ByteUtil.checkBytesLength(bytes,1);
        return getEnumByByte(bytes[0]);
    }


    public static CommandFlgEnum getEnumByByte(byte b){
        switch (b){
            case 1:
                return VEHICLE_LOGIN;
            case 2:
                return REAL_TIME_DATA;
            case 3:
                return REISSUE_DATA;
            case 4:
                return VEHICLE_LOGOUT;
            case 5:
                return PLATFORM_LOGIN;
            case 6:
                return PLATFORM_LOGOUT;
            default:
                return RESERVE;
        }


    }


}
