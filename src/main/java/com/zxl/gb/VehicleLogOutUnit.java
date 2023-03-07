package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 国标数据单元： 车辆登出
 * @author zxl
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class VehicleLogOutUnit extends BaseDataUnit implements GbDataUnit,CovertByteArray{

    /**
     * 登出流水号 。 2个字节
     *   范围：1-65531 , 循环周期为天。
     *
     *   登出流水号 = 登入流水号
     *
     */
    private GbValue<Integer> logoutSeq;

    @Override
    public byte[] covert() throws Exception {
        return  ByteConvertUtil.covertClassForUpload(this);
    }

    @Override
    public byte[] transferByteArray() throws Exception {
        return covert();
    }
}
