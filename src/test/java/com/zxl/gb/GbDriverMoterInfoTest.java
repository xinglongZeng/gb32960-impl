package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


class GbDriverMoterInfoTest {



    public static GbDriverMoterInfo initData(){
        GbDriverMoterInfo info = new GbDriverMoterInfo();
        info.setSeq(new GbValue<>((short)1));
        info.setStateEnum(GbDriverMoterStateEnum.READY);
        info.setControlTemperature(new GbValue<>((short)-40));
        info.setRotateSpeed(new GbValue<>(2000));
        info.setTorque(new GbValue<>(1000.9d));
        info.setTemperature(new GbValue<>((short)10));
        info.setInputVoltage(new GbValue<>(1000.5d));
        info.setMlElectricity(new GbValue<>(777.3d));
        return info;
    }



    @Test
    public void testParse() throws Exception {
        GbDriverMoterInfo initData = initData();
        byte[] bytes = initData.covert();
        byte[] bytes1 = ByteConvertUtil.covertClassForUpload(initData);
        Assertions.assertTrue(ByteUtil.compareByteArray(bytes,bytes1));
        List<CovertConfig> covertConfigList=ByteConvertUtil.getCovertConfigList(initData.getClass());
        GbDriverMoterInfo newUnit = (GbDriverMoterInfo) ByteConvertUtil.convertBytesForDownload(bytes, covertConfigList);
        Assertions.assertEquals(initData,newUnit);

    }


}