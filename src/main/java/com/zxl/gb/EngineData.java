package com.zxl.gb;


import com.zxl.util.ByteConvertUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/** 发动机数据
 *
 */
@Data
@Accessors(chain = true)
public class EngineData implements RealTimeDataContent,CovertByteArray{



    /**
     * 发动机状态
     */
    private EngineStateEnum engineSts;


    /**
     * 曲轴转速 ,数据单位： r/min
     */
    private GbValue<Integer> crankShaftSpeed;


    /**
     * 燃料消耗率, 数据单位： L/100KM , 最小计量单位：0.01 L/100KM
     */
    private GbValue<Double> fuelCellConsumptionRate;



    @Override
    public RealTimeDataTypeEnum getContentType() {
        return RealTimeDataTypeEnum.ENGINE_DATA;
    }

    @Override
    public byte[] covert() throws Exception {
        return ByteConvertUtil.covertClassForUpload(this);
    }

}
