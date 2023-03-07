package com.zxl.gb;


import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EngineDataTest {


    public static EngineData initData(){
        EngineData data = new EngineData();
        data.setEngineSts(EngineStateEnum.ON);
        data.setCrankShaftSpeed(new GbValue<>(30000));
        data.setFuelCellConsumptionRate(new GbValue<>(300.25d));
        return data;
    }


    @Test
    public void testParse() throws Exception {
        EngineData initData = initData();
        byte[] bytes = initData.covert();
        byte[] bytes1 = ByteConvertUtil.covertClassForUpload(initData);
        Assertions.assertTrue(ByteUtil.compareByteArray(bytes,bytes1));
        EngineData newUnit = ByteConvertUtil.parseByteArrayToObj(bytes, initData.getClass());
        Assertions.assertEquals(initData,newUnit);

    }


}