package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MD5 {
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] b = md.digest();

            int temp;
            StringBuilder sb = new StringBuilder();
            for (byte value : b) {
                temp = value;
                if (temp < 0) {
                    temp += 256;
                }
                if (temp < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(temp));
            }
            str = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }
}
