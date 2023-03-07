package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 报警数据
 */
@Data
@Accessors(chain = true)
public class WarningData implements  RealTimeDataContent,CovertByteArray{

    /**
     * 最高报警等级
     */
    private GbValue<Short> highAlarmLevel;


    /**
     * 通用报警标志
     */
    private CommonWarningFlg commonWarningFlg;

    /**
     *可充电储能装置故障总数
     */
    private GbValue<Short> energyStorageAlertCount;

    /**
     * 可充电储能装置故障代码列表
     */
    private List<FaultCodeData> energyStorageAlertList;


    /**
     * 驱动电机故障总数
     */
    private GbValue<Short> machineFaultCount;


    /**
     * 驱动电机故障代码列表
     */
    private List<FaultCodeData> machineFaultList;


    /**
     * 发动机故障总数
     * @return
     */
    private GbValue<Short> motorFaultCount;


    /**
     * 发动机故障代码列表
     */
    private List<FaultCodeData> motorFaultList;


    /**
     * 其他故障总数
     * @return
     */
    private GbValue<Short> otherFaultCount;


    /**
     * 其他故障代码列表
     */
    private List<FaultCodeData> otherFaultList;


    @Override
    public RealTimeDataTypeEnum getContentType() {
        return RealTimeDataTypeEnum.WARNING_DATA;
    }

    @Override
    public byte[] covert() throws Exception {
        return  ByteConvertUtil.covertClassForUpload(this);
    }


    private void addFaultCode2Bytes(List<FaultCodeData> codeDataList, List<byte[]> data) throws Exception {
        if(CollectionUtils.isEmpty(codeDataList)){
            return;
        }

        for (FaultCodeData codeData : codeDataList){
            data.add(codeData.covert());
        }
    }


    private byte[] parseCommonWarningFlgToBytes() throws Exception {
        return this.commonWarningFlg.covert();
    }

    private CommonWarningFlg parseBytesToCommonWarningFlg(byte[] bytes, WarningData data) throws Exception {
        return CommonWarningFlg.parseBytes(bytes,null);
    }

}
