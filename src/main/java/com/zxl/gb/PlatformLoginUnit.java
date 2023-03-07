package com.zxl.gb;

import com.zxl.util.ByteConvertUtil;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 国标数据单元 -  平台登入
 * @author zxl
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class PlatformLoginUnit extends BaseDataUnit implements GbDataUnit,CovertByteArray{


    /**
     * 登入流水号。 2个字节
     *   范围：1-65531 , 循环周期为天。
     */
    private GbValue<Integer> loginSeq;

    /**
     * 平台登入用户名，最多12个字节
     */
    private GbValue<String> loginAccount;

    /**
     * 平台登入密码，最多20个字节
     */
    private GbValue<String> loginPwd;


    /**
     * 加密规则
     */
    private EncryptWay encryptWay = EncryptWay.NOT_ENCRYPT;




    @Override
    public byte[] covert() throws Exception {
        return ByteConvertUtil.covertClassForUpload(this);
    }

    @Override
    public byte[] transferByteArray() throws Exception {
        return covert();
    }
}
