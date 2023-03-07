package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * todo: 国标数据单元: 实时数据上报
 */
@Data
public class RealTimeDataUnit extends BaseDataUnit implements GbDataUnit,CovertByteArray {


    /**
     * 整车数据
     */
    private WholeVehicleData wholeVehicleData;


    /**
     * 驱动电机数据
     */
    private DriverMotorData driverMotorData;

    /**
     * 燃料电池数据
     */
    private FuelCellData fuelCellData;


    /**
     * 发动机数据
     */
    private EngineData engineData;

    /**
     * 车辆位置数据
     */
    private VehicleLocationData vehicleLocationData;


    /**
     * 极值数据
     */
    private ExtremumData extremumData;


    /**
     * 报警数据
     */
    private WarningData warningData;

    /**
     * 终端数据预留
     */
//    private TerminalReservedData terminalReservedData;


    /**
     * 平台交换协议自定义数据
     */
//    private CustomData customData;

    /**
     * 预留
     */
//    private ReservedData reservedData;

    /**
     * 用户自定义数据
     */
//    private UserCustomData userCustomData;


    @Override
    public byte[] transferByteArray() throws Exception {
        return covert();
    }

    @Override
    public byte[] covert() throws Exception {

        List<byte[]> data = new ArrayList<>();

        Map<String, Field> fieldMap = ByteConvertUtil.getFieldMap(this.getClass());

        // 数据采集时间
        data.add(ByteUtil.processDateType(this.getTime().getValue()));

        for (Map.Entry<String,Field> entry : fieldMap.entrySet()){

            Field field = entry.getValue();

            if(field.getName().equals("time")){
                continue;
            }

            field.setAccessible(true);

            Object value = field.get(this);

            if(value!=null){
                RealTimeDataContent content = (RealTimeDataContent) value;
                data.add(content.getContentType().enum2Bytes());
                data.add(ByteConvertUtil.covertClassForUpload(value));
            }
        }
        return ByteConvertUtil.byteList2Array(data);
    }

    public static RealTimeDataUnit parseBytes(byte[] bytes, RealTimeDataUnit dataUnit) throws Exception {
        if(dataUnit==null){
            dataUnit = new RealTimeDataUnit();
        }


        Date time = null;

        int index = 0;

        while (index < bytes.length ){
            if(time == null){
                time = ByteUtil.convertByteArrayToDate(ByteUtil.subBytes(bytes,0,5));
                index += 6 ;
            }else {
                // 根据类型标识进行解析
                RealTimeDataTypeEnum type = (RealTimeDataTypeEnum) RealTimeDataTypeEnum.DRIVER_MOTOR.parseToEnum(ByteUtil.subBytes(bytes,index,index));
                index+=1;
                index += setFieldByRealTimeDataTypeEnum(ByteUtil.subBytes(bytes,index,bytes.length-1),type,dataUnit);
            }

        }


        return dataUnit;
    }

    private static int setFieldByRealTimeDataTypeEnum(byte[] bytes, RealTimeDataTypeEnum type, RealTimeDataUnit dataUnit) throws Exception {
        List<CovertConfig> configList = null;
        Object instance= null;
        switch (type){
            case HOLE_VEHICLE:
                instance = new WholeVehicleData();
                dataUnit.setWholeVehicleData( (WholeVehicleData) instance);
                break;
            case DRIVER_MOTOR:
                instance = new DriverMotorData();
                dataUnit.setDriverMotorData( (DriverMotorData) instance);
                break;
            case FUEL_CELL:
                instance = new FuelCellData();
                dataUnit.setFuelCellData( (FuelCellData) instance);
                break;
            case ENGINE_DATA:
                instance = new EngineData();
                dataUnit.setEngineData( (EngineData) instance);
                break;
            case VEHICLE_LOCATION:
                instance = new VehicleLocationData();
                dataUnit.setVehicleLocationData( (VehicleLocationData) instance);
                break;
            case EXTREMUM_DATA:
                instance = new ExtremumData();
                dataUnit.setExtremumData( (ExtremumData) instance);
                break;
            case WARNING_DATA:
                instance = new WarningData();
                dataUnit.setWarningData( (WarningData) instance);
                break;
            default:
                throw new Exception("不支持的类型:"+type);
        }

        configList = ByteConvertUtil.getCovertConfigList(instance.getClass());

        return ByteConvertUtil.setFieldOnInstance(bytes, instance, configList);
    }


}
