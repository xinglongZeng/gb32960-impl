package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DriverMotorDataTest {

    public static DriverMotorData initData(){
        DriverMotorData data = new DriverMotorData();
        data.setDriveMotorCounter(new GbValue<>((short)2));
        List<GbDriverMoterInfo> list = new ArrayList<>();
        list.add(GbDriverMoterInfoTest.initData());
        GbDriverMoterInfo info2 = GbDriverMoterInfoTest.initData();
        info2.setSeq(new GbValue<>((short)2));
        list.add(info2);
        data.setGbDriverMoterInfoList(list);
        return data;
    }


    @Test
    public void testParse() throws Exception {
        DriverMotorData initData = initData();
        byte[] bytes = initData.covert();
        byte[] bytes1 = ByteConvertUtil.covertClassForUpload(initData);
        Assertions.assertTrue(ByteUtil.compareByteArray(bytes,bytes1));
        DriverMotorData newUnit = ByteConvertUtil.parseByteArrayToObj(bytes, initData.getClass());
        Assertions.assertEquals(initData,newUnit);
    }



}