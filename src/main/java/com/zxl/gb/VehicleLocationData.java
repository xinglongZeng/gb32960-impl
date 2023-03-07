package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 *  车辆位置数据
 */
@Data
@Accessors(chain = true)
public class VehicleLocationData implements RealTimeDataContent,CovertByteArray{


    private VehicleLocationState locationState;


    /**
     * 经度，以度为单位，精确到百万分之1度
     */
    private GbValue<Double> longitude;

    /**
     * 维度，以度为单位，精确到百万分之1度
     */
    private GbValue<Double> latitude;



    @Override
    public RealTimeDataTypeEnum getContentType() {
        return RealTimeDataTypeEnum.VEHICLE_LOCATION;
    }


    @Override
    public byte[] covert() throws Exception {
        return ByteConvertUtil.covertClassForUpload(this);
    }

    public byte[] parseLocationStateToBytes() throws Exception {
        return this.locationState.covert();
    }

    public VehicleLocationState parseBytesToVehicleLocationState(byte[] bytes,VehicleLocationData data) throws Exception {
        VehicleLocationState state = new VehicleLocationState();
        state.parseByte(bytes,state);
        return state;
    }

}
