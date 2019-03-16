package com.wikedapps.spider.io;

import java.util.Base64;

public class FileUtils {

    public static String decodeByteArrayToString(byte[] byteData){
        byte[] decodedData = Base64.getDecoder().decode(byteData);
        return new String(decodedData);
    }
}
