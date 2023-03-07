package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 极值数据
 */
@Data
@Accessors(chain = true)
public class ExtremumData implements RealTimeDataContent,CovertByteArray{

    /**
     * 最高电压电池子系统号
     * @return
     */
    private GbValue<Short> highVolPowerBattSerial;


    /**
     * 最高电压电池单体代号
     */
    private GbValue<Short> highVolSingleBattSerial;


    /**
     * 电池单体电压最高值, 单位： V
     *  最小计量单位：0.001V
     */
    private GbValue<Double> singleBattHighVolValue;


    /**
     * 最低电压电池子系统号
     */
    private GbValue<Short>  lowVolPowerBattSerial;


    /**
     * 最低电压电池单体代号
     */
    private GbValue<Short> lowVolSingleBattSerial;



    /**
     * 电池单体电压最低值, 单位： V
     *  最小计量单位：0.001V
     */
    private GbValue<Double> singleBattLowVolValue;


    /**
     * 最高温度子系统号
     */
    private GbValue<Short> highTempPowerBattSerial;


    /**
     * 最高温度探针序号
     */
    private GbValue<Short> highTempRobeSerial;



    /**
     * 最高温度值
     */
    private GbValue<Short> highTempValue;



    /**
     * 最低温度子系统号
     */
    private GbValue<Short> lowTempPowerBattSerial;


    /**
     * 最低温度探针序号
     */
    private GbValue<Short> lowTempRobeSerial;



    /**
     * 最低温度值
     */
    private GbValue<Short> lowTempValue;





    @Override
    public RealTimeDataTypeEnum getContentType() {
        return RealTimeDataTypeEnum.EXTREMUM_DATA;
    }

    @Override
    public byte[] covert() throws Exception {
        return ByteConvertUtil.covertClassForUpload(this);
    }
}
