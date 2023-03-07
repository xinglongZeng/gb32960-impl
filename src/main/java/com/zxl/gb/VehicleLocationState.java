package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 车辆定位状态
 * @author zxl
 */
@Data
public class VehicleLocationState implements CovertByteArray{


    /**
     * 定位状态是否有效
     *
     *  0: 有效， 1:无效
     */
    @Length(min = 0,max = 1)
    private short validFlg;

    /**
     * 纬度类型
     * 0: 北纬， 1：南纬
     *
     */
    @Length(min = 0,max = 1)
    private short latitudeType;


    /**
     *  经度类型
     *  0: 东经
     *  1： 西经
     *
     */
    @Length(min = 0,max = 1)
    private short longitudeType;


    /**
     *     b |= (1 << bitIndex); // set a bit to 1
     *     b &= ~(1 << bitIndex); // set a bit to 0
     */
    @Override
    public byte[] covert() throws Exception {
        byte b =  (byte) 0;

        // 0位为状态位
        b =  ByteUtil.setBitByIndexFromByte(b,0,this.validFlg);

        // 1位为维度类型
        b = ByteUtil.setBitByIndexFromByte(b,1,this.latitudeType);

        // 2位为经度类型
        b = ByteUtil.setBitByIndexFromByte(b,2,this.longitudeType);

        // 3-7位 为保留

        return new byte[]{b};
    }

    public VehicleLocationState parseByte(byte[] bytes, VehicleLocationState instance ) throws Exception {
        if(instance == null){
            instance = new VehicleLocationState();
        }
        byte b = bytes[0];
        instance.setValidFlg(  (short)ByteUtil.getValueFromByte(b,0));
        instance.setLatitudeType(  (short)ByteUtil.getValueFromByte(b,1) );
        instance.setLongitudeType(  (short)ByteUtil.getValueFromByte(b,2) );
        return instance;
    }

}
