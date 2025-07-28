package com.opensource.utils;


import java.math.BigDecimal;


public class NumberUtils {

    public static Integer getPrecision(Double value){
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.precision();
    }

    public static Integer getScale(Double value){
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.scale();
    }
}
