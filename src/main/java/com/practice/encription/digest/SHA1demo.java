package com.practice.encription.digest;

import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Luo Bao Ding
 * @since 2018/8/13
 */
public class SHA1demo {

    public String digest(String content) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("sha-1");
            byte[] digest = sha1.digest(content.getBytes(StandardCharsets.UTF_8));
            return new BASE64Encoder().encode(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args)  {
        SHA1demo sha1demo = new SHA1demo();
        String digest = sha1demo.digest("hello, every one");
        System.out.println("digest = " + digest);
    }


}
