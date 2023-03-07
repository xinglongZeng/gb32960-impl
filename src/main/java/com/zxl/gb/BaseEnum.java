package com.zxl.gb;

public interface BaseEnum {

    byte[] enum2Bytes();

    Object parseToEnum(byte[] bytes) throws Exception;

}
