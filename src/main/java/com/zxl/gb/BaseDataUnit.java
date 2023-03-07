package com.zxl.gb;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class BaseDataUnit {

    /**
     * 采集时间 (北京时间，精确到秒)。6个字节
     */
    private GbValue<Date> time;


    public void setTime(Date time) {
        this.time = new GbValue(time);
    }

    public void setTime(GbValue<Date> time) {
        this.time = time;
    }








}
