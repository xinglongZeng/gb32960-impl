package com.zxl.gb;


import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExtremumDataTest {



    public static ExtremumData initData(){
        ExtremumData data = new ExtremumData();
        data.setHighVolPowerBattSerial(new GbValue<>((short)1));
        data.setHighVolSingleBattSerial(new GbValue<>((short)1));
        data.setSingleBattHighVolValue(new GbValue<>(10.002d));
        data.setLowVolPowerBattSerial(new GbValue<>((short)2));
        data.setLowVolSingleBattSerial(new GbValue<>((short)2));
        data.setSingleBattLowVolValue(new GbValue<>(2.123d));
        data.setHighTempPowerBattSerial(new GbValue<>((short)1));
        data.setHighTempRobeSerial(new GbValue<>((short)1));
        data.setHighTempValue(new GbValue<>((short)-10));
        data.setLowTempPowerBattSerial(new GbValue<>((short)1));
        data.setLowTempRobeSerial(new GbValue<>((short)100));
        data.setLowTempValue(new GbValue<>((short)-10));

        return data;
    }


    @Test
    public void parse() throws Exception {
        ExtremumData initData = initData();
        byte[] bytes = initData.covert();
        byte[] bytes1 = ByteConvertUtil.covertClassForUpload(initData);
        Assertions.assertTrue(ByteUtil.compareByteArray(bytes,bytes1));
        ExtremumData newUnit = ByteConvertUtil.parseByteArrayToObj(bytes, initData.getClass());
        Assertions.assertEquals(initData,newUnit);
    }


}