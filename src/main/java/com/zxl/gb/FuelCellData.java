package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 *  燃料电池数据
 */
@Data
@Accessors(chain = true)
public class FuelCellData implements RealTimeDataContent,CovertByteArray{

    /**
     * 燃料电池电压
     */
    private GbValue<Double> fuelVolTag;

    /**
     * 燃料电池电流
     */
    private GbValue<Double> fuelCurrent;

    /**
     * 燃料消耗率
     */
    private GbValue<Double> fuelConsumption;

    /**
     * 燃料电池温度探针总数
     */
    private GbValue<Integer>   fuelCellTemperaturesProbeNum;

    /**
     * 探针温度值集合.
     * 当fuelCellTemperaturesProbeNum不为0时，probeTemperatureList也不能为空
     */
    private List<ProbeTemperatures> probeTemperatureList;

    /**
     * 氢系统中最高温度
     */
    private GbValue<Double> hydrogenSystemMaxTemperature;

    /**
     * 氢系统中最高温度探针代号
     */
    private GbValue<Short> probeIndexOfHydrogenSystemMaxTemperature;



    /**
     * 氢气最高浓度
     */
    private GbValue<Integer> hydrogenMaxConcentration;



    /**
     * 氢气最高浓度传感器代号
     */
    private GbValue<Short> sensorIndexOfHydrogenMaxConcentration;



    /**
     * 氢气最高压力
     */
    private GbValue<Double> hydrogenMaxPressure;


    /**
     * 氢气最高压力传感器代号
     */
    private GbValue<Short> sensorIndexOfHydrogenMaxPressure;

    /**
     * 高压 DC/DC状态
     */
    private DcStateEnum evDcSts;

    @Override
    public RealTimeDataTypeEnum getContentType() {
        return RealTimeDataTypeEnum.FUEL_CELL;
    }

    @Override
    public byte[] covert() throws Exception {
        return ByteConvertUtil.covertClassForUpload(this);
    }



}
