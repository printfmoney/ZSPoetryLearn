package utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class StringUtil {

    public static String md5(String str) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(str.getBytes());
        }catch (Exception e) {
            throw new RuntimeException("没有这个md5算法");
        }
        StringBuilder md5Code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
        for (int i = 0; i < 32 - md5Code.length(); i++) {
            md5Code.insert(0, "0");
        }
        return md5Code.toString();
    }
}
