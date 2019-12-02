package com.practice.encription.domain;

/**
 * @author Luo Bao Ding
 * @since 2018/8/30
 */
public class MyRequestBody {

    private int uid;

    private String sign;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
