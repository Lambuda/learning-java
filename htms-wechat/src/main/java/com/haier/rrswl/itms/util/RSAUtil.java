package com.haier.rrswl.itms.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA 非对称加密工具类
 *
 * @Author: Lin
 * @CreateDate: 2019/3/22 15:24
 */
public class RSAUtil {

    /**
     * @Fields CHARSET :
     */
    public static final String CHARSET = "UTF-8";
    /**
     * @Description:RSA ALGORITHM
     * @Fields RSA_ALGORITHM :
     */
    public static final String RSA_ALGORITHM = "RSA";


    public static void main (String[] args) throws Exception {
        Map<String, String> keyPairMap = createKeys(512);
        System.out.println("-----公钥----\n"+keyPairMap.get("publicKey"));
        System.out.println("-----私钥----\n"+keyPairMap.get("privateKey"));
        //System.out.println("-----私钥----\n"+keyPairMap.get("modles"));

        String data= "abc122";

        //1.用公钥加密
        String encode=publicEncrypt(data,getPublicKey(keyPairMap.get("publicKey")));

        System.out.println("-----加密结果----\n"+encode);
        //1.用私钥解密
        String decodeResult=privateDecrypt(encode,getPrivateKey(keyPairMap.get("privateKey")));
        System.out.println("-----解密结果----\n"+decodeResult);
    }


    /**
     * @Title: createKeys
     * @Description: 产生RSA公钥和私钥
     * @param keySize
     * @return
     */
    public static Map<String, String> createKeys(int keySize){
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try{
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        }catch(NoSuchAlgorithmException e){
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        RSAPublicKey rsp= (RSAPublicKey)keyPair.getPublic();
        BigInteger bit= rsp.getModulus();
        byte[] b=bit.toByteArray();
        byte[] deBase64Value=Base64.encodeBase64(b);
        String retValue= new String(deBase64Value);
        keyPairMap.put("model",retValue);
        return keyPairMap;
    }


    /**
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @Title: getPublicKey
     * @Description: 获取RSA公钥
     * @Author Administrator
     * @DateTime 2018年11月19日 下午7:48:10
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * @param privateKey 密钥字符串（经过base64编码）
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @Title: getPrivateKey
     * @Description: 获取RSA私钥
     * @DateTime 2018年11月19日 下午7:47:03
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }


    /**
     * @param data
     * @param publicKey
     * @return
     * @Title: publicEncrypt
     * @Description: 公钥加密
     * @DateTime 2018年11月19日 下午7:49:09
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * @param data
     * @param privateKey
     * @return
     * @Title: privateDecrypt
     * @Description: 私钥解密
     * @DateTime 2018年11月19日 下午7:49:36
     */
    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * @param cipher
     * @param opmode
     * @param datas
     * @param keySize
     * @return
     * @Title: rsaSplitCodec
     * @Description: RSA分割code
     * @DateTime 2018年11月19日 下午7:50:11
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

}
