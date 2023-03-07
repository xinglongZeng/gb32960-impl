package com.zxl.gb;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 国标数据值的抽象定义
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class GbValue<T> {


    /**
     * 值状态
     *
     */
    private GbValueFlgEnum flg = GbValueFlgEnum.OK;


    /**
     * 值
     */
    private T value;


    public GbValue(T value) {
        this.flg = GbValueFlgEnum.OK;
        this.value = value;
    }
}
