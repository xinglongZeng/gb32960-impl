package com.zxl.gb;


import com.zxl.util.ByteConvertUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class PlatformLogoutUnitTest {



    public static PlatformLogoutUnit initData(){
        PlatformLogoutUnit unit = new PlatformLogoutUnit();
        unit.setTime(new Date());
        unit.setLogoutSeq(new GbValue<>(1));
        return unit;
    }


    @Test
    public void parse() throws Exception {
        PlatformLogoutUnit unit = initData();
        byte[] bytes = ByteConvertUtil.covertClassForUpload(unit);
        PlatformLogoutUnit newUnit =  ByteConvertUtil.parseByteArrayToObj(bytes,unit.getClass() );
        Assertions.assertEquals(unit,newUnit);
    }

}