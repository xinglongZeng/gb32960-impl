package com.zxl.gb;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class RealTimeDataUnitTest {

    public static RealTimeDataUnit initData(){
        RealTimeDataUnit data = new RealTimeDataUnit();
        data.setTime(new Date());
        data.setWholeVehicleData(WholeVehicleDataTest.initData());
        data.setDriverMotorData(DriverMotorDataTest.initData());
        data.setFuelCellData(FuelCellDataTest.initData());
        data.setEngineData(EngineDataTest.initData());
        data.setVehicleLocationData(VehicleLocationDataTest.initData());
        data.setExtremumData(ExtremumDataTest.initData());
        data.setWarningData(WarningDataTest.initData());

        return data;
    }

    @Test
    public void testParse() throws Exception {
        RealTimeDataUnit initData = initData();
        byte[] bytes = initData.covert();
        RealTimeDataUnit newData = initData.parseBytes(bytes, null);
        Assertions.assertEquals(initData,newData);

    }



}