package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * 实时数据的 信息类型标识
 */
@Getter
public enum RealTimeDataTypeEnum implements BaseEnum{


    /**
     * 整车数据
     */
    HOLE_VEHICLE((byte) 1),

    /**
     * 驱动电机
     */
    DRIVER_MOTOR((byte) 2),

    /**
     * 燃料电池
     */
    FUEL_CELL((byte) 3),

    /**
     * 发动机数据
     */
    ENGINE_DATA((byte) 4),

    /**
     * 车辆位置数据
     */
    VEHICLE_LOCATION((byte) 5),


    /**
     * 极值数据
     */
    EXTREMUM_DATA((byte) 6),

    /**
     * 报警数据
     */
    WARNING_DATA((byte) 7),

    /**
     * todo: 其他的都是预留
     */


    ;


    /**
     * 类型
     */
    private final byte type;


    private RealTimeDataTypeEnum(byte type) {
        this.type = type;
    }

    @Override
    public byte[] enum2Bytes() {
        return new byte[]{type};
    }

    @Override
    public Object parseToEnum(byte[] bytes) throws Exception {
        ByteUtil.checkBytesLength(bytes,1);
        return  Arrays.stream(RealTimeDataTypeEnum.values())
                .filter(e->e.getType()==bytes[0])
                .findFirst()
                .orElseThrow(()->new Exception("不支持的字节值:"+bytes[0]));

    }
}
