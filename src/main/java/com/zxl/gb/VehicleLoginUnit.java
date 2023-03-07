package com.zxl.gb;


import com.zxl.util.ByteConvertUtil;
import com.zxl.util.ByteUtil;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 国标数据单元 - 车辆登入
 * @author zxl
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class VehicleLoginUnit extends BaseDataUnit implements GbDataUnit,CovertByteArray{


    /**
     * 登入流水号。 2个字节
     *   范围：1-65531 , 循环周期为天。
     */
    private GbValue<Integer> loginSeq;


    /**
     * sim卡的iccid.  ,  20个字节
     */
    private GbValue<String> iccId;


    /**
     * 可充电储能子系统数，1个字节，范围：0-250
     */
    private GbValue<Short> rechargeSubSysNum;

    /**
     * 可充电储能系统编码长度m, 有效范围: 0~50,"0"表示不上传该编码,1个字节
     */
    private GbValue<Short>  rechargeSysCodeLen;

    /**
     * 可充电储能系统编码.n*m个字节
     */
    private List<String> rechargeSysCodeList;

    @Override
    public byte[] transferByteArray() throws Exception {
        return covert();
    }

    @Override
    public byte[] covert() throws Exception {
        return ByteConvertUtil.covertClassForUpload(this);
    }

    private byte[] parseRechargeSysCodeListToBytes() throws Exception {

        // 判断是否要上传可充电储能系统编码
        if(!GbValueFlgEnum.OK.equals(this.getRechargeSubSysNum().getFlg())){
            throw new Exception("rechargeSubSysNum为非ok数据!");
        }

        if(!GbValueFlgEnum.OK.equals(this.getRechargeSysCodeLen().getFlg())){
            throw new Exception("rechargeSysCodeLen为非ok数据!");
        }

        if(this.getRechargeSysCodeLen().getValue()==0){
            return null;
        }

        if (CollectionUtils.isEmpty(this.getRechargeSysCodeList())) {
            throw new Exception("rechargeSysCodeList为空!");
        }


            List<byte[]> rechargeSysCodeList = new ArrayList<>(this.rechargeSubSysNum.getValue());
            int len = this.getRechargeSysCodeLen().getValue();
            for(String s: this.getRechargeSysCodeList()){
                byte[] bytes = ByteUtil.processsStringType(s);
                rechargeSysCodeList.add( ByteConvertUtil.fillStringBytes(len, bytes) );
            }

            // 放入可充电储能系统编码
            return ByteConvertUtil.byteList2Array(rechargeSysCodeList);
    }



    private void checkRechargeSubSysNumAndCodeList() throws Exception {
        if(GbValueFlgEnum.OK.equals(this.rechargeSubSysNum.getFlg())){
            if(this.getRechargeSysCodeList().size() != (int) this.getRechargeSubSysNum().getValue()){
                throw new Exception("rechargeSubSysNum与rechargeSysCodeList的size不一致！");
            }
        }

    }


    public static VehicleLoginUnit parseBytes(byte[] bytes) throws Exception {
        return ByteConvertUtil.parseByteArrayToObj(bytes, VehicleLoginUnit.class);
    }


    public static List<String> getRechargeSysCodeListFromInstance(byte[] bytes, VehicleLoginUnit unit) throws Exception {
        return getRechargeSysCodeListFromBytes(bytes,unit.getRechargeSubSysNum().getValue(),unit.getRechargeSysCodeLen().getValue());
    }

    public static List<String> getRechargeSysCodeListFromBytes(byte[] bytes, int subSysNum,int codeLen) throws Exception {
        if(codeLen==0 || subSysNum==0){
            return null;
        }

        if(bytes.length != codeLen*subSysNum){
            throw new Exception("rechargeSysCodeList的字节数组长度不是rechargeSysCodeLen的整数倍!");
        }

        List<String> list = new ArrayList<>();
        for (int i=0;i< subSysNum; i++ ){
            list.add(new String(ByteUtil.subBytes(bytes,i*codeLen,(i+1)*codeLen-1), UTF_8));
        }


        return list;
    }






}
