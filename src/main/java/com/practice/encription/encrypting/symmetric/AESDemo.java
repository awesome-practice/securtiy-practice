package com.practice.encription.encrypting.symmetric;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 对称加密
 * <p>
 *
 * @author Luo Bao Ding
 * @since 2018/8/13
 */
public class AESDemo {

    public SecretKey genKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] encrypt(byte[] source, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(source);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] decrypt(byte[] source, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(source);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void demo() throws UnsupportedEncodingException {
        SecretKey secretKey = genKey();
        byte[] keyEncoded = secretKey.getEncoded();
        System.out.println("plain key length: " + keyEncoded.length);
        String plainKey = new String(keyEncoded, StandardCharsets.UTF_8);
        System.out.println("plainKey = " + plainKey);


        Base64.Encoder base64Encoder = Base64.getEncoder();
        String keyEncodedOfBase64 = new String(base64Encoder.encode(keyEncoded), StandardCharsets.UTF_8);

        System.out.println("keyEncodedOfBase64 = " + keyEncodedOfBase64);

        String msg = "hello, every one";

        byte[] encrypt = encrypt(msg.getBytes(StandardCharsets.UTF_8), secretKey);
        String encode = new String(base64Encoder.encode(encrypt), StandardCharsets.UTF_8);//使可打印
        System.out.println("encode = " + encode);

        SecretKey secretKeySpecRecovered = parseSecretKey(base64Encoder, keyEncodedOfBase64);

        byte[] decrypt = decrypt(encrypt, secretKeySpecRecovered);
        String s = new String(decrypt, StandardCharsets.UTF_8);
        System.out.println("s = " + s);

    }

    private SecretKey parseSecretKey(Base64.Encoder base64Encoder, String keyEncodedOfBase64)  {
        Base64.Decoder base64Decoder = Base64.getDecoder();
        byte[] keyBytesRecovered = base64Decoder.decode(keyEncodedOfBase64);
        SecretKey secretKeySpecRecovered = new SecretKeySpec(keyBytesRecovered, "AES");

        String base64RecoveredKey = new String(base64Encoder.encode(keyBytesRecovered), StandardCharsets.UTF_8);
        System.out.println("base64RecoveredKey = " + base64RecoveredKey);
        return secretKeySpecRecovered;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        new AESDemo().demo();
    }

}
