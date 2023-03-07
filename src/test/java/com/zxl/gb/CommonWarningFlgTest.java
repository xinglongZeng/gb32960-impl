package com.zxl.gb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


class CommonWarningFlgTest {

    public static CommonWarningFlg initData(){
        CommonWarningFlg data= new CommonWarningFlg();
        // 所有字段设置为1
        Arrays.stream(CommonWarningFlg.class.getDeclaredFields())
                .forEach(e->{
                    e.setAccessible(true);
                    try {
                        e.set(data,(short)1);
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                });

        return data;
    }

    @Test
    public void testCovert() throws Exception {
        CommonWarningFlg data= initData();
        byte[] bytes = data.covert();
        CommonWarningFlg newData = data.parseBytes(bytes,null);
        Assertions.assertEquals(data,newData);
    }



}