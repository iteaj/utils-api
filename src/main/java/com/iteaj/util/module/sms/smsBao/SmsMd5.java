package com.iteaj.util.module.sms.smsBao;

import com.iteaj.util.module.util.EncryptSecurity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Create Date By 2016/9/8
 *
 * @author iteaj
 * @since 1.7
 */
public class SmsMd5 implements EncryptSecurity<String> {

    @Override
    public String encrypt(String content) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            result = buf.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {

        }
        return result;
    }

    @Override
    public String encrypt(byte[] bytes) {
        return null;
    }
}
