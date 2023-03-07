package com.zxl.util;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ByteUtil {

    public static final byte[] FILL_STRING_BYTE ="\0".getBytes(UTF_8);

    public static byte[] long2Bytes(long data) {
        return new byte[]{
                (byte) ((data >> 56) & 0xff),
                (byte) ((data >> 48) & 0xff),
                (byte) ((data >> 40) & 0xff),
                (byte) ((data >> 32) & 0xff),
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) ((data) & 0xff),
        };
    }




    public static long convertByteArrayToLong(byte[] data) throws Exception {
        if (data == null ) {
            return 0x0;
        }
        int v= 8-data.length;
        if(v > 0 ){
            data = fillByteArrayOnHead(data,v);
        }
        // ----------
        return (long)(
                        (0xff & data[0])  << 56 |
                        (0xff & data[1])  << 48 |
                        (0xff & data[2])  << 40 |
                        (0xff & data[3])  << 32 |
                        (0xff & data[4])  << 24 |
                        (0xff & data[5])  << 16 |
                        (0xff & data[6])  <<  8 |
                        (0xff & data[7])
        );
    }


    public static byte[] intToBytes(final int data) {
        return new byte[] {
                (byte)((data >> 24) & 0xff),
                (byte)((data >> 16) & 0xff),
                (byte)((data >> 8) & 0xff),
                (byte)((data) & 0xff),
        };
    }

    public static byte[] shortToBytes(final short data) {
        return new byte[] {
                (byte)((data >> 8) & 0xff),
                (byte)((data) & 0xff),
        };
    }


    public static int convertByteArrayToInt(byte[] data) throws Exception {
        if (data == null ) {
            return 0x0;
        }
        int v= 4-data.length;
        if(v > 0 ){
            data = fillByteArrayOnHead(data,v);
        }
        // ----------
        int i = (int)( // NOTE: type cast not necessary for int
                        (0xff & data[0])  << 24 |
                        (0xff & data[1])  << 16 |
                        (0xff & data[2])  <<  8 |
                        (0xff & data[3])
        );
        return i;
    }

    /**
     * 从头部开始扩容字节数组
     * @param bytes 要扩容的字节数组
     * @param fillLength 要扩容的长度
     * @return
     */
    public static byte[] fillByteArrayOnHead(byte[]  bytes, int fillLength) throws Exception {
        if(fillLength <=0){
            throw new Exception("fillLength必须大于0");
        }

        byte[] newData = new byte[bytes.length+fillLength];
        System.arraycopy(bytes,0,newData,newData.length-bytes.length,bytes.length);
        return newData;
    }


    /**
     *  设置字节b的index位的值为value
     * @param b  要进行操作的字节
     * @param index  要操作的字节中的位的索引。范围： 0~7
     * @param value  要设置的值， 0或1
     * @return  字节byte
     */
    public static byte setBitByIndexFromByte(byte b,int index , short value ) throws Exception {
        long l = commonSetBitByIndex(b, index, 0, 7, value);
        return (byte)l;
    }

    /**
     *  设置i的index位的值为value (大端字节序)
     * @param i  要进行操作的整数int
     * @param index  要操作的int的位的索引。范围： 0~31
     * @param value  要设置的值， 0或1
     * @return  字节byte
     */
    public static int setBitByIndexFromInt(int i,int index , short value ) throws Exception {
        long l = commonSetBitByIndex(i, index, 0, 31, value);
        return (int)l;
    }


    public static long commonSetBitByIndex(long i,long index ,long minIndex,long maxIndex, short value  ) throws Exception {
        if(index > maxIndex || index < minIndex ){
            throw new Exception("int类型可操作的位数索引只支持"+minIndex+"-"+maxIndex+"! index:"+index);
        }

        if(value > 1 || value < 0){
            throw new Exception("位设置只支持0或者1 ! value:"+value);
        }


        if(value == 1 ){

            // set a bit to 1
            i |= (1 << (maxIndex-index));

        }else {

            // set a bit to 0
            i &= ~(1 << (maxIndex-index));

        }

        return i;
    }


    /**
     * 从byte数组中截取从start开始，到end位置的子数组（包括end）
     * @param bytes
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public static byte[]   subBytes(byte[] bytes,int start,int end ) throws Exception {

        if(start<0){
            throw new Exception("起始索引值必须大于等于0!");
        }

        if(end < start ){
            throw new Exception("结束索引值必须大于等于起始索引值!");
        }

        int length = end - start +1;

        if(length > bytes.length){
            throw new Exception("子数组长度大于原始数组的长度!");
        }

        byte[] sub = new byte[length];

        if (end + 1 - start >= 0) {
            System.arraycopy(bytes, start, sub, 0, length);
        }

        return sub;
    }


    public static void checkOutRange(double v, double min, double max, String key) throws Exception {
        if(v<min || v > max){
            throw new Exception("超出"+key+"的填值范围 ["+min+","+max+"] ,实际值:"+v);
        }
    }


    public static void checkByteLength(byte[] bytes , int length,String key) throws Exception {
        if( bytes==null ||  bytes.length != length){
            throw new Exception(key+"的字节数组长度校验未通过! 要求长度:"+length);
        }
    }

    public static short convertByteArrayToShort(byte[] data) throws Exception {
        if (data == null ) {
            return 0x0;
        }
        int v= 2-data.length;
        if(v > 0 ){
            data = fillByteArrayOnHead(data,v);
        }
        // ----------
        return (short)(
                        (0xff & data[0]) << 8   |
                        (0xff & data[1])
        );
    }



    public static Date convertByteArrayToDate(byte[] bytes) throws Exception {
        if(bytes.length!=6){
            throw new Exception("解析采集时间的字节数据失败,字节长度不为6");
        }

        // 年
        int year = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,0,0));

        ByteUtil.checkOutRange(year,0,99,"年");

        // 拼接上当前的世纪年份。
        year+= LocalDateTime.now().getYear()/100*100;

        // 月
        int month = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,1,1));

        ByteUtil.checkOutRange(month,1,12,"月");

        // 日
        int day = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,2,2));

        ByteUtil.checkOutRange(day,1,31,"日");

        // 时
        int hour = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,3,3));

        ByteUtil.checkOutRange(hour,0,23,"小时");

        // 分
        int minute = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,4,4));

        ByteUtil.checkOutRange(minute,0,59,"分钟");

        // 秒
        int second = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,5,5));

        ByteUtil.checkOutRange(second,0,59,"秒");


        LocalDateTime dateTime =  LocalDateTime.of(year,month,day,hour,minute,second);


        return DateUtil.convertToDate(dateTime);

    }


    public static String parseBytesToString(byte[] bytes) throws Exception {
        // 去除填补部分的字符
        byte fill = FILL_STRING_BYTE[0];
        int fillIndex = -1;
        for(int i=0;i<bytes.length;i++){
            if(bytes[i]==fill){
                fillIndex = i;
                break;
            }
        }

        if(fillIndex!=-1){
            return new String(ByteUtil.subBytes(bytes, 0, fillIndex-1), UTF_8);
        }else {
            return new String(bytes, UTF_8);
        }
    }

    public static byte[] processsStringType(String value) {
        if(value==null){
            return FILL_STRING_BYTE;
        }

        return value.getBytes(StandardCharsets.US_ASCII);

    }

    public static byte[] fillStringBytes(int length,String str) throws Exception {
        return ByteConvertUtil.fillStringBytes(length,processsStringType(str));
    }


    public static void checkBytesLength(byte[] bytes, int length) throws Exception {
        if(bytes.length!=length){
            throw new Exception("不支持的字节数组长度! length:"+length+", 实际长度:"+bytes.length);
        }
    }


    /**
     * 比较两个字节数组是否相等
     * @return
     */
    public static boolean compareByteArray(byte[] a,byte[] b){
        if(a.length != b.length){
            return false;
        }

        for(int i=0 ;i <a.length;i++){
            if(a[i] != b[i]){
                return false;
            }
        }

        return true;

    }

    /**
     * char数组转换为list
     */
    public static List<Character> charArrayToList(char[] bytes){
        List<Character> list = new ArrayList<>(bytes.length);
        for(char b : bytes){
            list.add(b);
        }
        return list;
    }


    /**
     * 获取字节b指定位置的值 （大端字节序）
     * @param b 字节
     * @param position 位置
     * @return 0或者1
     */
    public static int getValueFromByte(byte b , int position) throws Exception {
        if(position > 7 || position < 0){
            throw new Exception("超出字节的index范围[0,7]");
        }
        {
            return (b >> (7-position)) & 1;
        }
    }

    public static byte[] processDateType(Date value) {
        // 日期类型的数据返回的字节数组长度为6
        LocalDateTime localDateTime = ByteConvertUtil.convertToLocalDateTimeViaInstant(value);
        // 第1个字节：年，范围0-99
        byte year = intToBytes(localDateTime.getYear() % 100)[3];
        // 第2个字节：月，范围1-12
        byte month = intToBytes(localDateTime.getMonth().getValue())[3];
        // 第3个字节：范围1-31
        byte day = intToBytes(localDateTime.getDayOfMonth())[3];
        // 第4个字节：时, 范围0-23
        byte hour = intToBytes(localDateTime.getHour())[3];
        // 第5个字节: 分，范围0-59
        byte minute = intToBytes(localDateTime.getMinute())[3];
        // 第6个字节：秒，范围0-59
        byte second = intToBytes(localDateTime.getSecond())[3];

        return new byte[]{year, month, day, hour, minute, second};
    }

}
