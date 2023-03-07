package com.zxl.gb;


import com.zxl.util.ByteConvertUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class PlatformLoginUnitTest {


    public static PlatformLoginUnit initData(){
        PlatformLoginUnit unit = new PlatformLoginUnit();
        unit.setTime(new Date());
        unit.setLoginSeq(new GbValue<>(1));
        unit.setLoginAccount(new GbValue<>("test"));
        unit.setLoginPwd(new GbValue<>("1234567"));
        return unit;
    }

    @Test
    public void parse() throws Exception {
        PlatformLoginUnit unit = initData();
        byte[] bytes = ByteConvertUtil.covertClassForUpload(unit);
        PlatformLoginUnit newUnit = ByteConvertUtil.parseByteArrayToObj(bytes, unit.getClass());;
        Assertions.assertEquals(unit,newUnit);
    }


}