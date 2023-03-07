package com.zxl.gb;


import com.zxl.util.ByteConvertUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class VehicleLogOutUnitTest {


    public static VehicleLogOutUnit initData(){
        VehicleLogOutUnit unit = new VehicleLogOutUnit();
        unit.setTime(new Date());
        unit.setLogoutSeq(new GbValue<>(1));
        return unit;
    }


    @Test
    public void parse() throws Exception {
        VehicleLogOutUnit unit = initData();
        byte[] bytes = ByteConvertUtil.covertClassForUpload(unit);
        VehicleLogOutUnit newUnit = ByteConvertUtil.parseByteArrayToObj(bytes, unit.getClass());;
        Assertions.assertEquals(unit,newUnit);
    }


}