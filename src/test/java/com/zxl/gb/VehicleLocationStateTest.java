package com.zxl.gb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class VehicleLocationStateTest {



    public static VehicleLocationState initData(){
        VehicleLocationState state = new VehicleLocationState();
        state.setValidFlg((short)1);
        state.setLatitudeType((short)1);
        state.setLongitudeType((short) 1);
        return state;
    }

    @Test
    public void testParse() throws Exception {
        VehicleLocationState initData = initData();
        byte[] bytes = initData.covert();
        VehicleLocationState state = initData.parseByte(bytes, null);
        Assertions.assertEquals(initData,state);
    }



}