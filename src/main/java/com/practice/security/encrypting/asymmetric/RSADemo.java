package com.practice.security.encrypting.asymmetric;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Luo Bao Ding
 * @since 2018/8/13
 */
public class RSADemo {

    public static final int KEY_SIZE = 512;
    //not thread safe
    private BASE64Encoder base64Encoder = new BASE64Encoder();
    //not thread safe
    private BASE64Decoder base64Decoder = new BASE64Decoder();

    public KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    public String getBASE64PublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] encoded = publicKey.getEncoded();
        return base64Encoder.encode(encoded);

    }

    public String getBASE64PrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] encoded = privateKey.getEncoded();
        return base64Encoder.encode(encoded);
    }

    public PublicKey parsePublicKey(String pubStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = base64Decoder.decodeBuffer(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public PrivateKey parsePrivateKey(String privateStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = base64Decoder.decodeBuffer(privateStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public byte[] encryptByPublicKey(byte[] content, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    public byte[] decryptByPrivateKey(byte[] content, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    public void demo() throws Exception {
        String msg = "hello, every one";
        KeyPair keyPair = getKeyPair();

        byte[] bytes = encryptByPublicKey(msg.getBytes("utf-8"), keyPair.getPublic());
        String base64encode = base64Encoder.encode(bytes);
        System.out.println("base64encode = " + base64encode);

        byte[] decode = decryptByPrivateKey(bytes, keyPair.getPrivate());
        String s = new String(decode);
        System.out.println("s = " + s);
    }

    /**
     * parsePublicKey, parsePrivateKey
     */
    public void demo2() throws Exception {
        String msg = "hello, every one";
        KeyPair keyPair = getKeyPair();
        String base64PublicKey = getBASE64PublicKey(keyPair);
        System.out.println("base64PublicKey = " + base64PublicKey);
        PublicKey publicKey = parsePublicKey(base64PublicKey);

        byte[] bytesOfEncryptedContent = encryptByPublicKey(msg.getBytes("utf-8"), publicKey);
        String base64encodeContent = base64Encoder.encode(bytesOfEncryptedContent);
        System.out.println("base64encodeContent = " + base64encodeContent);

        String base64PrivateKey = getBASE64PrivateKey(keyPair);
        System.out.println("base64PrivateKey = " + base64PrivateKey);
        PrivateKey privateKey = parsePrivateKey(base64PrivateKey);

        byte[] decode = decryptByPrivateKey(bytesOfEncryptedContent, privateKey);
        String s = new String(decode);
        System.out.println("s = " + s);
    }


    public static void main(String[] args) throws Exception {
        RSADemo rsaDemo = new RSADemo();
        rsaDemo.demo();
        System.out.println("=============demo2==========");
        rsaDemo.demo2();
    }

}
