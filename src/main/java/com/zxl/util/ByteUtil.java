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
     * ?????????????????????????????????
     * @param bytes ????????????????????????
     * @param fillLength ??????????????????
     * @return
     */
    public static byte[] fillByteArrayOnHead(byte[]  bytes, int fillLength) throws Exception {
        if(fillLength <=0){
            throw new Exception("fillLength????????????0");
        }

        byte[] newData = new byte[bytes.length+fillLength];
        System.arraycopy(bytes,0,newData,newData.length-bytes.length,bytes.length);
        return newData;
    }


    /**
     *  ????????????b???index????????????value
     * @param b  ????????????????????????
     * @param index  ???????????????????????????????????????????????? 0~7
     * @param value  ?????????????????? 0???1
     * @return  ??????byte
     */
    public static byte setBitByIndexFromByte(byte b,int index , short value ) throws Exception {
        long l = commonSetBitByIndex(b, index, 0, 7, value);
        return (byte)l;
    }

    /**
     *  ??????i???index????????????value (???????????????)
     * @param i  ????????????????????????int
     * @param index  ????????????int??????????????????????????? 0~31
     * @param value  ?????????????????? 0???1
     * @return  ??????byte
     */
    public static int setBitByIndexFromInt(int i,int index , short value ) throws Exception {
        long l = commonSetBitByIndex(i, index, 0, 31, value);
        return (int)l;
    }


    public static long commonSetBitByIndex(long i,long index ,long minIndex,long maxIndex, short value  ) throws Exception {
        if(index > maxIndex || index < minIndex ){
            throw new Exception("int???????????????????????????????????????"+minIndex+"-"+maxIndex+"! index:"+index);
        }

        if(value > 1 || value < 0){
            throw new Exception("??????????????????0??????1 ! value:"+value);
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
     * ???byte??????????????????start????????????end???????????????????????????end???
     * @param bytes
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public static byte[]   subBytes(byte[] bytes,int start,int end ) throws Exception {

        if(start<0){
            throw new Exception("?????????????????????????????????0!");
        }

        if(end < start ){
            throw new Exception("????????????????????????????????????????????????!");
        }

        int length = end - start +1;

        if(length > bytes.length){
            throw new Exception("??????????????????????????????????????????!");
        }

        byte[] sub = new byte[length];

        if (end + 1 - start >= 0) {
            System.arraycopy(bytes, start, sub, 0, length);
        }

        return sub;
    }


    public static void checkOutRange(double v, double min, double max, String key) throws Exception {
        if(v<min || v > max){
            throw new Exception("??????"+key+"??????????????? ["+min+","+max+"] ,?????????:"+v);
        }
    }


    public static void checkByteLength(byte[] bytes , int length,String key) throws Exception {
        if( bytes==null ||  bytes.length != length){
            throw new Exception(key+"????????????????????????????????????! ????????????:"+length);
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
            throw new Exception("???????????????????????????????????????,??????????????????6");
        }

        // ???
        int year = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,0,0));

        ByteUtil.checkOutRange(year,0,99,"???");

        // ?????????????????????????????????
        year+= LocalDateTime.now().getYear()/100*100;

        // ???
        int month = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,1,1));

        ByteUtil.checkOutRange(month,1,12,"???");

        // ???
        int day = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,2,2));

        ByteUtil.checkOutRange(day,1,31,"???");

        // ???
        int hour = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,3,3));

        ByteUtil.checkOutRange(hour,0,23,"??????");

        // ???
        int minute = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,4,4));

        ByteUtil.checkOutRange(minute,0,59,"??????");

        // ???
        int second = ByteUtil.convertByteArrayToInt( ByteUtil.subBytes(bytes,5,5));

        ByteUtil.checkOutRange(second,0,59,"???");


        LocalDateTime dateTime =  LocalDateTime.of(year,month,day,hour,minute,second);


        return DateUtil.convertToDate(dateTime);

    }


    public static String parseBytesToString(byte[] bytes) throws Exception {
        // ???????????????????????????
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
            throw new Exception("??????????????????????????????! length:"+length+", ????????????:"+bytes.length);
        }
    }


    /**
     * ????????????????????????????????????
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
     * char???????????????list
     */
    public static List<Character> charArrayToList(char[] bytes){
        List<Character> list = new ArrayList<>(bytes.length);
        for(char b : bytes){
            list.add(b);
        }
        return list;
    }


    /**
     * ????????????b?????????????????? ?????????????????????
     * @param b ??????
     * @param position ??????
     * @return 0??????1
     */
    public static int getValueFromByte(byte b , int position) throws Exception {
        if(position > 7 || position < 0){
            throw new Exception("???????????????index??????[0,7]");
        }
        {
            return (b >> (7-position)) & 1;
        }
    }

    public static byte[] processDateType(Date value) {
        // ???????????????????????????????????????????????????6
        LocalDateTime localDateTime = ByteConvertUtil.convertToLocalDateTimeViaInstant(value);
        // ???1????????????????????????0-99
        byte year = intToBytes(localDateTime.getYear() % 100)[3];
        // ???2????????????????????????1-12
        byte month = intToBytes(localDateTime.getMonth().getValue())[3];
        // ???3??????????????????1-31
        byte day = intToBytes(localDateTime.getDayOfMonth())[3];
        // ???4???????????????, ??????0-23
        byte hour = intToBytes(localDateTime.getHour())[3];
        // ???5?????????: ????????????0-59
        byte minute = intToBytes(localDateTime.getMinute())[3];
        // ???6????????????????????????0-59
        byte second = intToBytes(localDateTime.getSecond())[3];

        return new byte[]{year, month, day, hour, minute, second};
    }

}
