package com.zxl.gb;


import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GbCommandUnitTest {


    public static  GbCommandUnit initData(){
        GbCommandUnit commandUnit = new GbCommandUnit();
        commandUnit.setCommandFlg(CommandFlgEnum.REAL_TIME_DATA);
        commandUnit.setReplyFlg(ReplyFlgEnum.COMMAND);
        return commandUnit;
    }

    @Test
    public void parse() throws Exception {
        GbCommandUnit initData = initData();
        byte[] bytes = initData.covert();
        byte[] bytes1 = ByteConvertUtil.covertClassForUpload(initData);
        Assertions.assertTrue(ByteUtil.compareByteArray(bytes,bytes1));
        GbCommandUnit newUnit = ByteConvertUtil.parseByteArrayToObj(bytes, initData.getClass());
        Assertions.assertEquals(initData,newUnit);
    }

}