package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class VehicleLoginUnitTest {


    public static VehicleLoginUnit initData(){
        VehicleLoginUnit unit = new VehicleLoginUnit();
        unit.setTime(new Date());
        unit.setLoginSeq(new GbValue<>(200));
        unit.setIccId(new GbValue<>("1234567"));
        unit.setRechargeSubSysNum(new GbValue<>((short)2));
        unit.setRechargeSysCodeLen(new GbValue<>((short)5));

        List<String> list= new ArrayList<>();
        list.add("12345");
        list.add("12346");

        unit.setRechargeSysCodeList(list);

        return unit;

    }



    @Test
    public void testCovertFieldForUpload() throws Exception {
        VehicleLoginUnit unit = initData();
        CovertConfig config = ByteConvertUtil.getCovertConfig(unit.getClass(), "time");
        byte[] bytes = ByteConvertUtil.covertFieldForUpload(config, unit.getTime());
        assert bytes.length==6;

        config = ByteConvertUtil.getCovertConfig(unit.getClass(), "loginSeq");
        bytes = ByteConvertUtil.covertFieldForUpload(config, unit.getLoginSeq());
        assert bytes.length==2;

        config = ByteConvertUtil.getCovertConfig(unit.getClass(), "iccId");
        bytes = ByteConvertUtil.covertFieldForUpload(config, unit.getIccId());
        assert bytes.length==20;

        config = ByteConvertUtil.getCovertConfig(unit.getClass(), "rechargeSubSysNum");
        bytes = ByteConvertUtil.covertFieldForUpload(config, unit.getRechargeSubSysNum());
        assert bytes.length==1;

        config = ByteConvertUtil.getCovertConfig(unit.getClass(), "rechargeSysCodeLen");
        bytes = ByteConvertUtil.covertFieldForUpload(config, unit.getRechargeSysCodeLen());
        assert bytes.length==1;

        config = ByteConvertUtil.getCovertConfig(unit.getClass(), "rechargeSysCodeList");
        bytes = (byte[]) ByteConvertUtil.executeSpecialMethodForUpload(unit,config.getUploadMethodName());
        assert bytes.length == unit.getRechargeSubSysNum().getValue() * unit.getRechargeSysCodeLen().getValue()  ;


    }



    @Test
    public void testParseToBytes() throws Exception {
        VehicleLoginUnit unit = initData();
        byte[] bytes = ByteConvertUtil.covertClassForUpload(unit);
        VehicleLoginUnit newUnit = VehicleLoginUnit.parseBytes(bytes);
        Assertions.assertEquals(unit,newUnit);

        VehicleLoginUnit unit3 = ByteConvertUtil.parseByteArrayToObj(bytes, unit.getClass());
        Assertions.assertEquals(unit,unit3);
    }




}