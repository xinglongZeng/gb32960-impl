package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProbeTemperaturesTest {


    public static ProbeTemperatures initData(){
        ProbeTemperatures data = new ProbeTemperatures();
        data.setTemperature(new GbValue<>((short)120));
        return data;
    }

    @Test
    public void parse() throws Exception {
        ProbeTemperatures initData = initData();
        byte[] bytes = initData.covert();
        byte[] bytes1 = ByteConvertUtil.covertClassForUpload(initData);
        Assertions.assertTrue(ByteUtil.compareByteArray(bytes,bytes1));
        ProbeTemperatures newUnit = ByteConvertUtil.parseByteArrayToObj(bytes, initData.getClass());
        Assertions.assertEquals(initData,newUnit);
    }

}