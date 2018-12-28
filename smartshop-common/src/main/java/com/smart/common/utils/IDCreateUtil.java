package com.smart.common.utils;

import java.util.UUID;

public class IDCreateUtil {
    public static String createUUID(){
        return UUID.randomUUID().toString();
    }
}
