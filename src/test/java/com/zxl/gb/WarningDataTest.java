package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class WarningDataTest {

    public static WarningData initData(){
        WarningData data = new WarningData();
        data.setHighAlarmLevel(new GbValue<>((short)3));
        data.setCommonWarningFlg(CommonWarningFlgTest.initData());

        data.setEnergyStorageAlertCount(new GbValue<>((short)2));
        List<FaultCodeData> energyStorageAlertList  = new ArrayList<>();
        FaultCodeData code1=new FaultCodeData();
        code1.setCode(new GbValue<>("123"));
        FaultCodeData code2=new FaultCodeData();
        code2.setCode(new GbValue<>("1234"));
        energyStorageAlertList.add(code1);
        energyStorageAlertList.add(code2);
        data.setEnergyStorageAlertList(energyStorageAlertList);

        data.setMachineFaultCount(new GbValue<>((short)2));
        List<FaultCodeData> machineFaultList  = new ArrayList<>();
        FaultCodeData code3=new FaultCodeData();
        code3.setCode(new GbValue<>("123"));
        FaultCodeData code4=new FaultCodeData();
        code4.setCode(new GbValue<>("1234"));
        machineFaultList.add(code3);
        machineFaultList.add(code4);
        data.setMachineFaultList(machineFaultList);


        data.setMotorFaultCount(new GbValue<>((short)2));
        List<FaultCodeData> motorFaultList  = new ArrayList<>();
        FaultCodeData code5=new FaultCodeData();
        code5.setCode(new GbValue<>("123"));
        FaultCodeData code6=new FaultCodeData();
        code6.setCode(new GbValue<>("1234"));
        motorFaultList.add(code5);
        motorFaultList.add(code6);
        data.setMotorFaultList(motorFaultList);

        data.setOtherFaultCount(new GbValue<>((short)2));
        List<FaultCodeData> otherFaultList  = new ArrayList<>();
        FaultCodeData code7=new FaultCodeData();
        code7.setCode(new GbValue<>("123"));
        FaultCodeData code8=new FaultCodeData();
        code8.setCode(new GbValue<>("1234"));
        otherFaultList.add(code7);
        otherFaultList.add(code8);
        data.setOtherFaultList(otherFaultList);


        return data;
    }


    @Test
    public void testParse() throws Exception {
        WarningData initData = initData();
        byte[] bytes = initData.covert();
        byte[] bytes1 = ByteConvertUtil.covertClassForUpload(initData);
        Assertions.assertTrue(ByteUtil.compareByteArray(bytes,bytes1));
        WarningData newData = ByteConvertUtil.parseByteArrayToObj(bytes1, initData.getClass());
        Assertions.assertEquals(initData,newData);


    }


}