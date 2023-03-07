package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 驱动电机数据
 * @author zxl
 */
@Data
@Accessors(chain = true)
public class DriverMotorData implements RealTimeDataContent, CovertByteArray {


    /**
     * 驱动电机个数. 有效值: 1~253
     */
    private GbValue<Short> driveMotorCounter;

    /**
     * 驱动电机信息列表
     */
    private List<GbDriverMoterInfo> gbDriverMoterInfoList;


    @Override
    public RealTimeDataTypeEnum getContentType() {
        return RealTimeDataTypeEnum.DRIVER_MOTOR;
    }

    @Override
    public byte[] covert() throws Exception {
       return ByteConvertUtil.covertClassForUpload(this);
    }


}



