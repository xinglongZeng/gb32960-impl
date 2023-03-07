package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;


/**
 * 通用报警标志
 * @author zxl
 */
@Data
@Accessors(chain = true)
public class CommonWarningFlg implements CovertByteArray{

    /**
     * 温度差异报警
     *  0:正常 ， 1：报警
     *
     */
    private short tempDifAlarm;


    /**
     * 电池高温报警
     *  0:正常 ， 1：报警
     *
     */
    private short battPoleHighTempAlarm;


    /**
     * 车载储能装置类型过压报警
     *  0:正常 ， 1：报警
     *
     */
    private short powerBattOverVolAlarm;


    /**
     * 车载储能装置类型欠压报警
     *  0:正常 ， 1：报警
     *
     */
    private short powerBattUnderVolAlarm;


    /**
     * SOC低报警
     *  0:正常 ， 1：报警
     *
     */
    private short socLowAlarm;


    /**
     * 单体电池过压报警
     * 0:正常 ， 1：报警
     */
    private short singleBattOverVolAlarm;



    /**
     * 单体电池欠压报警
     * 0:正常 ， 1：报警
     */
    private short singleBattUnderVolAlarm;



    /**
     * SOC过高报警
     * 0:正常 ， 1：报警
     */
    private short socTooHighAlarm;



    /**
     * SOC跳变报警
     * 0:正常 ， 1：报警
     */
    private short socChangeAlarm;


    /**
     * 可充电储能系统不匹配报警
     * 0:正常 ， 1：报警
     */
    private short powerBattPackageDiffAlarm;


    /**
     * 电池单体一致性差报警
     * 0:正常 ， 1：报警
     */
    private short powerBattDiffAlarm;


    /**
     * 绝缘报警
     * 0:正常 ， 1：报警
     */
    private short insulationAlarm;



    /**
     * DC-DC温度报警
     * 0:正常 ， 1：报警
     */
    private short dcDcTempAlarm;


    /**
     * 制动系统报警
     * 0:正常 ， 1：报警
     */
    private short brakeSystemAlarm;


    /**
     * DC-DC状态报警
     * 0:正常 ， 1：报警
     */
    private short dcDcStatusAlarm;


    /**
     * 驱动电机控制器温度报警
     * 0:正常 ， 1：报警
     */
    private short driveMotorControllerTemperatureAlert;


    /**
     * 高压互锁状态报警
     * 0:正常 ， 1：报警
     */
    private short highVoltageInterlockStatusAlert;


    /**
     * 驱动电机温度报警
     * 0:正常 ， 1：报警
     */
    private short driveMotorTempAlert;


    /**
     * 车载储能装置类型过充
     * 0:正常 ， 1：报警
     */
    private short energyStorageOvercharge;


    /**
     * 内部类
     */
    @Getter
    protected enum Position {

        tempDifAlarm("tempDifAlarm",0),
        battPoleHighTempAlarm("battPoleHighTempAlarm",1),
        powerBattOverVolAlarm("powerBattOverVolAlarm",2),
        powerBattUnderVolAlarm("powerBattUnderVolAlarm",3),
        socLowAlarm("socLowAlarm",4),
        singleBattOverVolAlarm("singleBattOverVolAlarm",5),
        singleBattUnderVolAlarm("singleBattUnderVolAlarm",6),
        socTooHighAlarm("socTooHighAlarm",7),
        socChangeAlarm("socChangeAlarm",8),
        powerBattPackageDiffAlarm("powerBattPackageDiffAlarm",9),
        powerBattDiffAlarm("powerBattDiffAlarm",10),
        insulationAlarm("insulationAlarm",11),
        dcDcTempAlarm("dcDcTempAlarm",12),
        brakeSystemAlarm("brakeSystemAlarm",13),
        dcDcStatusAlarm("dcDcStatusAlarm",14),
        driveMotorControllerTemperatureAlert("driveMotorControllerTemperatureAlert",15),
        highVoltageInterlockStatusAlert("highVoltageInterlockStatusAlert",16),
        driveMotorTempAlert("driveMotorTempAlert",17),
        energyStorageOvercharge("energyStorageOvercharge",18),

        ;

        // 字段名称
        private String fieldName;

        // 位位置
        private int position;


         Position(String fieldName, int position) {
            this.fieldName = fieldName;
            this.position = position;
        }

        public static Position getPositionByFieldName(String fieldName) throws Exception {
            Optional<Position> op =Arrays.stream(Position.values())
                    .filter(e->e.getFieldName().equals(fieldName))
                    .findFirst();

            if(!op.isPresent()){
                throw new Exception("不识别的字段名:"+fieldName);
            }

            return op.get();
        }
    }


    @Override
    public byte[] covert() throws Exception {
        int i = 0;

        for(Field field: CommonWarningFlg.class.getDeclaredFields()){
            Position position = Position.getPositionByFieldName(field.getName());
            field.setAccessible(true);
            short v = (short)field.get(this);
            i = ByteUtil.setBitByIndexFromInt(i,position.getPosition(),v);
        }

        return ByteUtil.intToBytes(i);
    }


    public static CommonWarningFlg parseBytes( byte[] bytes, CommonWarningFlg data) throws Exception {

        ByteUtil.checkBytesLength(bytes,4);

        if(data==null){
            data = new CommonWarningFlg();
        }

        for(Field field: CommonWarningFlg.class.getDeclaredFields()){
            Position position = Position.getPositionByFieldName(field.getName());
            field.setAccessible(true);
            field.set(data, getShortValueFromBytes(bytes,position.getPosition()));
        }

        return data;

    }

    private static short getShortValueFromBytes(byte[] bytes,int position ) throws Exception {
        return getShortValueFromBytes(bytes, position/8,position% 8 );
    }

    private static short getShortValueFromBytes(byte[] bytes,int byteIndex,int position ) throws Exception {
        return (short) ByteUtil.getValueFromByte(bytes[byteIndex],position);
    }

}
