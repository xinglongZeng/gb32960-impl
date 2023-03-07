package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import lombok.Data;

/**
 * 探针温度
 * @author zxl
 */
@Data
public class ProbeTemperatures implements CovertByteArray{

    /**
     * 温度
     */
    private GbValue<Short> temperature;

    @Override
    public byte[] covert() throws Exception {
        return ByteConvertUtil.covertClassForUpload(this);
    }
}
