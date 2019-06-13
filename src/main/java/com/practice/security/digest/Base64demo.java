package com.practice.security.digest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * @author Luo Bao Ding
 * @since 2018/8/13
 */
public class Base64demo {

    public String byteToBase64(byte[] bytes) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(bytes);
    }

    public byte[] base64ToByte(String base64) throws IOException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        return base64Decoder.decodeBuffer(base64);
    }



    public static void main(String[] args) throws IOException {
        Base64demo demo = new Base64demo();

        String msg = "hello";
        String base64 = demo.byteToBase64(msg.getBytes("utf-8"));
        System.out.println("base64 = " + base64);

        byte[] bytes = demo.base64ToByte(base64);
        System.out.println("new String (bytes) = " + new String(bytes));



    }


}
