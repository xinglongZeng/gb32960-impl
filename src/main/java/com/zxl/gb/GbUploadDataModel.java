package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 国标数据上报的实体模型
 * @author zxl
 */
@Data
public class GbUploadDataModel implements CovertByteArray{


    /**
     * 起始符，2个字节
     */
    private String startFlg="##";


    /**
     * 命令单元。2个字节
     */

    private GbCommandUnit commandUnit;


    /**
     * 唯一识别码 (传输车辆数据时应使用车辆的vin ;  传输其他数据应自定义唯一编码）。 17个字节
     */
    private String uniqueKey;

    /**
     * 加密方式
     */
    private EncryptWay encryptWay;


    /**
     * 数据单元
     */
    private GbDataUnit dataUnit;



    /**
     * 解析字节数据为GbUploadDataModel
     * @param bytes 字节数据
     * @return GbUploadDataModel
     */
    public static GbUploadDataModel parseBytes(byte[] bytes) throws Exception {
        GbUploadDataModel dataModel = new GbUploadDataModel();
        // 0-1 字节，起始字符
        dataModel.setStartFlg(parseStartFlg( ByteUtil.subBytes(bytes,0,1) ));
        //  2-3字节，命令单元
        dataModel.setCommandUnit(GbCommandUnit.parseBytes(ByteUtil.subBytes(bytes,2,3) ));
        //  4-20字节，唯一识别码
        dataModel.setUniqueKey(parseUniqueKey(ByteUtil.subBytes(bytes,4,20) ));
        // 21字节，数据单元加密方式
        dataModel.setEncryptWay(EncryptWay.getEnumByBytes(bytes[21]));
        // 22-23字节，数据单元长度
        int dataUnitLength = ByteUtil.convertByteArrayToInt(ByteUtil.subBytes(bytes,22,23) );
        // 24 至 24+dataUnitLength字节，数据单元
        if(dataUnitLength !=0 ){
            dataModel.setDataUnit( parseGbDataUnit(ByteUtil.subBytes(bytes,24,23+dataUnitLength), dataModel.getCommandUnit()) );
        }

        // 比对校验校验码
        byte[] sub = ByteUtil.subBytes(bytes, 0, bytes.length - 2);
        byte bccResult = getBccResult(sub, 2);

        if(bccResult != bytes[bytes.length-1]){
            throw new Exception("bcc校验码验证不通过!");
        }

        return dataModel;
    }





    private static String parseStartFlg(byte[] bytes) throws Exception {
        if(bytes.length != 2){
            throw new Exception("解析起始字符错误！字节长度必须为2！");
        }

        return new String(bytes, UTF_8);
    }


    private static String parseUniqueKey(byte[] bytes) throws Exception {
        if(bytes.length != 17){
            throw new Exception("解析唯一识别码错误,字节长度必须为17！");
        }

        return ByteUtil.parseBytesToString(bytes);
    }


   private static GbDataUnit parseGbDataUnit(byte[] bytes, GbCommandUnit commandUnit) throws Exception {
        // todo:
       // 应答标识是命令
       if(bytes.length==0){
           throw new Exception("数据单元字节长度位0!");
       }

       if(!commandUnit.getReplyFlg().equals(ReplyFlgEnum.COMMAND )){
           throw new Exception("数据单元实体化失败，应答标识为非命令! replyFlg："+commandUnit.getReplyFlg());
       }

       switch (commandUnit.getCommandFlg()){
           // 车辆登入
           case VEHICLE_LOGIN:
               return VehicleLoginUnit.parseBytes(bytes);
           //  实时信息上报
           case REAL_TIME_DATA:
               return RealTimeDataUnit.parseBytes(bytes,null);
           //    补发信息上报
//           case REISSUE_DATA:
//               return null;
           //   车辆登出
           case VEHICLE_LOGOUT:
               return ByteConvertUtil.parseByteArrayToObj(bytes,VehicleLogOutUnit.class);
           // todo:    平台登入
           case PLATFORM_LOGIN:
               return  ByteConvertUtil.parseByteArrayToObj(bytes,PlatformLoginUnit.class);
           // todo:    平台登出
           case PLATFORM_LOGOUT:
               return ByteConvertUtil.parseByteArrayToObj(bytes,PlatformLogoutUnit.class);
           // todo: 预留
           default:
               throw new Exception("不支持的命令标识! "+commandUnit.getCommandFlg());

       }

   }


    @Override
    public byte[] covert() throws Exception {

        byte[] bytes = convertBytesWithOutBccResult();

        byte[] result = new byte[bytes.length+1];

        // 校验码
        byte bccResult = getBccResult(bytes, 2);


        System.arraycopy(bytes,0,result,0,bytes.length);

        result[result.length-1] = bccResult;

        return result;
    }


    /**
     * 将实体对象转换为没有bcc校验码的字节数组
     * @return
     */
    private byte[] convertBytesWithOutBccResult() throws Exception {
        List<byte[]> byteList = new ArrayList<>();

        //  设置起始符
        byteList.add( this.getStartFlg().getBytes(UTF_8) );


        // 命令单元
        byteList.add( this.getCommandUnit().covert());

        // 唯一识别码
        byteList.add(ByteConvertUtil.fillStringBytes(17,this.getUniqueKey().getBytes(UTF_8)) );

        // 数据加密方式
        byteList.add(this.getEncryptWay().enum2Bytes() );

        // 设置处理数据单元和数据单元长度
        byteList.add(getDataUnitBytesAndDataLength());


        return ByteConvertUtil.byteList2Array(byteList);
    }




    /**
     * 获取数据单元长度和数据单元的字节数组
     * @return
     */
    private byte[] getDataUnitBytesAndDataLength() throws Exception {
        List<byte[]> list  = new ArrayList<>();
        int DATA_UNIT_MAX_LEN=65531;

        byte[] dataUnitBytes = dataUnit2ByteArray(this.dataUnit, this.encryptWay);

        // 数据单元长度是数据单元的总字节数，有效值范围0~65531
        if (dataUnitBytes == null ) {
            throw new Exception( "数组单元转换的字节数组为null");
        }

        int length = dataUnitBytes.length;

        if (length > DATA_UNIT_MAX_LEN) {
            throw new Exception("数据单元总字节数超过限制！最大值:" + DATA_UNIT_MAX_LEN + ", 实际值:" + length);
        }

        // 数据单元长度
        list.add(ByteUtil.shortToBytes((short) length));

        // 数据单元
        list.add(dataUnitBytes);

        return ByteConvertUtil.byteList2Array(list);
    }



    /**
     * 数据单元转为字节数组
     *
     * @param dataUnit   数据单元
     * @param encryptWay 加密方式
     * @return
     */
    private static byte[] dataUnit2ByteArray(GbDataUnit dataUnit, EncryptWay encryptWay) throws Exception {

        // 非加密处理
        byte[] bytes = dataUnit2ByteArrayWithOutEncryptWay(dataUnit);

        switch (encryptWay) {
            case NOT_ENCRYPT:
                return bytes;
            case RSA:
                // rsa加密
                return encryptByRsa(bytes);
            case AES128:
                // AES128加密
                return encryptByAes128(bytes);
            case INVALID:
                throw new Exception( "数据加密方式为<无效>！无法进行处理");
            case EXCEPTION:
                throw new Exception( "数据加密方式为<异常>！无法进行处理");
            default:
                throw new Exception( "暂不支持的数据加密方式！encryptWay:" + encryptWay);
        }
    }

    /**
     * rsa加密
     *
     * @param bytes
     * @return
     */
    private static byte[] encryptByRsa(byte[] bytes) throws Exception {
        // todo: 还未接收到相关的密钥
        throw new Exception( "暂不支持的数据加密方式:RSA");
    }


    /**
     * AES128加密
     *
     * @param bytes
     * @return
     */
    private static byte[] encryptByAes128(byte[] bytes) throws Exception {
        // todo: 还未接收到相关的密钥
        throw new Exception( "暂不支持的数据加密方式:AES128");
    }


    /**
     * todo:
     *
     * @param dataUnit
     * @return
     */
    private static byte[] dataUnit2ByteArrayWithOutEncryptWay(GbDataUnit dataUnit) throws Exception {
        return dataUnit.transferByteArray();
    }



    /**
     *        获取校验码，采用BCC校验法。从命令单元第一个字节（index:2）开始，同后一个字节进行异或，直到校验码前一字节为止，校验码占用1个字节。
     *        当数据单元存在加密时，应先加密后校验，先校验后解密
     * @param startIndex 参与计算的起始字节索引位置
     * @param bytes 进行计算校验码的数组(bytes本身不包含校验码的字节)
     * @return byte
     * @throws Exception
     */
    private static byte getBccResult(byte[] bytes, int startIndex ) throws Exception {
        if (bytes.length <= startIndex + 1) {
            throw new Exception("bytes的长度:"+bytes.length+",必须超过 startIndex:" + startIndex);
        }

        byte bccResult = bytes[startIndex];
        for (int i = startIndex + 1; i < bytes.length-1; i++) {
            bccResult = (byte) (bccResult ^ bytes[i]);
        }
        return bccResult;
    }

}
