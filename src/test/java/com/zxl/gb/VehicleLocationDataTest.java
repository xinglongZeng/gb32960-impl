package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VehicleLocationDataTest {


    public static VehicleLocationData initData(){
        VehicleLocationData state = new VehicleLocationData();
        state.setLocationState(VehicleLocationStateTest.initData());
        state.setLatitude(new GbValue<>(120.123456d));
        state.setLongitude(new GbValue<>(120.123456d));
        return state;
    }


    @Test
    public void testParse() throws Exception {
        VehicleLocationData initData = initData();
        byte[] bytes = initData.covert();
        byte[] bytes1 = ByteConvertUtil.covertClassForUpload(initData);
        Assertions.assertTrue(ByteUtil.compareByteArray(bytes,bytes1));
        VehicleLocationData newUnit = ByteConvertUtil.parseByteArrayToObj(bytes, initData.getClass());
        Assertions.assertEquals(initData,newUnit);
    }
}