package com.zxl.gb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class GbUploadDataModelTest {



    private GbUploadDataModel initData() {


        // 命令单元
        GbCommandUnit commandUnit = GbCommandUnitTest.initData();
        commandUnit.setCommandFlg(CommandFlgEnum.VEHICLE_LOGIN);


        // 车辆登入
        VehicleLoginUnit vehicleLoginUnit = VehicleLoginUnitTest.initData();

        // 唯一识别码
        String uniqueKey="1111111111";


        GbUploadDataModel dataModel = new GbUploadDataModel();
        dataModel.setCommandUnit(commandUnit);
        dataModel.setUniqueKey(uniqueKey);
        dataModel.setEncryptWay(EncryptWay.NOT_ENCRYPT);
        dataModel.setDataUnit(vehicleLoginUnit);


        return dataModel;

    }

    /**
     * 测试实体对象转换为字节数据
     */
    @Test
    public void testObj2Bytes() throws Exception {

        // 车辆登入
        GbUploadDataModel dataModel = initData();
        byte[] bytes = dataModel.covert();
        GbUploadDataModel newData = GbUploadDataModel.parseBytes(bytes);
        Assertions.assertEquals(dataModel,newData);

        // 实时信息上报
        GbCommandUnit commandUnit = dataModel.getCommandUnit();
        commandUnit.setCommandFlg(CommandFlgEnum.REAL_TIME_DATA);
        dataModel.setDataUnit(RealTimeDataUnitTest.initData());
        bytes = dataModel.covert();
        newData = GbUploadDataModel.parseBytes(bytes);
        Assertions.assertEquals(dataModel,newData);

        // 车辆登出
        commandUnit = dataModel.getCommandUnit();
        commandUnit.setCommandFlg(CommandFlgEnum.VEHICLE_LOGOUT);
        dataModel.setDataUnit(VehicleLogOutUnitTest.initData());
        bytes = dataModel.covert();
        newData = GbUploadDataModel.parseBytes(bytes);
        Assertions.assertEquals(dataModel,newData);

        // 平台登入
        commandUnit = dataModel.getCommandUnit();
        commandUnit.setCommandFlg(CommandFlgEnum.PLATFORM_LOGIN);
        dataModel.setDataUnit(PlatformLoginUnitTest.initData());
        bytes = dataModel.covert();
        newData = GbUploadDataModel.parseBytes(bytes);
        Assertions.assertEquals(dataModel,newData);

        // 平台登出
        commandUnit = dataModel.getCommandUnit();
        commandUnit.setCommandFlg(CommandFlgEnum.PLATFORM_LOGOUT);
        dataModel.setDataUnit(PlatformLogoutUnitTest.initData());
        bytes = dataModel.covert();
        newData = GbUploadDataModel.parseBytes(bytes);
        Assertions.assertEquals(dataModel,newData);

    }



}