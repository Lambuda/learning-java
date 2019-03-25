package com.haier.rrswl.itms.domain;

/**
 * 用户信息
 *
 *@Author:        Lin
 *@CreateDate:    2019/3/25 9:33
 */
public class ReqPerson {

    private String userName;

    private String password;

    //认证标记
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
