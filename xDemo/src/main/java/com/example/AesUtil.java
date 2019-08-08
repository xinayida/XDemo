//package com.example;
//
//
//
//import org.apache.commons.codec.binary.Base64;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//
///**
// * @author vanHoff
// */
//public class AesUtil {
//    private static final String AES_ALG = "AES";
//
//    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";
//
//    private static final byte[] AES_IV = initIv(AES_CBC_PCK_ALG);
//
//    public static String bytesToStrByBase64(byte[] data){
//        return new String(Base64.encodeBase64(data));
//    }
//
//    public static byte[] strToBytesByBase64(String data){
//        return Base64.decodeBase64(data.getBytes());
//    }
//
//    public static void main(String[]args)throws Exception{
//        byte[] bytes = "SUGAR苏格".getBytes("utf-8");
//        String key = bytesToStrByBase64(aesEncrypt(bytes, "b3a6e13121a1ccf6aa66b44f2c07113b"));
//        System.out.println(key);
//        bytes = strToBytesByBase64(key);
//        bytes = aesDecrypt(bytes, "b3a6e13121a1ccf6aa66b44f2c07113b");
//        String str = new String(bytes, "utf-8");
//        System.out.println(str);
//    }
//    public static byte[] aesDecrypt(byte[] targetBytes, String key)
//    {
//        try {
//            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
//            IvParameterSpec iv = new IvParameterSpec(initIv(AES_CBC_PCK_ALG));
//            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key.getBytes()),
//                    AES_ALG), iv);
//
//            return cipher.doFinal(targetBytes);
//        } catch (Exception e) {
//            throw new ValidationException("aes.error", "AES解密失败");
//        }
//    }
//
//    public static byte[] aesEncrypt(byte[] srcBytes, String aesKey){
//        try {
//            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
//
//            IvParameterSpec iv = new IvParameterSpec(AES_IV);
//            cipher.init(Cipher.ENCRYPT_MODE,
//                    new SecretKeySpec(Base64.decodeBase64(aesKey.getBytes()), AES_ALG), iv);
//
//            return cipher.doFinal(srcBytes);
//        } catch (Exception e) {
//            throw new ValidationException("aes.error", "AES解密失败");
//        }
//
//    }
//    public static String getKey(String pwd){
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(128, new SecureRandom(pwd.getBytes()));
//            SecretKey sk = keyGenerator.generateKey();
//            byte[] b = sk.getEncoded();
//            return byteToHexString(b);
//        } catch (NoSuchAlgorithmException e) {
//            throw new ValidationException(MessageCodes.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    private static String byteToHexString(byte[] bytes){
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < bytes.length; i++) {
//            String strHex=Integer.toHexString(bytes[i]);
//            if(strHex.length() > 3){
//                sb.append(strHex.substring(6));
//            } else {
//                if(strHex.length() < 2){
//                    sb.append("0" + strHex);
//                } else {
//                    sb.append(strHex);
//                }
//            }
//        }
//        return  sb.toString();
//    }
//
//    private static byte[] initIv(String fullAlg) {
//        try {
//            Cipher cipher = Cipher.getInstance(fullAlg);
//            int blockSize = cipher.getBlockSize();
//            byte[] iv = new byte[blockSize];
//            for (int i = 0; i < blockSize; ++i) {
//                iv[i] = 0;
//            }
//            iv[2] = 8;
//            return iv;
//        } catch (Exception e) {
//
//            int blockSize = 16;
//            byte[] iv = new byte[blockSize];
//            for (int i = 0; i < blockSize; ++i) {
//                iv[i] = 0;
//            }
//            return iv;
//        }
//    }
//
//}
//
