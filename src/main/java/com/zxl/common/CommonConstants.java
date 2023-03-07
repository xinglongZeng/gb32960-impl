package com.zxl.common;

/**
 * 通用常量
 */
public class CommonConstants {


    // -------------------  输入源数据类型  --------------
    public static final String INPUT_TYPE_SHORT = "Short";
    public static final String INPUT_TYPE_ENUM = "Enum";
    public static final String INPUT_TYPE_INTEGER = "Integer";
    public static final String INPUT_TYPE_FLOAT = "Float";
    public static final String INPUT_TYPE_DOUBLE = "Double";


    // -------------------  输出源数据类型  --------------
    public static final String OUTPUT_TYPE_BYTES = "byte[]";


    // -------------------  执行顺序  --------------

    // os(先偏移，后缩放)
    public static final String SEQUENCE_OP_OS = "os";

    // so(先缩放，后偏移)
    public static final String SEQUENCE_OP_SO = "so";

    // o (只偏移)
    public static final String SEQUENCE_OP_O = "o";

    // s (只缩放)
    public static final String SEQUENCE_OP_S = "s";


    // -------------------  偏移量操作符  --------------

    // 加法
    public static final String OFFSET_OP_ADD = "+";


    // 减法
    public static final String OFFSET_OP_SUB = "-";


    // -------------------  缩放操作符  --------------

    // 乘法
    public static final String SCALE_OP_PLUS = "*";


    // 除法
    public static final String SCALE_OP_DIV = "/";

}
