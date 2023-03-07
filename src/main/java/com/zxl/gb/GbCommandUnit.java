package com.zxl.gb;

import lombok.Data;

/**
 * 国标-命令单元
 */
@Data
public class GbCommandUnit implements CovertByteArray{

    /**
     * 命令标识
     */
    private CommandFlgEnum commandFlg;


    /**
     * 应答标志
     */
    private ReplyFlgEnum replyFlg;

    public static GbCommandUnit parseBytes(byte[] bytes) throws Exception {
        if(bytes.length != 2 ){
            throw new Exception("解析命令单元错误！字节长度必须为2！");
        }
        GbCommandUnit unit = new GbCommandUnit();
        // 命令标识
        unit.setCommandFlg(CommandFlgEnum.getEnumByByte(bytes[0]));
        // 应答标识
        unit.setReplyFlg(ReplyFlgEnum.getEnumByByte(bytes[1]));
        return unit;
    }

    @Override
    public byte[] covert() throws Exception {
        byte[] bytes=new byte[2];
        bytes[0] = this.getCommandFlg().getFlg();
        bytes[1] = this.getReplyFlg().getFlg();
        return bytes;
    }
}
