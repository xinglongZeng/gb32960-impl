package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class WholeVehicleDataTest {

    public static  WholeVehicleData initData(){
        // 整车数据
        WholeVehicleData wholeVehicleData = new WholeVehicleData();
        wholeVehicleData.setGbVehicleState(GbVehicleState.STARING);
        wholeVehicleData.setGbRechargeState(GbRechargeState.RECHARGE_RUNNING);
        wholeVehicleData.setGbRunningMode(GbRunningMode.ELETRIC);
        wholeVehicleData.setSpeed(new GbValue<>(new Double("100.5")));
        wholeVehicleData.setTotalMileage(new GbValue<>(new Double("1005.7")));
        wholeVehicleData.setTotalVoltage(new GbValue<>(new Double("500.2")));
        wholeVehicleData.setTotalElectricity(new GbValue<>(new Double("500.3")));
        wholeVehicleData.setSoc(new GbValue<>((short)20));
        wholeVehicleData.setDcStateEnum(DcStateEnum.WORK);
        wholeVehicleData.setGbGears(GearsEnum.GEAR_3);
        wholeVehicleData.setInsulationResistance(new GbValue<>(20000));

        return wholeVehicleData;
    }


    @Test
    public void testParse() throws Exception {
        WholeVehicleData initData = initData();
        byte[] bytes = initData.covert();
        byte[] bytes1 = ByteConvertUtil.covertClassForUpload(initData);
        Assertions.assertTrue(ByteUtil.compareByteArray(bytes,bytes1));
        WholeVehicleData newUnit = ByteConvertUtil.parseByteArrayToObj(bytes, initData.getClass());
        Assertions.assertEquals(initData,newUnit);

    }

}