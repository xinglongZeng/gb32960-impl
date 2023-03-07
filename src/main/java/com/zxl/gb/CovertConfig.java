package com.zxl.gb;

import lombok.Getter;

import java.math.BigDecimal;


@Getter
public enum CovertConfig {

    /**
     *    车辆登入
     */

    VehicleLoginUnit_time(VehicleLoginUnit.class,"time",0,InputValueTypeEnum.Date,null,null,true,null,null,null,null,6,null,null,null,DataFlowEnum.UPLOAD,"车辆登入采集时间不能为空",0,null,null),
    VehicleLoginUnit_loginSeq(VehicleLoginUnit.class,"loginSeq",1,InputValueTypeEnum.Integer,null,null,true,new BigDecimal("65531"),new BigDecimal("1"),null,null,2,null,null,null,DataFlowEnum.UPLOAD,"车辆登入流水号不能为空",0,null,null),
    VehicleLoginUnit_iccId(VehicleLoginUnit.class,"iccId",2,InputValueTypeEnum.String,null,null,true,null,null,null,null,20,null,null,null,DataFlowEnum.UPLOAD,"sim卡的iccid不能为空",0,null,null),
    VehicleLoginUnit_rechargeSubSysNum(VehicleLoginUnit.class,"rechargeSubSysNum",3,InputValueTypeEnum.Short,null,null,true,new BigDecimal("250"),new BigDecimal("0"),null,null,1,null,null,null,DataFlowEnum.UPLOAD,"可充电储能子系统数不能为空",0,null,null),
    VehicleLoginUnit_rechargeSysCodeLen(VehicleLoginUnit.class,"rechargeSysCodeLen",4,InputValueTypeEnum.Short,null,null,true,new BigDecimal("50"),new BigDecimal("0"),null,null,1,null,null,null,DataFlowEnum.UPLOAD,"可充电储能系统编码长度不能为空",0,null,null),
    VehicleLoginUnit_rechargeSysCodeList(VehicleLoginUnit.class,"rechargeSysCodeList",5,InputValueTypeEnum.List,"rechargeSubSysNum","rechargeSysCodeLen",false,null,null,null,null,null,null,null,null,DataFlowEnum.UPLOAD,null,0,"com.nx.gb.VehicleLoginUnit#parseRechargeSysCodeListToBytes",null),


    /**
     * 车辆登出
     */
    VehicleLogOutUnit_time(VehicleLogOutUnit.class,"time",0,InputValueTypeEnum.Date,null,null,true,null,null,null,null,6,null,null,null,DataFlowEnum.UPLOAD,"车辆登出采集时间不能为空",0,null,null),
    VehicleLogOutUnit_logoutSeq(VehicleLogOutUnit.class,"logoutSeq",1,InputValueTypeEnum.Integer,null,null,true,new BigDecimal("65531"),new BigDecimal("1"),null,null,2,null,null,null,DataFlowEnum.UPLOAD,"车辆登出流水号不能为空",0,null,null),

    /**
     * 平台登入
     */
    PlatformLoginUnit_time(PlatformLoginUnit.class,"time",0,InputValueTypeEnum.Date,null,null,true,null,null,null,null,6,null,null,null,DataFlowEnum.UPLOAD,"平台登入采集时间不能为空",0,null,null),
    PlatformLoginUnit_loginSeq(PlatformLoginUnit.class,"loginSeq",1,InputValueTypeEnum.Integer,null,null,true,new BigDecimal("65531"),new BigDecimal("1"),null,null,2,null,null,null,DataFlowEnum.UPLOAD,"平台登入流水号不能为空",0,null,null),
    PlatformLoginUnit_loginAccount(PlatformLoginUnit.class,"loginAccount",2,InputValueTypeEnum.String,null,null,true,null,null,null,null,12,null,null,null,DataFlowEnum.UPLOAD,"平台登入用户名不能为空",0,null,null),
    PlatformLoginUnit_loginPwd(PlatformLoginUnit.class,"loginPwd",3,InputValueTypeEnum.String,null,null,true,null,null,null,null,20,null,null,null,DataFlowEnum.UPLOAD,"平台登入密码不能为空",0,null,null),
    PlatformLoginUnit_encryptWay(PlatformLoginUnit.class,"encryptWay",4,InputValueTypeEnum.Enum,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"平台登入加密规则不能为空",0,null,null),

    /**
     * 平台登出
     */
    PlatformLogoutUnit_time(PlatformLogoutUnit.class,"time",0,InputValueTypeEnum.Date,null,null,true,null,null,null,null,6,null,null,null,DataFlowEnum.UPLOAD,"平台登入采集时间不能为空",0,null,null),
    PlatformLogoutUnit_logoutSeq(PlatformLogoutUnit.class,"logoutSeq",1,InputValueTypeEnum.Integer,null,null,true,new BigDecimal("65531"),new BigDecimal("1"),null,null,2,null,null,null,DataFlowEnum.UPLOAD,"平台登出采集时间不能为空",0,null,null),

    /**
     * 实时数据上报-整车数据
     */
    WholeVehicleData_gbVehicleState(WholeVehicleData.class,"gbVehicleState",0,InputValueTypeEnum.Enum,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"车辆状态不能为空",0,null,null),
    WholeVehicleData_gbRechargeState(WholeVehicleData.class,"gbRechargeState",1,InputValueTypeEnum.Enum,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"充电状态不能为空",0,null,null),
    WholeVehicleData_gbRunningMode(WholeVehicleData.class,"gbRunningMode",2,InputValueTypeEnum.Enum,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"运行模式不能为空",0,null,null),
    WholeVehicleData_speed(WholeVehicleData.class,"speed",3,InputValueTypeEnum.Double,null,null,true,new BigDecimal("220"),new BigDecimal("0"),(long)0xFFFE,(long)0xFFFF,2,"s",null,"10",DataFlowEnum.UPLOAD,"车速不能为空",1,null,null),
    WholeVehicleData_totalMileage(WholeVehicleData.class,"totalMileage",4,InputValueTypeEnum.Double,null,null,true,new BigDecimal("999999.9"),new BigDecimal("0"),(long)0xFFFFFFFE,(long)0xFFFFFFFF,4,"s",null,"10",DataFlowEnum.UPLOAD,"总里程不能为空",1,null,null),
    WholeVehicleData_totalVoltage(WholeVehicleData.class,"totalVoltage",5,InputValueTypeEnum.Double,null,null,true,new BigDecimal("1000"),new BigDecimal("0"),(long)0xFFFE,(long)0xFFFF,2,"s",null,"10",DataFlowEnum.UPLOAD,"总电压不能为空",1,null,null),
    WholeVehicleData_totalElectricity(WholeVehicleData.class,"totalElectricity",6,InputValueTypeEnum.Double,null,null,true,new BigDecimal("1000"),new BigDecimal("-1000"),(long)0xFFFE,(long)0xFFFF,2,"os","1000","10",DataFlowEnum.UPLOAD,"总电流不能为空",1,null,null),
    WholeVehicleData_soc(WholeVehicleData.class,"soc",7,InputValueTypeEnum.Short,null,null,true,new BigDecimal("100"),new BigDecimal("0"),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"SOC不能为空",1,null,null),
    WholeVehicleData_dcStateEnum(WholeVehicleData.class,"dcStateEnum",8,InputValueTypeEnum.Enum,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"DC-DC状态不能为空",0,null,null),
    WholeVehicleData_gbGears(WholeVehicleData.class,"gbGears",9,InputValueTypeEnum.Enum,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"挡位不能为空",0,null,null),
    WholeVehicleData_insulationResistance(WholeVehicleData.class,"insulationResistance",10,InputValueTypeEnum.Integer,null,null,true,new BigDecimal("60000"),new BigDecimal("0"),null,null,2,null,null,null,DataFlowEnum.UPLOAD,"绝缘电阻不能为空",0,null,null),
    WholeVehicleData_reserved(WholeVehicleData.class,"reserved",11,InputValueTypeEnum.Object,null,null,false,null,null,null,null,2,null,null,null,DataFlowEnum.UPLOAD,null,0,"com.nx.gb.WholeVehicleData#parseReservedToBytes","com.nx.gb.WholeVehicleData#parseBytesToReserved"),

    /**
     * 发动机数据
     */
    EngineData_engineSts(EngineData.class,"engineSts",0,InputValueTypeEnum.Enum,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"发动机状态不能为空",0,null,null),
    EngineData_crankShaftSpeed(EngineData.class,"crankShaftSpeed",1,InputValueTypeEnum.Integer,null,null,true,new BigDecimal("60000"),new BigDecimal("0"),(long)0xFFFE,(long)0xFFFF,2,null,null,null,DataFlowEnum.UPLOAD,"曲轴转速不能为空",0,null,null),
    EngineData_fuelCellConsumptionRate(EngineData.class,"fuelCellConsumptionRate",2,InputValueTypeEnum.Double,null,null,true,new BigDecimal("600"),new BigDecimal("0"),(long)0xFFFE,(long)0xFFFF,2,"s",null,"100",DataFlowEnum.UPLOAD,"燃料消耗率不能为空",2,null,null),


    /**
     * 驱动电机数据
     */
    DriverMotorData_driveMotorCounter(DriverMotorData.class,"driveMotorCounter",0,InputValueTypeEnum.Short,null,null,true,new BigDecimal(253),new BigDecimal(1),null,null,1,null,null,null,DataFlowEnum.UPLOAD,"驱动电机个数不能为空",0,null,null),
    DriverMotorData_gbDriverMoterInfoList(DriverMotorData.class,"gbDriverMoterInfoList",1,InputValueTypeEnum.List,"driveMotorCounter",null,false,null,null,null,null,null,null,null,null,DataFlowEnum.UPLOAD,"驱动电机信息列表不能为空",0,null,null),

    /**
     * 驱动电机总成信息
     */
    GbDriverMoterInfo_driveMotorCounter(GbDriverMoterInfo.class,"seq",0,InputValueTypeEnum.Short,null,null,true,new BigDecimal(253),new BigDecimal(1),null,null,1,null,null,null,DataFlowEnum.UPLOAD,"驱动电机序号不能为空",0,null,null),
    GbDriverMoterInfo_stateEnum(GbDriverMoterInfo.class,"stateEnum",1,InputValueTypeEnum.Enum,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"驱动电机状态不能为空",0,null,null),
    GbDriverMoterInfo_controlTemperature(GbDriverMoterInfo.class,"controlTemperature",2,InputValueTypeEnum.Short,null,null,true,new BigDecimal(210),new BigDecimal(-40),(long)0xFE,(long)0xFF,1,"o","40",null,DataFlowEnum.UPLOAD,"驱动电机控制器温度不能为空",0,null,null),
    GbDriverMoterInfo_rotateSpeed(GbDriverMoterInfo.class,"rotateSpeed",3,InputValueTypeEnum.Integer,null,null,true,new BigDecimal(45531),new BigDecimal(-20000),(long)0xFFFE,(long)0xFFFF,2,"o","20000",null,DataFlowEnum.UPLOAD,"驱动电机转速不能为空",0,null,null),
    GbDriverMoterInfo_torque(GbDriverMoterInfo.class,"torque",4,InputValueTypeEnum.Double,null,null,true,new BigDecimal("4553.1"),new BigDecimal(-2000),(long)0xFFFE,(long)0xFFFF,2,"so","20000","10",DataFlowEnum.UPLOAD,"驱动电机转矩不能为空",1,null,null),
    GbDriverMoterInfo_temperature(GbDriverMoterInfo.class,"temperature",5,InputValueTypeEnum.Short,null,null,true,new BigDecimal(210),new BigDecimal(-40),(long)0xFE,(long)0xFF,1,"o","40",null,DataFlowEnum.UPLOAD,"驱动电机温度不能为空",0,null,null),
    GbDriverMoterInfo_inputVoltage(GbDriverMoterInfo.class,"inputVoltage",6,InputValueTypeEnum.Double,null,null,true,new BigDecimal(6000),new BigDecimal(0),(long)0xFFFE,(long)0xFFFF,2,"s",null,"10",DataFlowEnum.UPLOAD,"电机控制器输入电压不能为空",1,null,null),
    GbDriverMoterInfo_mlElectricity(GbDriverMoterInfo.class,"mlElectricity",7,InputValueTypeEnum.Double,null,null,true,new BigDecimal(1000),new BigDecimal(-1000),(long)0xFFFE,(long)0xFFFF,2,"so","1000","10",DataFlowEnum.UPLOAD,"电机控制器直流母线电流不能为空",1,null,null),


    /**
     * 极值数据
     */
    ExtremumData_driveMotorCounter(ExtremumData.class,"highVolPowerBattSerial",0,InputValueTypeEnum.Short,null,null,true,new BigDecimal(250),new BigDecimal(1),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"最高电压电池子系统号不能为空",0,null,null),
    ExtremumData_highVolSingleBattSerial(ExtremumData.class,"highVolSingleBattSerial",1,InputValueTypeEnum.Short,null,null,true,new BigDecimal(250),new BigDecimal(1),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"最高电压电池单体代号不能为空",0,null,null),
    ExtremumData_singleBattHighVolValue(ExtremumData.class,"singleBattHighVolValue",2,InputValueTypeEnum.Double,null,null,true,new BigDecimal(15),new BigDecimal(0),(long)0xFFFE,(long)0xFFFF,2,"s",null,"1000",DataFlowEnum.UPLOAD,"电池单体电压最高值不能为空",3,null,null),
    ExtremumData_lowVolPowerBattSerial(ExtremumData.class,"lowVolPowerBattSerial",3,InputValueTypeEnum.Short,null,null,true,new BigDecimal(250),new BigDecimal(1),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"最低电压电池子系统号不能为空",0,null,null),
    ExtremumData_lowVolSingleBattSerial(ExtremumData.class,"lowVolSingleBattSerial",4,InputValueTypeEnum.Short,null,null,true,new BigDecimal(250),new BigDecimal(1),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"最低电压电池单体代号不能为空",0,null,null),
    ExtremumData_singleBattLowVolValue(ExtremumData.class,"singleBattLowVolValue",5,InputValueTypeEnum.Double,null,null,true,new BigDecimal(15),new BigDecimal(1),(long)0xFFFE,(long)0xFFFF,2,"s",null,"1000",DataFlowEnum.UPLOAD,"电池单体电压最低值不能为空",3,null,null),
    ExtremumData_highTempPowerBattSerial(ExtremumData.class,"highTempPowerBattSerial",6,InputValueTypeEnum.Short,null,null,true,new BigDecimal(250),new BigDecimal(1),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"最高温度子系统号不能为空",0,null,null),
    ExtremumData_highTempRobeSerial(ExtremumData.class,"highTempRobeSerial",7,InputValueTypeEnum.Short,null,null,true,new BigDecimal(250),new BigDecimal(1),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"最高温度探针序号不能为空",0,null,null),
    ExtremumData_highTempValue(ExtremumData.class,"highTempValue",8,InputValueTypeEnum.Short,null,null,true,new BigDecimal(210),new BigDecimal(-40),(long)0xFE,(long)0xFF,1,"o","40",null,DataFlowEnum.UPLOAD,"最高温度值不能为空",0,null,null),
    ExtremumData_lowTempPowerBattSerial(ExtremumData.class,"lowTempPowerBattSerial",9,InputValueTypeEnum.Short,null,null,true,new BigDecimal(250),new BigDecimal(1),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"最低温度子系统号不能为空",0,null,null),
    ExtremumData_lowTempRobeSerial(ExtremumData.class,"lowTempRobeSerial",10,InputValueTypeEnum.Short,null,null,true,new BigDecimal(250),new BigDecimal(1),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"最低温度探针序号不能为空",0,null,null),
    ExtremumData_lowTempValue(ExtremumData.class,"lowTempValue",11,InputValueTypeEnum.Short,null,null,true,new BigDecimal(250),new BigDecimal(-40),(long)0xFE,(long)0xFF,1,"o","40",null,DataFlowEnum.UPLOAD,"最低温度值不能为空",0,null,null),


    /**
     * 燃料电池数据
     */
    FuelCellData_driveMotorCounter(FuelCellData.class,"fuelVolTag",0,InputValueTypeEnum.Double,null,null,true,new BigDecimal(2000),new BigDecimal(1),(long)0xFFFE,(long)0xFFFF,2,"s",null,"10",DataFlowEnum.UPLOAD,"燃料电池电压不能为空",1,null,null),
    FuelCellData_fuelCurrent(FuelCellData.class,"fuelCurrent",1,InputValueTypeEnum.Double,null,null,true,new BigDecimal(2000),new BigDecimal(1),(long)0xFFFE,(long)0xFFFF,2,"s",null,"10",DataFlowEnum.UPLOAD,"燃料电池电流不能为空",1,null,null),
    FuelCellData_fuelConsumption(FuelCellData.class,"fuelConsumption",2,InputValueTypeEnum.Double,null,null,true,new BigDecimal(600),new BigDecimal(0),(long)0xFFFE,(long)0xFFFF,2,"s",null,"100",DataFlowEnum.UPLOAD,"燃料消耗率不能为空",2,null,null),
    FuelCellData_fuelCellTemperaturesProbeNum(FuelCellData.class,"fuelCellTemperaturesProbeNum",3,InputValueTypeEnum.Integer,null,null,true,new BigDecimal(65531),new BigDecimal(0),(long)0xFFFE,(long)0xFFFF,2,null,null,"100",DataFlowEnum.UPLOAD,"燃料电池温度探针总数不能为空",0,null,null),
    FuelCellData_probeTemperatureList(FuelCellData.class,"probeTemperatureList",4,InputValueTypeEnum.List,"fuelCellTemperaturesProbeNum",null,false,null,null,null,null,null,null,null,"100",DataFlowEnum.UPLOAD,null,0,null,null),
    FuelCellData_hydrogenSystemMaxTemperature(FuelCellData.class,"hydrogenSystemMaxTemperature",5,InputValueTypeEnum.Double,null,null,true,new BigDecimal(200),new BigDecimal(-40),(long)0xFFFE,(long)0xFFFF,2,"os","40","10",DataFlowEnum.UPLOAD,"氢系统中最高温度不能为空",1,null,null),
    FuelCellData_probeIndexOfHydrogenSystemMaxTemperature(FuelCellData.class,"probeIndexOfHydrogenSystemMaxTemperature",6,InputValueTypeEnum.Short,null,null,true,new BigDecimal(252),new BigDecimal(1),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"氢系统中最高温度探针代号不能为空",0,null,null),
    FuelCellData_hydrogenMaxConcentration(FuelCellData.class,"hydrogenMaxConcentration",7,InputValueTypeEnum.Integer,null,null,true,new BigDecimal(50000),new BigDecimal(0),(long)0xFFFE,(long)0xFFFF,2,null,null,null,DataFlowEnum.UPLOAD,"氢气最高浓度不能为空",0,null,null),
    FuelCellData_sensorIndexOfHydrogenMaxConcentration(FuelCellData.class,"sensorIndexOfHydrogenMaxConcentration",8,InputValueTypeEnum.Short,null,null,true,new BigDecimal(252),new BigDecimal(1),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"氢气最高浓度传感器代号不能为空",0,null,null),
    FuelCellData_hydrogenMaxPressure(FuelCellData.class,"hydrogenMaxPressure",9,InputValueTypeEnum.Double,null,null,true,new BigDecimal(100),new BigDecimal(0),null,null,2,"s",null,"10",DataFlowEnum.UPLOAD,"氢气最高压力不能为空",1,null,null),
    FuelCellData_sensorIndexOfHydrogenMaxPressure(FuelCellData.class,"sensorIndexOfHydrogenMaxPressure",10,InputValueTypeEnum.Short,null,null,true,new BigDecimal(252),new BigDecimal(1),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"氢气最高压力传感器代号不能为空",0,null,null),
    FuelCellData_evDcSts(FuelCellData.class,"evDcSts",11,InputValueTypeEnum.Enum,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"高压DC/DC状态不能为空",0,null,null),


    /**
     * 探针温度
     */
    ProbeTemperatures_temperature(ProbeTemperatures.class,"temperature",0,InputValueTypeEnum.Short,null,null,true,new BigDecimal(200),new BigDecimal(-40),(long)0xFE,(long)0xFF,1,"o","40",null,DataFlowEnum.UPLOAD,"探针温度不能为空",0,null,null),


    /**
     * 车辆位置数据
     */
    VehicleLocationData_temperature(VehicleLocationData.class,"locationState",0,InputValueTypeEnum.Object,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"定位状态不能为null",0,"com.nx.gb.VehicleLocationData#parseLocationStateToBytes","com.nx.gb.VehicleLocationData#parseBytesToVehicleLocationState"),
    VehicleLocationData_longitude(VehicleLocationData.class,"longitude",1,InputValueTypeEnum.Double,null,null,true,new BigDecimal(180),new BigDecimal(0),null,null,4,"s",null,"1000000",DataFlowEnum.UPLOAD,"经度值不能为null",6,null,null),
    VehicleLocationData_latitude(VehicleLocationData.class,"latitude",2,InputValueTypeEnum.Double,null,null,true,new BigDecimal(180),new BigDecimal(0),null,null,4,"s",null,"1000000",DataFlowEnum.UPLOAD,"维度值不能为null",6,null,null),


    /**
     * 报警数据
     */
    WarningData_highAlarmLevel(WarningData.class,"highAlarmLevel",0,InputValueTypeEnum.Short,null,null,true,new BigDecimal(3),new BigDecimal(0),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"最高报警等级不能为空",0,null,null),
    WarningData_commonWarningFlg(WarningData.class,"commonWarningFlg",1,InputValueTypeEnum.Object,null,null,false,null,null,null,null,4,null,null,null,DataFlowEnum.UPLOAD,"通用报警标志不能为空",0,"com.nx.gb.WarningData#parseCommonWarningFlgToBytes","com.nx.gb.WarningData#parseBytesToCommonWarningFlg"),
    WarningData_energyStorageAlertCount(WarningData.class,"energyStorageAlertCount",2,InputValueTypeEnum.Short,null,null,true,new BigDecimal(252),new BigDecimal(0),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"可充电储能装置故障总数不能为空",0,null,null),
    WarningData_energyStorageAlertList(WarningData.class,"energyStorageAlertList",3,InputValueTypeEnum.List,"energyStorageAlertCount",null,false,null,null,null,null,null,null,null,null,DataFlowEnum.UPLOAD,null,0,null,null),
    WarningData_machineFaultCount(WarningData.class,"machineFaultCount",4,InputValueTypeEnum.Short,null,null,true,new BigDecimal(252),new BigDecimal(0),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"驱动电机故障总数不能为空",0,null,null),
    WarningData_machineFaultList(WarningData.class,"machineFaultList",5,InputValueTypeEnum.List,"machineFaultCount",null,false,null,null,null,null,null,null,null,null,DataFlowEnum.UPLOAD,null,0,null,null),
    WarningData_motorFaultCount(WarningData.class,"motorFaultCount",6,InputValueTypeEnum.Short,null,null,true,new BigDecimal(252),new BigDecimal(0),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"发动机故障总数不能为空",0,null,null),
    WarningData_motorFaultList(WarningData.class,"motorFaultList",7,InputValueTypeEnum.List,"motorFaultCount",null,false,null,null,null,null,null,null,null,null,DataFlowEnum.UPLOAD,null,0,null,null),
    WarningData_otherFaultCount(WarningData.class,"otherFaultCount",8,InputValueTypeEnum.Short,null,null,true,new BigDecimal(252),new BigDecimal(0),(long)0xFE,(long)0xFF,1,null,null,null,DataFlowEnum.UPLOAD,"其他故障总数不能为空",0,null,null),
    WarningData_otherFaultList(WarningData.class,"otherFaultList",9,InputValueTypeEnum.List,"otherFaultCount",null,false,null,null,null,null,null,null,null,null,DataFlowEnum.UPLOAD,null,0,null,null),


    /**
     * 故障码
     */
    FaultCodeData_code(FaultCodeData.class,"code",0,InputValueTypeEnum.String,null,null,true,null,null,null,null,4,null,null,null,DataFlowEnum.UPLOAD,"故障码不能为null",0,null,null),


    /**
     * 命令单元
     */
    GbCommandUnit_commandFlg(GbCommandUnit.class,"commandFlg",0,InputValueTypeEnum.Enum,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"命令标识不能为空",0,null,null),
    GbCommandUnit_replyFlg(GbCommandUnit.class,"replyFlg",1,InputValueTypeEnum.Enum,null,null,false,null,null,null,null,1,null,null,null,DataFlowEnum.UPLOAD,"应答标志不能为空",0,null,null),



    ;

    /**
     * 类名
     */
    private final Class clazz;

    /**
     * 字段名
     */
    private final String fieldName;

    /**
     * 字段的解析顺序（升序解析）
     */
    private final int fieldOrder;

    /**
     * GbValue包装的值类型
     */
    private final InputValueTypeEnum inputValueType;


    /**
     * 字段是否是GbVehicleState包装的类型
     */
    private final boolean packFlg;


    /**
     * 最大值限制
     */
    private final BigDecimal maxValue;

    /**
     * 最小值限制
     */
    private final BigDecimal minValue;

    /**
     * 异常值的字节表示 ,例如:0xFE
     */
    private final Long exceptionBytes;

    /**
     * 无效值的字节表示
     */
    private final Long invalidBytes;

    /**
     * 解析出的字节长度
     */
    private final Integer byteLength;


    /**
     * 偏移和缩放的执行顺序 （实体数据转换为字节时使用该顺序，字节转换为实体数据使用该顺序的相反顺序）
     */
    private final String sequence;

    /**
     * 实体转换为字节数据时的偏移量
     */
    private final String offset;

    /**
     * 实体转换为字节数据时的缩放量
     */
    private final String scale;

    /**
     * 非空校验的流程： 上传 、下行
     */
    private final DataFlowEnum  checkNotNull;

    /**
     * 值为空时的错误信息
     */
    private final String nullErrMsg;

    /**
     * 浮点数时有效的小数位数
     */
    private final Integer precision ;

    /**
     *   上行用-特殊方法名 (将字段解析为字节数组的所用的特殊方法名，格式: clazz#method)
     *
     */
    private final String  uploadMethodName;

    /**
     *  下行用-特殊方法名(将字节数组解析为实体字段所用的特殊方法名，格式: clazz#method)
     *
     */
    private final String  downloadMethodName;

    /**
     * inputValueType为List时，需要知道其元素个数，而listSizeFidldName就是指定元素个数的field的name
     */
    private final String listSizeFieldName;

    /**
     * inputValueType为List时，List所包装的类型的所需字节长度的字段名
     */
    private final String eleLengthFieldName;




    CovertConfig(Class clazz, String fieldName,int fieldOrder,InputValueTypeEnum inputValueType, String listSizeFieldName,String eleLengthFieldName,boolean packFlg, BigDecimal maxValue, BigDecimal minValue, Long exceptionBytes, Long invalidBytes, Integer byteLength, String sequence, String offset, String scale, DataFlowEnum checkNotNull, String nullErrMsg, Integer precision,String uploadMethodName ,String downloadMethodName) {
        this.clazz = clazz;
        this.fieldName = fieldName;
        this.fieldOrder = fieldOrder;
        this.inputValueType = inputValueType;
        this.packFlg = packFlg;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.exceptionBytes = exceptionBytes;
        this.invalidBytes = invalidBytes;
        this.byteLength = byteLength;
        this.sequence = sequence;
        this.offset = offset;
        this.scale = scale;
        this.checkNotNull = checkNotNull;
        this.nullErrMsg = nullErrMsg;
        this.precision = precision;
        this.uploadMethodName = uploadMethodName;
        this.downloadMethodName = downloadMethodName;
        this.listSizeFieldName = listSizeFieldName;
        this.eleLengthFieldName = eleLengthFieldName;
    }
}
