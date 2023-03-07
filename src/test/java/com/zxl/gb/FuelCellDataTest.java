package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class FuelCellDataTest {


    public static FuelCellData initData(){
        FuelCellData data = new FuelCellData();
        data.setFuelVolTag(new GbValue<>(1000.1d));
        data.setFuelCurrent(new GbValue<>(1000.1d));
        data.setFuelConsumption(new GbValue<>(500.19d));
        data.setFuelCellTemperaturesProbeNum(new GbValue<>(2));

        List<ProbeTemperatures> list = new ArrayList<>();
        ProbeTemperatures temperatures = ProbeTemperaturesTest.initData();
        list.add(temperatures);
        ProbeTemperatures temperatures2 =ProbeTemperaturesTest.initData();
        temperatures2.setTemperature(new GbValue<>((short)50));
        list.add(temperatures2);

        data.setProbeTemperatureList(list);
        data.setHydrogenSystemMaxTemperature(new GbValue<>(199.9d));
        data.setProbeIndexOfHydrogenSystemMaxTemperature(new GbValue<>((short)1));
        data.setHydrogenMaxConcentration(new GbValue<>(20000));
        data.setSensorIndexOfHydrogenMaxConcentration(new GbValue<>((short)10));
        data.setHydrogenMaxPressure(new GbValue<>(99.9d));
        data.setSensorIndexOfHydrogenMaxPressure(new GbValue<>((short)1));
        data.setEvDcSts(DcStateEnum.WORK);

        return data;
    }


    @Test
    public void parse() throws Exception {
        FuelCellData initData = initData();
        byte[] bytes = initData.covert();
        byte[] bytes1 = ByteConvertUtil.covertClassForUpload(initData);
        Assertions.assertTrue(ByteUtil.compareByteArray(bytes,bytes1));
        FuelCellData newUnit = ByteConvertUtil.parseByteArrayToObj(bytes, initData.getClass());
        Assertions.assertEquals(initData,newUnit);
    }

}