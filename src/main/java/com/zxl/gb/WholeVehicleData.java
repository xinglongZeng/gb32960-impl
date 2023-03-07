package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *  整车数据
 */
@Data
@Accessors(chain = true)
public class WholeVehicleData implements RealTimeDataContent,CovertByteArray{

    /**
     * 车辆状态. 1字节
     */
    private GbVehicleState gbVehicleState;

    /**
     * 充电状态。 1字节
     */
    private GbRechargeState gbRechargeState;

    /**
     * 运行模式
     */
    private GbRunningMode gbRunningMode;

    /**
     * 车速. 有效值范围 : 0~220km/h, 最小计量单位: 0.1
     */
    private GbValue<Double> speed;


    /**
     * 总里程
     *  有效值范围: 0~999999.9km,最小计量单位:0.1km
     */
    private GbValue<Double> totalMileage;



    /**
     * 总电压
     * 有效值范围: 0~1000v,最小计量单位:0.1v
     */
    private GbValue<Double>  totalVoltage;


    /**
     * 总电流
     * 有效值范围: -1000A ~ +1000v,最小计量单位:0.1A
     */
    private GbValue<Double>  totalElectricity;

    /**
     * SOC
     *  有效值范围 : 0~100, 表示:0% ~ 100%
     */
    private GbValue<Short> soc;

    /**
     * DC-DC状态
     */
    private DcStateEnum dcStateEnum;


    /**
     * 挡位. 0-15 一共16个挡位
     *
     *  0:空挡；
     *  1：1档
     *  ...
     *  12 : 12档
     *  13 : 倒挡
     *  14 : 自动D档
     *  15 : 停车P档
     *
     */
    private GearsEnum gbGears;



    /**
     * 绝缘电阻
     *    0 ~ 60000 千欧
     */
    private GbValue<Integer> insulationResistance;


    /**
     * 预留,2个字节
     *
     */
    private Object reserved;

    @Override
    public RealTimeDataTypeEnum getContentType() {
        return RealTimeDataTypeEnum.HOLE_VEHICLE;
    }

    @Override
    public byte[] covert() throws Exception {
        return  ByteConvertUtil.covertClassForUpload(this);
    }

    private byte[] parseReservedToBytes(){
        byte[] bytes = new byte[2];
        return bytes;
    }

    private Object parseBytesToReserved(byte[] bytes,WholeVehicleData data){
        return null;
    }

}
