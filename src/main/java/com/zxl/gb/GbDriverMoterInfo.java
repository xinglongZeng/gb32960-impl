package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 驱动电机总层信息列表
 * @author zxl
 */
@Data
@Accessors(chain = true)
public class GbDriverMoterInfo implements CovertByteArray,Comparable<GbDriverMoterInfo>{


    /**
     * 驱动电机序号. 有效值 1-253
     */
    private GbValue<Short> seq;


    /**
     * 驱动电机状态
     */
    private GbDriverMoterStateEnum stateEnum;


    /**
     * 驱动电机控制器温度  (-40度  到 210度)
     */
    private GbValue<Short> controlTemperature;

    /**
     * 驱动电机转速
     * 有效值范围:  -20000  到  45531
     */
    private GbValue<Integer> rotateSpeed;


    /**
     * 驱动电机转矩
     */
    private GbValue<Double> torque;


    /**
     * 驱动电机温度
     */
    private GbValue<Short> temperature;


    /**
     * 电机控制器输入电压
     */
    private GbValue<Double> inputVoltage;


    /**
     * 电机控制器直流母线电流
     */
    private GbValue<Double> mlElectricity;

    @Override
    public byte[] covert() throws Exception {
        return ByteConvertUtil.covertClassForUpload(this);
    }



    @Override
    public int compareTo(GbDriverMoterInfo moterInfo) {
        return this.getSeq().getValue() - moterInfo.getSeq().getValue();
    }
}
