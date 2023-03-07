package com.zxl.gb;

import com.zxl.util.ByteUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 故障码
 */
@Data
@Accessors(chain = true)
public class FaultCodeData implements CovertByteArray{


    private GbValue<String> code;

    @Override
    public byte[] covert() throws Exception {
       return ByteUtil.fillStringBytes(4,this.code.getValue());
    }
}
